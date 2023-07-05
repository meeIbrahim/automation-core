package azure.object;

import com.microsoft.graph.models.Group;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

public class AzureGroup {

    private final  GraphServiceClient<Request> client;
    private final Group group;

    AzureGroup(GraphServiceClient<Request> client, Group group){
        this.group = group;
        this.client = client;
    }

    AzureGroup(GraphServiceClient<Request> client,String displayName){
        Group newGroup = new Group();
        newGroup.displayName = displayName;
        newGroup.mailEnabled = false;
        newGroup.description = "Automation";
        newGroup.mailNickname = "automation";
        newGroup.securityEnabled = true;
        newGroup = client.groups().buildRequest().post(newGroup);
        this.client = client;
        this.group = newGroup;
    }
}
