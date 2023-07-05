package resource.data.readers.connectors;

import ui.com.ztna.web.parameters.AwsConnectorParameters;

public class AwsConnectorReader extends ConnectorReader<AwsConnectorParameters>{
    String instanceType;
    String vpcID;
    String subnetID;
    String keyPair;
    public AwsConnectorReader instance(String instanceType){
        this.instanceType = instanceType;
        return this;
    }
    public AwsConnectorReader vpc(String vpcID){
        this.vpcID = vpcID;
        return this;
    }
    public AwsConnectorReader subnet(String subnetID){
        this.subnetID = subnetID;
        return this;
    }
    public AwsConnectorReader keyPair(String keyPair){
        this.keyPair = keyPair;
        return this;
    }
    @Override
    public AwsConnectorParameters any() {
        return new AwsConnectorParameters(name,site,relay,instanceType,vpcID,subnetID,keyPair);
    }

    @Override
    public ConnectorReader<AwsConnectorParameters> name(String name) {
        return super.name(name);
    }

    @Override
    public ConnectorReader<AwsConnectorParameters> relay(String relay) {
        return super.relay(relay);
    }

    @Override
    public ConnectorReader<AwsConnectorParameters> site(String site) {
        return super.site(site);
    }
}
