package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class AzureConnectorParameters extends ConnectorParameters {


    public final String vmType;
    public final String vnet;
    public final String subnetId;
    public final String keyPair;
    public static final String HOSTING_PROVIDER = "Azure";

    public AzureConnectorParameters(String name, String site,
                                    String relay, String vmType,
                                    String vnet, String subnetId, String keyPair){
        super(name,"",site,relay,HOSTING_PROVIDER);
        this.vmType = vmType;
        this.vnet= vnet;
        this.subnetId= subnetId;
        this.keyPair = keyPair;
    }

}
