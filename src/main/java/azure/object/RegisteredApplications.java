package azure.object;

import azure.Azure;
import com.microsoft.graph.models.AppRole;
import com.microsoft.graph.models.Application;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static azure.Azure.ROLE_NAME;

/**
 * Provides access to methods for creating and getting Azure Registered Applications
 */
public class RegisteredApplications {
    Azure azure;
    static final List<RegisteredApplication> REGISTERED_APPLICATION_LIST = new ArrayList<>();


    public RegisteredApplications(Azure azure) {
        this.azure = azure;
    }
    public GraphServiceClient<Request> client(){
        return azure.client();
    }


    /**
     * Creates RegisteredApplication Object using existing Azure Registered App
     * @param appID AppID for Existing Application
     */
    public RegisteredApplication get(String appID){

        Application registration = azure.client().applications(appID).buildRequest().get();


        assert registration != null;
        assert registration.appRoles != null;

        AppRole role = registration.appRoles.stream()
                .filter(role1 -> {
                    assert role1.allowedMemberTypes != null;
                    return role1.allowedMemberTypes.contains("User");
                })
                .collect(Collectors.toList()).get(0);

        return new RegisteredApplication(azure.client(),registration,role);
    }

    /**
     * Create a new Registered Application Object
     * @param displayName Name for the new Azure Registered App
     *
     */
    public RegisteredApplication create(String displayName){
        return new RegisteredApplication(azure.client(),displayName);
    }


    /**
     *
     * Creates local app role which gives application access to User/Groups
     */
    static AppRole appRole(){
        AppRole role = new AppRole();
        role.allowedMemberTypes = List.of("User");
        role.description = "Access to App for User/Groups";
        role.displayName = ROLE_NAME;
        role.isEnabled = true;
        role.value = "";
        role.id = UUID.randomUUID();
        return role;
    }


}
