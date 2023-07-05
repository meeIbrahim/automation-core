package restassured;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.webdriver.javascript.JavascriptExecutorFacade;
import org.openqa.selenium.WebDriver;

import java.util.*;
/*
        CLIENT CLASS TO HANDLE A PERSISTENT API SESSION
*/
public class Client {

    public Set<Cookie> persistentCookies;

    public Hashtable<String,String> persistentHeaders;
    public Hashtable<String,Object> LocalData;
    public Response LastResponse;


    public WebDriver driver;

    public Client(){
        this.persistentCookies = new HashSet<>();
        this.persistentHeaders = new Hashtable<>();
        this.LocalData = new Hashtable<>();
    }

    /// Constructor with Webdriver Support
    public Client(WebDriver driver){
        this.persistentCookies = new HashSet<>();
        this.persistentHeaders = new Hashtable<>();
        this.LocalData = new Hashtable<>();
        this.driver = driver;
    }

    public Boolean send (Prepared request){
        API readyAPI = request.with();
        return this.send(readyAPI);
    }

    public Boolean send (Prepared request,String... params){
        API readyAPI = request.with().params(params);
        return this.send(readyAPI);
    }
    public Boolean send(API request){

        setSessionCookies(request);
        setSessionHeaders(request);
        API.BundledResponse BResponse = request.send();
        System.out.println("Response: " + BResponse.Response.getBody().asString());
        System.out.println("SC: " + BResponse.Response.getStatusCode());
        saveLocalData(BResponse.getData);
        LastResponse = BResponse.Response;
        saveSessionCookies(BResponse.Response);
        if (BResponse.Response.statusCode() < 400){
            return true;
        }
        else {
            return false;
        }

    }

    /// Set WebDriver Cookies
    public Client setDriverCookies(String CookieName){
        Date today = new Date();
        Date future = new Date(today.getTime() + (3 * 1000 * 60 * 60 * 24)); // Three days later stuff
        String Cookie = (String) this.LocalData.get(CookieName);
        org.openqa.selenium.Cookie ActualCookie = new org.openqa.selenium.Cookie.Builder(CookieName,Cookie).expiresOn(future).path("/").isHttpOnly(true).isSecure(true).build();
        driver.manage().addCookie(ActualCookie);
        return this;
    }
    /// Set WebDriver Local Storage
    public Client setDriverStorage(String CookieName) {
        String Cookie = (String) this.LocalData.get(CookieName);
        JavascriptExecutorFacade js = new JavascriptExecutorFacade(driver);
        js.executeScript(
                String.format(
                        "window.localStorage.setItem('%s','%s');", CookieName, Cookie));
        return this;
    }

    //// Refresh Webdriver Page
    public void Refresh(){
        this.driver.navigate().refresh();
    }

    //// Save Data in Client Object for use later
    private void saveLocalData(Hashtable<String,Object> newData){
        if (newData.isEmpty()){return;}
        Enumeration<String> keys = newData.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            LocalData.put(key,newData.get(key));
        }
    }

    //// Set Session Cookies before sending the request
    private API setSessionCookies(API request){
        if (persistentCookies.isEmpty()){return request;}
        for (Cookie cookie : persistentCookies){
            request.setCookie(cookie);
        }
        return request;
    }

    //// Set Session Headers before sending the request
    private API setSessionHeaders(API request){
        if (persistentHeaders.isEmpty()){return request;}
        Enumeration<String> keys = persistentHeaders.keys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            request.setHeader(key,persistentHeaders.get(key));
        }
        return request;
    }

    /// Save new Headers to Session Storage
    public void saveSessionHeaders(String Name, String Value){
        this.persistentHeaders.put(Name,Value);
    }

    /// Save new Cookies to Session Storage
    public void saveSessionCookies(Response response){
       List<Cookie> Cookies =  response.getDetailedCookies().asList();
        persistentCookies.addAll(Cookies);
    }
    public void saveSessionCookies(Cookie... cookies){
        new Cookies();
        persistentCookies.addAll(List.of(cookies));
    }

    //// Remove Session Cookies using Cookie Name
    public void removeSessionCookies(String CookieName){
        for (Cookie cookie : persistentCookies){
            if (cookie.getName().equals(CookieName)){
                persistentCookies.remove(cookie);
            }
        }
    }

    /// get Cookie from Session Storage
    public String getCookie(String CookieName){
        for (Cookie cookie : persistentCookies){
            if(cookie.getName().equals(CookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }

    /// Return Last Response Body
    public String lastResponse(){
        return LastResponse.asString();
    }

    /// Return last Status Code
    public Integer lastStatusCode(){
        return LastResponse.getStatusCode();
    }

    /// Return True if last call was successful
    public boolean OK(){
        if (LastResponse.statusCode() < 400){
            return true;
        }
        else {return false;}
    }

    ///  Get Response from last Call
    public Object getPayload(String payloadKey){
        JsonPath Body = new JsonPath(LastResponse.asString());
        return Body.get(payloadKey);
    }

}
