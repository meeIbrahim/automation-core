package resource.manager.provider;


import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.ResourceException;
import resource.manager.provider.common.IndependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.pages.cloud.relays.CloudHostedRelays;
import ui.com.ztna.web.parameters.RelayParameters;


import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Relay extends IndependentEntity<RelayParameters> {

    CloudHostedRelays relayPage;
    public Relay(){
        super(new CloudHostedRelays());
        this.relayPage = (CloudHostedRelays) page;
    }

    @Override
    protected RelayParameters getParameters() throws UsedResource {
        return getParameters(false);
    }
    protected RelayParameters getParameters(Boolean HA) throws UsedResource {
        return Data.relayAnyRegion().setHA(HA).get();
    }

    @Override
    public ZTAParameters create(String Reference) throws UsedResource, ResourceException {
        return create(Reference,false);
    }

    public ZTAParameters create(String Reference,Boolean HA) throws UsedResource, ResourceException {
        RelayParameters Resource_Data = getParameters(HA);
        try {
            theActorInTheSpotlight().attemptsTo(
                    page.create(Resource_Data)
            );
        }
        catch (Exception e)
        {
            throw new ResourceException(getClass().getName() + "-" + Reference);
        }
        Resource_Data.name = theActorInTheSpotlight().asksFor(relayPage.table().row(1)).name();
        cache().cache(Reference, Resource_Data);
        return Resource_Data;
    }

    public ZTAParameters getFree(String Reference,Boolean HA) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(relayPage.getFree(HA,cache().allCached()));
            if (resource == null) {
                resource = theActorInTheSpotlight().asksFor(relayPage.getFree(cache().allCached()));
                if (resource != null){
                    theActorInTheSpotlight().attemptsTo(
                            relayPage.toggleHA(resource, HA));
                }
            }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resource == null){
            return create(Reference,HA);
        }
        cache().cache(Reference, resource);
        return resource;
    }


    public ZTAParameters getActive(String Reference, Boolean HA) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(relayPage.getActive(HA,cache().allCached()));
            if (resource == null) {
                resource = theActorInTheSpotlight().asksFor(relayPage.getActive(cache().allCached()));
                if (resource != null){
                    theActorInTheSpotlight().attemptsTo(
                    relayPage.toggleHA(resource, HA));
                }
            }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resource == null){
            return create(Reference,HA);
        }
        cache().cache(Reference, resource);
        return resource;
    }

}
