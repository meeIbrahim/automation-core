package resource.manager.provider;


import net.serenitybdd.screenplay.Performable;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import ui.com.ztna.web.pages.connectors.ServiceConnectors;
import ui.com.ztna.web.parameters.ConnectorParameters;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Connector extends DependentEntity<ConnectorParameters> {

    ServiceConnectors connectorPage;
    public Connector() {
        super(new ServiceConnectors(), RM.site());
        if (page() instanceof  ServiceConnectors){
            this.connectorPage = (ServiceConnectors) page();
        }
        else{
            throw new IllegalArgumentException("Page is not instance of ServiceConnectors!");
        }
    }
    public ZTAParameters getFree(String Reference,String hostingProvider) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(connectorPage.getFree(cache().allCached(),hostingProvider));
            if (resource == null){
                return createConnector(Reference,hostingProvider);
            }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        cache().cache(Reference, resource);
        return resource;
    }
    public ZTAParameters getActive(String Reference,String hostingProvider) throws UsedResource, ResourceException {
        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){}
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(connectorPage.getActive(cache().allCached(),hostingProvider));
            if (resource == null){
                return createConnector(Reference,hostingProvider);
            }
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        cache().cache(Reference, resource);
        return resource;
    }


    public ZTAParameters createConnector(String reference,String hostingProvider){return createConnector(reference,"",hostingProvider);}

    public ZTAParameters createConnector(String reference,String parent,String hostingProvider){
        String preReq = preReq(parent).name;
        ConnectorParameters resourceData = getParameters(preReq,hostingProvider);
        try {
            theActorInTheSpotlight().attemptsTo(
                    connectorPage.create(resourceData)
            );
        }
        catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + reference);
        }
        cache().cache(reference, resourceData);
        return resourceData;
    }
    @Override
    protected PrivateConnectorParameters getParameters(String parent) throws UsedResource {
        return Data.privateConnector().site(parent).any();
    }
    protected ConnectorParameters getParameters(String parent, String hostingProvider){
        switch (hostingProvider){
            case "aws":
                return Data.awsConnector().site(parent).any();
            case "azure":
                return Data.azureConnector().site(parent).any();
            case "gcp":
                return Data.gcpConnector().site(parent).any();
            default:
                Data.privateConnector().site(parent).any();
        }
        return null;
    }

}
