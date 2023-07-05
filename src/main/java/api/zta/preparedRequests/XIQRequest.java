package api.zta.preparedRequests;

import api.zta.endpoints.XIQ;
import org.json.JSONObject;
import restassured.API;
import restassured.Endpoint;
import restassured.Method;
import restassured.Prepared;

import java.util.Arrays;
import java.util.List;

public class XIQRequest {
    public static Prepared CatalogApps(String Base){
        Endpoint complete_endpoint = new Endpoint(api.zta.endpoints.XIQ.catalog_apps);
        complete_endpoint.URL = complete_endpoint.URL.replaceAll("\\{custom\\}",Base);
        return new Prepared(complete_endpoint, Method.GET);
    }

    public static Prepared login(){
        JSONObject body = new API.Body().add("is_sso", true).build();
        List<String> get = List.of(
                "payload.home_owner_id"
        );
        return new Prepared(XIQ.xiq_login,Method.POST).setBody(body).getResponseData(get);
    }
}
