package resource.data.readers.connectors;

import ui.com.ztna.web.parameters.AzureConnectorParameters;

public class AzureConnectorReader extends ConnectorReader<AzureConnectorParameters> {
    String vmType;
    String subnetID;
    String vnet;
    String keyPair;
    public AzureConnectorReader vm(String vmType){
        this.vmType = vmType;
        return this;
    }
    public AzureConnectorReader vnet(String vnet){
        this.vnet = vnet;
        return this;
    }
    public AzureConnectorReader subnet(String subnetID){
        this.subnetID = subnetID;
        return this;
    }
    public AzureConnectorReader keyPair(String keyPair){
        this.keyPair = keyPair;
        return this;
    }
    @Override
    public AzureConnectorParameters any() {
        return new AzureConnectorParameters(name,site,relay,vmType,vnet,subnetID,keyPair);
    }
}
