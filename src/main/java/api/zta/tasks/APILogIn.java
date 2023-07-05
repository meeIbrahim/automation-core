package api.zta.tasks;

import api.zta.preparedRequests.Auth;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import restassured.Client;

import java.util.Hashtable;

/// Task to Sign in using APIs and setting cookies
public class APILogIn {

    public static boolean SignIn(String email, String password, WebDriver driver){
        Client client = new Client(driver);
        client.send(Auth.SignIn(email,password));
        System.out.println("Response: " + client.LastResponse.asString());
        client.LocalData = createSignInCookies(client.LocalData);
        client.setDriverCookies("sessionid").setDriverStorage("userData").Refresh();
        return client.OK();
    }

    public static Hashtable<String,Object> createSignInCookies(Hashtable<String,Object> Data){
        Hashtable<String,Object> cookies = new Hashtable<>();
        String fullName = Data.get("first_name") +" "+ Data.get("last_name");
        JSONObject userData = new JSONObject();
        String sessionid = (String)Data.get("session_id");
        userData.put("id",Data.get("id"));
        userData.put("workspace",Data.get("workspace_url"));
        userData.put("name",fullName);
        userData.put("sessionId",sessionid);
        userData.put("role",Data.get("code"));
        userData.put("email",Data.get("email"));
        cookies.put("userData",userData.toString());
        cookies.put("sessionid",sessionid);
        return cookies;
    }
}
