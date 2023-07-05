package resource.manager.provider;

import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.parameters.PolicyParameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.pages.ZTA.ZTA.policies;

public class Policy extends DependentEntity<PolicyParameters> {

    private final Policies policyPage;

    public Policy() {
        super(new Policies(),RM.service()
                .webApp());
        if(page() instanceof Policies){
            policyPage = (Policies) page();
        }else{
            throw new IllegalArgumentException("Page object is not instance of policies.");
        }
    }

    @Override
    protected PolicyParameters getParameters(String parent) throws UsedResource {
        return Data.policyForService(parent).get();
    }

    protected PolicyParameters getParameters(String parent,Boolean hasProject) throws UsedResource {
        if(Boolean.TRUE.equals(hasProject)){
            return Data.policyForProject(parent).get();
        }else {
            return Data.policyForService(parent).get();
        }

    }

    public ZTAParameters preReq(String Reference, Boolean hasProject) throws UsedResource, ResourceException {
        if(hasProject){
            setParentProvider(RM.project());
        }
        try {
            return parentProvider().cache().retrieve(Reference);
        } catch (cachedNotFound ignored) {
        }
        return parentProvider().getActive(Reference);
    }

    public ZTAParameters create(String reference,Boolean hasProject) throws UsedResource, ResourceException {

        String preReq = preReq("",hasProject).name;
        PolicyParameters resourceData = getParameters(preReq,hasProject);
        try {
            theActorInTheSpotlight().attemptsTo(
                    policyPage.create(resourceData)
            );
        }
        catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + reference);
        }
        cache().cache(reference, resourceData);
        return resourceData;
    }

    public ZTAParameters getFree(String reference, Boolean hasProject){
        try {
            cache().retrieve(reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(policyPage.getFree(cache().allCached(),hasProject));
            if (resource == null){
                return create(reference,hasProject);
            }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + reference);
        }
        cache().cache(reference, resource);
        return resource;
    }

}
