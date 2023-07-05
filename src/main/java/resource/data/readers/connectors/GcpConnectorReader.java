package resource.data.readers.connectors;

import ui.com.ztna.web.parameters.GcpConnectorParameters;

public class GcpConnectorReader extends ConnectorReader <GcpConnectorParameters> {
    String vmType;
    String vpcID;
    String subnetID;
    String keyPair;
    String region;
    String availabilityZone;
    public GcpConnectorReader vm(String vmType){
        this.vmType = vmType;
        return this;
    }
    public GcpConnectorReader vpc(String vpcID){
        this.vpcID = vpcID;
        return this;
    }
    public GcpConnectorReader subnet(String subnetID){
        this.subnetID = subnetID;
        return this;
    }
    public GcpConnectorReader keyPair(String keyPair){
        this.keyPair = keyPair;
        return this;
    }
    public GcpConnectorReader availabilityZone(String availabilityZone){
        this.availabilityZone = availabilityZone;
        return this;
    }
    public GcpConnectorReader region(String region){
        this.region = region;
        return this;
    }
    @Override
    public GcpConnectorParameters any() {
        return new GcpConnectorParameters(name.toLowerCase(),site,relay,vmType,vpcID,subnetID,keyPair,region,availabilityZone);
    }

    @Override
    public ConnectorReader<GcpConnectorParameters> name(String name) {
        return super.name(name);
    }

    @Override
    public ConnectorReader<GcpConnectorParameters> relay(String relay) {
        return super.relay(relay);
    }

    @Override
    public ConnectorReader<GcpConnectorParameters> site(String site) {
        return super.site(site);
    }
}
