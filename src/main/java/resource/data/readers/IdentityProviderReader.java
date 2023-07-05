package resource.data.readers;

import ui.com.ztna.web.parameters.AzureIDPParameters;
import ui.com.ztna.web.parameters.GSuiteIDPParameters;

public class IdentityProviderReader{

    public IdentityProviderReader(){

    }
    public AzureIDPParameters withAzureCredentials(String clientId,String clientSecret,String tenantId,String approvedDomains){
        return new AzureIDPParameters(clientId,clientSecret,tenantId,approvedDomains);
    }
    public GSuiteIDPParameters withGSuiteCredentials(String clientId,String clientSecret,String approvedDomains){
        return new GSuiteIDPParameters(clientId,clientSecret,approvedDomains);
    }
}
