package azure.object;

import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

public class Group {
    private final GraphServiceClient<Request> client;

    Group(GraphServiceClient<Request> client) {
        this.client = client;
    }
}
