package resource.data.readers;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import resource.data.Data;
import resource.data.parameters.file.AzureIntegrationJSON;
import resource.data.parameters.file.GcpIntegrationJSON;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.AzureIntegrationParameters;
import ui.com.ztna.web.parameters.GCPIntegrationParameters;

import java.util.List;


public class GCPIntegrationReader {
    final List<GcpIntegrationJSON> INTEGRATIONS;
    String Name="";

    String ProjectId="";
    String FilePath="src/test/resources/test_data/web/multicloud_integrations/.json";
    public GCPIntegrationReader() {
        this.Name = Data.generateName();
        this.INTEGRATIONS = Data.parameters().gcpIntegration;
    }

    public GCPIntegrationReader withJsonFilePath(String filePath){

        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("env");
        String json=filePath;
        String[] parts = json.split("(?=\\.)");
        json = parts[0]+"-"+environment+parts[1];

        this.FilePath =json;
        return this;
    }

    public GCPIntegrationParameters withGcpProjectId(String projectId){
        return new GCPIntegrationParameters(Data.generateName(),projectId,this.FilePath);
    }
    public GCPIntegrationParameters withGcpProjectId(String projectId,String filePath){
        if(filePath.isEmpty()){
            filePath=this.FilePath;
            String[] parts = filePath.split("(?=\\.)");
            filePath = parts[0]+projectId+parts[1];
        }
        withJsonFilePath(filePath);
        return new GCPIntegrationParameters(Data.generateName(),projectId,this.FilePath);
    }

    public GCPIntegrationParameters get(){
        return new GCPIntegrationParameters(Name,ProjectId,FilePath);
    }
    public GCPIntegrationParameters any(){
        GcpIntegrationJSON gcpIntegration = Data.freeResource(INTEGRATIONS,"Integration");
        Data.used(gcpIntegration);
        return getGcpIntegrationParameters(gcpIntegration);
    }
    public static GCPIntegrationParameters getGcpIntegrationParameters(GcpIntegrationJSON json){
        return new GCPIntegrationReader().withJsonFilePath(json.serviceAccountKeyFilePath).withGcpProjectId(json.gcpProjectId) ;
    }
}
