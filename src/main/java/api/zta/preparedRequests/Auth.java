package api.zta.preparedRequests;

import org.json.JSONObject;
import restassured.Prepared;
import restassured.Method;
import restassured.API;

import java.util.Arrays;
import java.util.List;

public class Auth {
    public static Prepared SignIn(String email, String password){
        JSONObject body = new API.Body().
                add("email",email).
                add("password",password).
                add("is_ztna_admin",false).
                build();

        List<String> get = Arrays.asList(
                "payload.session_id",
                "payload.first_name",
                "payload.last_name",
                "payload.workspace_url",
                "payload.id",
                "payload.role.code",
                "payload.email"
        );
        return new Prepared(api.zta.endpoints.Auth.sign_in, Method.POST).setBody(body).getResponseData(get);
    }

    public static Prepared signUp(String firstName, String lastName, String password, String token){

        JSONObject body = new API.Body()
                .add("first_name",firstName)
                .add("last_name",lastName)
                .add("password",password).
                build();

        String tokenn = "Token " + token;

        return new Prepared(api.zta.endpoints.Auth.sign_up, Method.POST).setBody(body).setHeader("Authorization", tokenn);

    }

}
