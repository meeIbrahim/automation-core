package resource.manager.provider.common;

import resource.cached.ZTAParameters;
import resource.manager.ResourceException;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public abstract class IndependentEntity <T extends ZTAParameters> extends resourceEntityImplementor <T> {
    public IndependentEntity(Resource<T> page) {
        super(page);
    }

    public ZTAParameters create(String Reference) throws UsedResource, ResourceException {
        T Resource_Data = getParameters();
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
    protected abstract T getParameters() throws UsedResource;
}
