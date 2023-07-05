package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class GcpConnectorParameters extends ConnectorParameters {

    public final String vmType;
    public final String vpcId;
    public final String region;
    public final String subnetId;
    public final String availabilityZone;
    public final String keyPair;
    public static final String HOSTING_PROVIDER = "GCP";

    public GcpConnectorParameters(String name, String site, String relay,
                                  String vmType, String vpcId, String subnetId,
                                  String keyPair,String region,String availabilityZone){
        super(name,"",site,relay,HOSTING_PROVIDER);
        this.vmType = vmType;
        this.vpcId= vpcId;
        this.subnetId= subnetId;
        this.availabilityZone =availabilityZone;
        this.region=region;
        this.keyPair = keyPair;
    }

}
