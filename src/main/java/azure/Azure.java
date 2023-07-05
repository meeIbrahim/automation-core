package azure;


import azure.object.AzureUsers;
import azure.object.EnterpriseApplications;
import azure.object.RegisteredApplications;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.AppRole;
import com.microsoft.graph.models.AppRoleAssignment;
import com.microsoft.graph.models.ServicePrincipal;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class Azure {
    static Azure azure = null;
    static String portal = "https://login.microsoftonline.com";
    public static final String ROLE_NAME = "IAM Access";
    public static final String USER_PRINCIPAL = "@emumbapvtgmail.onmicrosoft.com";  // Verified Domain for Users
    public static final String USER_PASSWORD = "automation@123-ZTA";
    private final GraphServiceClient<Request> client;

    RegisteredApplications application;
    EnterpriseApplications enterpriseApplications;
    AzureUsers azureUsers;

    public static Azure theAzureClient(){
        if (azure == null){azure = new Azure("b790f6ff-34c5-4074-bc2e-2f6be41e5870",
                "981fbbf9-721a-4357-93b1-a2e7cce9ab7b",
                "WKj8Q~8N3mdRlJQ.WvRopaCCyymDyZYeGROnObW_");}
        return azure;
    }

    private Azure(String tenant, String clientID, String clientSecret) {
        List<String> scopes = List.of("https://graph.microsoft.com/.default");
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientID)
                .clientSecret(clientSecret)
                .tenantId(tenant)
                .build();

        ///////////////////////                 Authenticate Using Client Secret                                //////////////////
        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        this.client = GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        this.application = new RegisteredApplications(this);
        this.azureUsers = new AzureUsers(this);
        this.enterpriseApplications = new EnterpriseApplications(this);
    }

    public RegisteredApplications application(){
        return application;
    }
    public GraphServiceClient<Request> client(){
        return client;
    }

    //////////////////  Assign Role to Application    //////////////////
    public void assignRole(String servicePrincipalID, Boolean isGroup, String memberID){
        ServicePrincipal principal  = client.servicePrincipals(servicePrincipalID).buildRequest().get();
        assert principal != null;
        assert principal.appRoles != null;
        AppRole appRole = principal.appRoles.stream().filter(role -> Objects.equals(role.displayName, "roleName")).collect(Collectors.toList()).get(0);
        AppRoleAssignment assignment = new AppRoleAssignment();
        assert principal.id != null;
        assignment.principalId = UUID.fromString(memberID);
        assignment.resourceId = UUID.fromString(principal.id);
        assignment.appRoleId = appRole.id;
        if (isGroup){
            client.groups(memberID).appRoleAssignments().buildRequest().post(assignment);
        }
        else {
            client.users(memberID).appRoleAssignments().buildRequest().post(assignment);
        }
    }

    public static void main(String[] args) {
        Azure azure = theAzureClient();
        azure.azureUsers.get("52e58d32-79b4-4a43-ad53-9cfd3e5de48c").delete();
    }




}
