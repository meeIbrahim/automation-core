package azure.object;

import com.microsoft.graph.models.AppRole;
import com.microsoft.graph.models.ServicePrincipal;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Application {
    private final GraphServiceClient<Request> client;
    final static String roleName = "IAM Access";
    private AppRole role;
    private com.microsoft.graph.models.Application registration;
    Application(GraphServiceClient<Request> client) {
        this.client = client;
    }

    ///////////////////        Get an Existing AppRegistration with User Role          //////////////////
    static Application get(String appID,GraphServiceClient<Request> client){
        Application application = new Application(client);
        application.registration = client.applications(appID).buildRequest().get();


        assert application.registration != null;
        assert application.registration.appRoles != null;

        application.role = application.registration.appRoles.stream()
                .filter(role1 -> {
                    assert role1.allowedMemberTypes != null;
                    return role1.allowedMemberTypes.contains("User");
                })
                .collect(Collectors.toList()).get(0);

        return application;
    }
    ///////////////////        Creates an AppRegistration with User Role          //////////////////
    static Application create(String displayName,GraphServiceClient<Request> client){
        Application application = new Application(client);
        com.microsoft.graph.models.Application appRegistration = new com.microsoft.graph.models.Application();
        appRegistration.displayName = displayName;
        appRegistration.appRoles = List.of(application.appRole());
        application.registration = client.applications().buildRequest().post(appRegistration);
        return application;
    }


    ////////////////// Create AppRole to allow User and Group role assignment    //////////////////
    private AppRole appRole(){
        AppRole role = new AppRole();
        role.allowedMemberTypes = List.of("User");
        role.description = "Access to App for User/Groups";
        role.displayName = roleName;
        role.isEnabled = true;
        role.value = "";
        role.id = UUID.randomUUID();
        return role;
    }
}
