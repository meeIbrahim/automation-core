package resource.data.readers;

import aws.TemporaryCredentialsWithMFAAuthentication;
import resource.data.Data;
import resource.data.parameters.file.AwsIntegrationJSON;
import resource.data.parameters.file.ConnectorJSON;
import resource.data.readers.connectors.ConnectorReader;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.AwsConnectorParameters;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;


import java.util.HashMap;
import java.util.List;


public class AwsIntegrationReader {
    final List<AwsIntegrationJSON> INTEGRATIONS;
    String Name="";
    String AwsAccountId="";
    String region="";
    String AwsAccessKeyId="";
    String AwsSecret="";
    String SessionToken="";

    public AwsIntegrationReader() {
        this.Name = Data.generateName();
        this.INTEGRATIONS = Data.parameters().awsIntegration;
    }

    public AwsIntegrationReader withRegion(String region){this.region = region;return this;}

    public AWSIntegrationParameters withAwsAccountId(String Id){
        HashMap<String, String> credentials=TemporaryCredentialsWithMFAAuthentication.getCredentials(Id);
        this.AwsAccessKeyId=credentials.get("AccessKey");
        this.AwsSecret= credentials.get("Secret");
        this.SessionToken=credentials.get("SessionToken");

        return new AWSIntegrationParameters(Data.generateName(),Id,this.region,this.AwsAccessKeyId,this.AwsSecret,this.SessionToken);
    }
    public AWSIntegrationParameters get(){
        return new AWSIntegrationParameters(Name,AwsAccountId,region,AwsAccessKeyId,AwsSecret,SessionToken);
    }

    public AWSIntegrationParameters any(){
        AwsIntegrationJSON awsIntegration = Data.freeResource(INTEGRATIONS,"Integration");
        Data.used(awsIntegration);
        return getAwsIntegrationParameters(awsIntegration);
    }
    public static AWSIntegrationParameters getAwsIntegrationParameters(AwsIntegrationJSON json){
        return new AwsIntegrationReader().withAwsAccountId(json.awsAccountId) ;
    }
}
