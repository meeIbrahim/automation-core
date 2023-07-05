package ui.com.ztna.web.parameters;

import java.util.ArrayList;
import java.util.List;

public class AzureSiteParameters extends SiteParameters {
    public static final String HOSTING_PROVIDER = "Azure";
    public final String resourceGroup;
    public final String azureSubscriptionId;
    public final String region;
    public final List<String> vnet;
    public AzureSiteParameters(String name, List<String> relays,String azureSubscriptionId,String resourceGroup, String region, List<String> vnet){
        super(name, HOSTING_PROVIDER, relays);
        this.resourceGroup=resourceGroup;
        this.azureSubscriptionId=azureSubscriptionId;
        this.region = region;
        this.vnet = vnet;

    }

}
