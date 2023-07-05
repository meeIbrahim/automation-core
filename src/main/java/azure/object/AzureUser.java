package azure.object;

import azure.Azure;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;

public class AzureUser implements Deletion {
    private final GraphServiceClient<Request> client;
    private final User user;

    /**
     * Create Azure User Object using existing user
     * @param client Graph client
     * @param user Microsoft graph User Object
     */
    AzureUser(GraphServiceClient<Request> client, User user) {
        this.client = client;
        this.user = user;
    }

    /**
     * Create Azure User object using a new Microsoft user
     * @param client Graph client
     * @param displayName display Name
     * @param mailNick mail Nick
     * @param firstName Optional: First Name
     * @param lastName Optional: Last Name
     */
    AzureUser ( GraphServiceClient<Request> client,
                     String displayName,
                     String mailNick,
                     String firstName,
                     String lastName){
        PasswordProfile passwordProfile = new PasswordProfile();
        passwordProfile.forceChangePasswordNextSignIn = false;
        passwordProfile.password = Azure.USER_PASSWORD;
        User user = new User();
        user.passwordProfile = passwordProfile;
        user.accountEnabled = true;
        user.displayName = displayName;
        user.mailNickname = mailNick;
        user.userPrincipalName = mailNick + Azure.USER_PRINCIPAL;
        if (!firstName.isEmpty()){
            user.givenName = firstName;
        }
        if (!lastName.isEmpty()){
            user.surname = lastName;
        }
        user = client.users().buildRequest().post(user);
        this.client = client;
        this.user = user;
        AzureUsers.AZURE_USERS.add(this);
    }

    @Override
    public void delete() {
        assert user.id != null;
        client.users(user.id).buildRequest().delete();
    }
}
