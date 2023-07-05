package azure.object;

import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

public class User {
    private final GraphServiceClient<Request> client;

    User(GraphServiceClient<Request> client) {
        this.client = client;
    }
}
