package api.zta;


import api.zta.preparedRequests.XIQRequest;
import api.zta.tasks.APILogIn;
import api.zta.tasks.APISignUp;
import api.zta.tasks.XIQ;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import postgresql.databases.AuthDb;
import restassured.Client;

import java.io.File;

import static api.zta.tasks.Deletion.CleanEnv;


public class Request {

    public static Performable signIn(String email, String password){
        return Task.where("{0} Signs in using API",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            APILogIn.SignIn(email,password,driver);
        });
    }

    public static Performable signUp(String email, String firstName, String lastName,String password){
        return Task.where("Signup using API",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            String token = AuthDb.getAuthToken(email);
            APISignUp.signUp(firstName,lastName,password,token,driver);
        });
    }



    public static Performable deleteAll(String email, String password) {
        return Task.where("{0} Deletes All Resources",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            CleanEnv(email, password,driver);
        });


    }


    public static Performable XIQSession() {
        return Task.where("{0} Gets Session Data",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            String url = driver.getCurrentUrl();
            String polling_url = url.substring(0,url.indexOf("/",10)) + "/xcd-apps/catalog-apps";
            XIQ.setCookies(driver);
            XIQ.Poll(polling_url);
        });

    }

    public static Question<String> homeOwnerID(){
        return Question.about("workspace ID of logged in Tenant").answeredBy(
                actor -> {
                    WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                    return XIQ.homeOwnerID(driver);
                }
        );
    }
}
