package api.zta.tasks;

import api.zta.preparedRequests.XIQRequest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import restassured.Client;

import java.util.HashMap;

public class XIQ {
    public static HashMap<String,String> cookies = new HashMap<>();;
    public static Client client = new Client();  /// Use this client to send get calls


    public static void setCookies(WebDriver driver){
        setCookies(driver,XIQ.client);
    }

    public static Client setCookies(WebDriver driver, Client client){
        Cookie access_token = driver.manage().getCookieNamed("api_access_token");
        Cookie oauth2AccessToken = driver.manage().getCookieNamed("oauth2AccessToken");
        Cookie csrftoken = driver.manage().getCookieNamed("csrftoken");
        cookies.put(access_token.getName(),access_token.getValue());
        cookies.put(oauth2AccessToken.getName(),oauth2AccessToken.getValue());
        cookies.put(csrftoken.getName(),csrftoken.getValue());
        io.restassured.http.Cookie access_token_RA = new io.restassured.http.Cookie.Builder(access_token.getName(),access_token.getValue())
                .setDomain(access_token.getDomain()).setExpiryDate(access_token.getExpiry()).setHttpOnly(access_token.isHttpOnly())
                .setPath(access_token.getPath()).setSecured(access_token.isSecure()).build();
        io.restassured.http.Cookie oauth_RA = new io.restassured.http.Cookie.Builder(oauth2AccessToken.getName(),oauth2AccessToken.getValue())
                .setDomain(oauth2AccessToken.getDomain()).setHttpOnly(oauth2AccessToken.isHttpOnly())
                .setPath(oauth2AccessToken.getPath()).setSecured(oauth2AccessToken.isSecure()).build();
        io.restassured.http.Cookie csrf_RA = new io.restassured.http.Cookie.Builder(csrftoken.getName(),csrftoken.getValue())
                .setDomain(csrftoken.getDomain()).setExpiryDate(csrftoken.getExpiry()).setHttpOnly(csrftoken.isHttpOnly())
                .setPath(csrftoken.getPath()).setSecured(csrftoken.isSecure()).build();
        client.saveSessionCookies(access_token_RA,oauth_RA,csrf_RA);
        client.saveSessionHeaders(access_token_RA.getName(),access_token.getValue());
        client.saveSessionHeaders(oauth2AccessToken.getName(),oauth2AccessToken.getValue());
        client.saveSessionHeaders(csrftoken.getName(),csrftoken.getValue());
        return client;
    }

    public static void Poll(String URL){
        Thread polling = new Thread(){
            public void run(){
                long startTime = System.currentTimeMillis();
                long endTime = System.currentTimeMillis();
                while ((endTime - startTime) <= (235 * 60 * 1000)) {
                    endTime = System.currentTimeMillis();
                }
                client.send(XIQRequest.CatalogApps(URL));
            }
        };
        polling.start();
    }

    public static String homeOwnerID(WebDriver driver){
        Client client = new Client();
        XIQ.setCookies(driver, client);
        client.send(XIQRequest.login());
        return String.valueOf(client.LocalData.get("home_owner_id"));
    }



}
