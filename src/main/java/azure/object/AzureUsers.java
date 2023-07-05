package azure.object;

import azure.Azure;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;

public class AzureUsers {
    Azure azure;
    static final List<AzureUser> AZURE_USERS  = new ArrayList<>();
    public AzureUsers(Azure azure) {
        this.azure = azure;
    }
    public GraphServiceClient<Request> client(){
        return azure.client();
    }


    public AzureUser create(String displayName, String mailNick, String firstName, String lastName){
        return new AzureUser(azure.client(),displayName,mailNick,firstName,lastName);
    }

    public AzureUser create(String displayName, String mailNick){
        return create(displayName,mailNick,"","");
    }

    public AzureUser get(String userID){
        User user = azure.client().users(userID).buildRequest().get();
        return new AzureUser(azure.client(),user);
    }

}
