package ui.com.ztna.web.parameters;


public class AWSIntegrationParameters extends CloudIntegrationParameters {
   public String AwsAccountId="";
    public String region="";
    public String AwsAccessKeyId="";
    public String AwsSecret="";
    public String SessionToken="";

    public AWSIntegrationParameters(String Name, String AccountId,String region, String AccessKeyId, String Secret, String SessionToken){
        super(Name,AccountId);
        this.AwsAccountId=AccountId;
        this.region=region;
        this.AwsAccessKeyId=AccessKeyId;
        this.AwsSecret=Secret;
        this.SessionToken=SessionToken;
    }
}
