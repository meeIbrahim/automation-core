package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class AwsConnectorParameters extends ConnectorParameters {


    public final String instanceType;
    public final String vpcId;
    public final String subnetId;
    public final String keyPair;
    public static final String HOSTING_PROVIDER = "AWS";


    public AwsConnectorParameters(String name, String site,String relay, String instanceType, String vpcId, String subnetId, String keyPair){
        super(name,"",site,relay,HOSTING_PROVIDER);
        this.instanceType = instanceType;
        this.vpcId= vpcId;
        this.subnetId= subnetId;
        this.keyPair = keyPair;
    }

}
