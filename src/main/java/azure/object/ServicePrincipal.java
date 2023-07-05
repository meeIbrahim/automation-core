package azure.object;

import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

public class ServicePrincipal {
    private final Application application;

    ServicePrincipal(Application application) {
        this.application = application;
    }
}
