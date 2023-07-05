package azure.object;

import azure.Azure;
import com.microsoft.graph.models.ServicePrincipal;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to methods for creating and getting Azure Enterprise Applications
 */
public class EnterpriseApplications {
    Azure azure;
    static final List<EnterpriseApplication> ENTERPRISE_APPLICATION_LIST = new ArrayList<>();
    public EnterpriseApplications(Azure azure){
        this.azure = azure;
    }
    public GraphServiceClient<Request> client(){
        return azure.client();
    }

    EnterpriseApplication get(String objectID){
        ServicePrincipal sp = azure.client().servicePrincipals(objectID).buildRequest().get();
        assert sp != null;
        RegisteredApplication application = azure.application().get(sp.id);
        return new EnterpriseApplication(application,sp);
    }

}
