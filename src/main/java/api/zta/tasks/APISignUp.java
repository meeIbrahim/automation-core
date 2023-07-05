package api.zta.tasks;

import api.zta.preparedRequests.Auth;
import org.openqa.selenium.WebDriver;
import restassured.Client;

import java.util.Hashtable;

public class APISignUp {
    public static boolean signUp(String firstName,String lastName, String password,String token, WebDriver driver){
        Client client = new Client(driver);
        client.send(Auth.signUp(firstName,lastName,password,token));
        return client.OK();
    }
}
