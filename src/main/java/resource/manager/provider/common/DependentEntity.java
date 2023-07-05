package resource.manager.provider.common;

import org.jetbrains.annotations.NotNull;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.ResourceException;
import resource.data.UsedResource;
import resource.manager.provider.ApplicationGroup;
import ui.com.ztna.web.common.Resource;

import java.util.List;
import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public abstract class DependentEntity <T extends ZTAParameters> extends resourceEntityImplementor <T>{

    resourceEntityImplementor<?> parentProvider;

    public void setParentProvider(resourceEntityImplementor<?> entity){
        parentProvider = entity;
    }

    public DependentEntity(@NotNull Resource<T> page,@NotNull resourceEntityImplementor<?> parentProvider) {
        super(page);
        this.parentProvider = parentProvider;
    }
    public resourceEntityImplementor<?> parentProvider(){return parentProvider;}

    public ZTAParameters create(String Reference) throws UsedResource, ResourceException {
        return create(Reference,"");
    }
    public ZTAParameters create(String Reference,String Parent) throws UsedResource, ResourceException {
        String preReq = preReq(Parent).name;
        T Resource_Data = getParameters(preReq);
        try {
            theActorInTheSpotlight().attemptsTo(
                    page.create(Resource_Data)
            );
        }
        catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        cache().cache(Reference, Resource_Data);
        return Resource_Data;
    }

    public ZTAParameters getActive(String Reference, String ParentRef) throws UsedResource {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters Parent = preReq(ParentRef);
        ZTAParameters resourceToReturn = null;
        try {
            List<ZTAParameters> allResources = theActorInTheSpotlight().asksFor(page.getAllActive());
            if (!allResources.isEmpty()) {
            for (ZTAParameters resource : allResources){
                if (Objects.equals(resource.parent, Parent.name)){
                    if(!Objects.equals(cache().lookFor(resource.name), Reference)) {
                        resourceToReturn = resource;
                        break;
                    }
                }
            }

            }
        }
        catch (Exception e){
            throw new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resourceToReturn == null){
            return create(Reference, ParentRef);
        }
        cache().cache(Reference,resourceToReturn);
        return resourceToReturn;

    }
    public ZTAParameters getFree(String Reference, String ParentRef) throws UsedResource {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters Parent = preReq(ParentRef);
        ZTAParameters resourceToReturn = null;
        try {
            List<ZTAParameters> allResources = theActorInTheSpotlight().asksFor(page.getAllFree());
            if (!allResources.isEmpty()) {
                for (ZTAParameters resource : allResources){
                    if (Objects.equals(resource.parent, Parent.name)){
                        if (!Objects.equals(Reference,cache().lookFor(resource.name))) {
                            resourceToReturn = resource;
                            break;
                        }
                    }
                }

            }
        }
        catch (Exception e){
            throw new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resourceToReturn == null){
            try {
                return create(Reference, ParentRef);
            }
            catch (UsedResource e) {
                List<ZTAParameters> allResources = theActorInTheSpotlight().asksFor(page.getAllActive());
                if (!allResources.isEmpty()) {
                    for (ZTAParameters resource : allResources) {
                        if (Objects.equals(resource.parent, Parent.name)) {
                            if (!Objects.equals(Reference, cache().lookFor(resource.name))) {
                                resourceToReturn = resource;
                                theActorInTheSpotlight().attemptsTo(
                                        page.removeAssociations(resourceToReturn)
                                );
                                break;
                            }
                        }
                    }
                } else {
                    throw new UsedResource(e.getMessage());
                }
            }
        }
        cache().cache(Reference,resourceToReturn);
        return resourceToReturn;
    }


    protected abstract T getParameters(String parent) throws UsedResource, ResourceException;
    protected ZTAParameters preReq(String Reference) throws UsedResource, ResourceException {
        try {
            return parentProvider().cache().retrieve(Reference);
        } catch (cachedNotFound ignored) {
        }
        return parentProvider().getActive(Reference);
    }
}
