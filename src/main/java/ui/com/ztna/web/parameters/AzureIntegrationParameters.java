package ui.com.ztna.web.parameters;


public class AzureIntegrationParameters extends CloudIntegrationParameters {
    public String SubscriptionId="";
    public String TenantId="";
    public String ApplicationClientId="";
    public String ObjectId="";
    public String ApplicationClientSecret="";

    public AzureIntegrationParameters(String Name, String SubscriptionId, String TenantId, String ApplicationClientId, String ObjectId, String ApplicationClientSecret){
        super(Name,SubscriptionId);
        this.SubscriptionId=SubscriptionId;
        this.TenantId=TenantId;
        this.ApplicationClientId=ApplicationClientId;
        this.ObjectId=ObjectId;
        this.ApplicationClientSecret=ApplicationClientSecret;
    }
}
