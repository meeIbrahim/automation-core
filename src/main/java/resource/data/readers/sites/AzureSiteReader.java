package resource.data.readers.sites;

import resource.data.Data;
import ui.com.ztna.web.parameters.AzureSiteParameters;

import java.util.List;

public class AzureSiteReader extends SiteReader<AzureSiteParameters>{

    String subscriptionId;
    String region;
    String resourceGroup;
    List<String> vnet;
    public AzureSiteReader(){
        super(Data.generateName());
    }
    public AzureSiteReader subscription(String subscriptionId){
        this.subscriptionId = subscriptionId;
        return this;
    }
    public AzureSiteReader region(String region){
        this.region = region;
        return this;
    }
    public AzureSiteReader resourceGroup(String resourceGroup){
        this.resourceGroup = resourceGroup;
        return this;
    }
    public AzureSiteReader vnet(List<String> vnet){
        this.vnet = vnet;
        return this;
    }
    public AzureSiteReader vnet(String vnet){
        this.vnet = List.of(vnet);
        return this;
    }
    @Override
    public AzureSiteParameters getParameters() {
        return new AzureSiteParameters(name,relay,subscriptionId,resourceGroup,region,vnet);
    }

    @Override
    public SiteReader<AzureSiteParameters> withName(String name) {
        return super.withName(name);
    }

    @Override
    public SiteReader<AzureSiteParameters> withRelay(String relay) {
        return super.withRelay(relay);
    }

    @Override
    public SiteReader<AzureSiteParameters> withRelays(List<String> relay) {
        return super.withRelays(relay);
    }
}
