package azure.object;

import azure.Azure;
import com.microsoft.graph.models.Group;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;

public class AzureGroups {
    Azure azure;

    static final List<AzureGroup> AZURE_GROUP_LIST = new ArrayList<>();

    AzureGroups(Azure azure) {
        this.azure = azure;
    }
    public GraphServiceClient<Request> client(){
        return azure.client();
    }
    public AzureGroup create(String displayName){
        return new AzureGroup(azure.client(),displayName);
    }

    public AzureGroup get(String objectID){
        Group existingGroup;
        existingGroup = azure.client().groups(objectID).buildRequest().get();
        return new AzureGroup(azure.client(),existingGroup);
    }

}
