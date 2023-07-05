package resource.data.readers;

import resource.data.Data;
import resource.data.parameters.file.AwsIntegrationJSON;
import resource.data.parameters.file.AzureIntegrationJSON;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.AzureIntegrationParameters;

import java.util.List;

public class AzureIntegrationReader {
    final List<AzureIntegrationJSON> INTEGRATIONS;
    String Name="";
    String SubscriptionId="";
    String TenantId=Data.parameters().azureIntegrationCredentials.tenantId;
    String ApplicationClientId=Data.parameters().azureIntegrationCredentials.ApplicationClientId;
    String ObjectId=Data.parameters().azureIntegrationCredentials.ObjectId;
    String ApplicationClientSecret=Data.parameters().azureIntegrationCredentials.ApplicationClientSecret;

    public AzureIntegrationReader() {
        this.Name = Data.generateName();
        this.INTEGRATIONS = Data.parameters().azureIntegration;
    }

    public AzureIntegrationReader withTenantId(String tenantId){this.TenantId = tenantId;return this;}
    public AzureIntegrationReader withApplicationClientId(String applicationClientId){this.ApplicationClientId =applicationClientId;return this;}
    public AzureIntegrationReader withObjectId(String objectId){this.ObjectId= objectId;return this;}
    public AzureIntegrationReader withApplicationClientSecret(String applicationClientSecret){this.ApplicationClientSecret= applicationClientSecret;return this;}

    public AzureIntegrationParameters withAzureSubscriptionId(String subscriptionId){
        return new AzureIntegrationParameters(Data.generateName(),subscriptionId,this.TenantId,this.ApplicationClientId,this.ObjectId,this.ApplicationClientSecret);
    }

    public AzureIntegrationParameters withAzureAccount(String subscriptionId, String tenantId, String applicationClientId, String objectId, String applicationClientSecret){
        return new AzureIntegrationParameters(Data.generateName(),subscriptionId,tenantId,applicationClientId,objectId,applicationClientSecret);
    }
    public AzureIntegrationParameters get(){
        return new AzureIntegrationParameters(Name,SubscriptionId,TenantId,ApplicationClientId,ObjectId,ApplicationClientSecret );
    }
    public AzureIntegrationParameters any(){
        AzureIntegrationJSON azureIntegration = Data.freeResource(INTEGRATIONS,"Integration");
        Data.used(azureIntegration);
        return getAzureIntegrationParameters(azureIntegration);
    }
    public static AzureIntegrationParameters getAzureIntegrationParameters(AzureIntegrationJSON json){
        return new AzureIntegrationReader().withAzureSubscriptionId(json.azureSubscriptionId) ;
    }
}
