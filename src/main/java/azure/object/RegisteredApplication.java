package azure.object;

import com.microsoft.graph.models.AppRole;
import com.microsoft.graph.models.Application;
import com.microsoft.graph.models.ServicePrincipal;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.List;


/**
 * This is a wrapper class around Azure's Registered Application
 * It provides functionality that is required by the Parent Project
 */
public class RegisteredApplication implements Deletion {
    private final GraphServiceClient<Request> client;
    private final AppRole role;
    private final Application registration;

    /**
     * Create a Registered Application object using existing Azure registered app
     * @param client Graph Client
     * @param registration Parent Registered Application Object
     * @param role IAM App Role
     */
    RegisteredApplication(GraphServiceClient<Request> client, Application registration, AppRole role) {
        this.client = client;
        this.registration = registration;
        this.role = role;
    }

    /**
     *
     * Create a Registered Application Object using a new Azure registered apps
     * @param client Graph Client
     * @param displayName Name to be used for new Registered App
     */
    RegisteredApplication(GraphServiceClient<Request> client,String displayName){
        Application appRegistration = new Application();
        appRegistration.displayName = displayName;
        AppRole appRole = RegisteredApplications.appRole();
        appRegistration.appRoles = List.of(appRole);
        appRegistration = client.applications().buildRequest().post(appRegistration);
        this.role = appRole;
        this.registration = appRegistration;
        this.client = client;
        RegisteredApplications.REGISTERED_APPLICATION_LIST.add(this);
    }

    public String name() {
        return registration.displayName;
    }

    public void delete() {
        assert registration.id != null;
        client.applications(registration.id).buildRequest().delete();
    }

    //////// Getters
    public String id() {
        return registration.id;
    }

    public String roleID() {
        assert role.id != null;
        return role.id.toString();
    }

    public GraphServiceClient<Request> client() {
        return client;
    }

    ///// Microsoft Methods
    public EnterpriseApplication getEnterpriseApp(String objectID) {
        ServicePrincipal sp = client.servicePrincipals(objectID).buildRequest().get();
        return new EnterpriseApplication(this, sp);
    }

    public EnterpriseApplication createEnterpriseApp() {
        return new EnterpriseApplication(this);
    }

}
