package resource.manager.provider.common;

import resource.cached.Cache;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.ResourceException;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;


import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public abstract class resourceEntityImplementor <T extends ZTAParameters> implements resourceEntity {
    protected final Resource<T> page;
    protected final Cache<T> cache;

    @SuppressWarnings("unchecked")
    public resourceEntityImplementor(Resource<T> page){
        this.page = page;
        this.cache = new Cache<T>();
    }
    public Resource<T> page(){return page;}

    @Override
    public ZTAParameters getFree(String Reference) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(page.getFree(cache().allCached()));
        if (resource == null){
            try {
                return create(Reference);
            }
            catch (UsedResource e) {
                resource = theActorInTheSpotlight().asksFor(page.getAllActive()).get(0);
                if (resource == null){
                    throw new UsedResource(e.getMessage());
                }
                theActorInTheSpotlight().attemptsTo(
                        page.removeAssociations(resource)
                );
            }
        }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        cache().cache(Reference, resource);
        return resource;
    }

    @Override
    public ZTAParameters getActive(String Reference) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(page.getActive(cache().allCached()));
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resource == null){
            return create(Reference);
        }
        cache().cache(Reference, resource);
        return resource;
    }

    @Override
    public void delete(String Reference) {
        try {
        theActorInTheSpotlight().attemptsTo(
                page.purge(cache().retrieve(Reference))
        );} catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        cache().remove(Reference);
    }

    @Override
    public void removeAssociations(String Reference) {

    }

    @Override
    public ZTAParameters get(String Reference) throws cachedNotFound{
        ZTAParameters resource = cache().retrieve(Reference);
        if (resource == null){throw new cachedNotFound(Reference);}
        return resource;
    }
    public Cache<T> cache(){
        return this.cache;
    }
}
