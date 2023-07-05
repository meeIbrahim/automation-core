package restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.*;

import static io.restassured.config.EncoderConfig.encoderConfig;

///         This Object Holds data specific to each request e.g. Parameters Cookies and Headers
///         Takes a Prepared Request and Creates a temporary Object that will be used for sending the Request
public class API extends RequestObject {

    public Set<Cookie> Cookies = new HashSet<>();

    /// Builder Class for a Prepared Request Object
    public static Prepared Prepare(restassured.Endpoint Endpoint, String Method) {
        return new Prepared(Endpoint, Method);
    }

    public API(Prepared Prepped) {

        super(Prepped.Endpoint, Prepped.Method);

        if (Prepped.getData != null){this.overwriteResponseData(Prepped.getData);} /// If getData is set in Prepared Request copy to this Object
        if (Prepped.Body != null){this.overwriteBody(Prepped.Body);}  /// If Body is set in Prepared Request copy to this Object
        if (Prepped.Headers != null){this.setHeader(Prepped.Headers);} /// If Headers are set in Prepared Request copy to this Object

        ////   Setting Default Headers                  ///////////
        ////   Can be overwritten by setting them again ///////////
        this.Headers.put("Content-type","application/json");
        this.Headers.put("Accept","*/*");
    }
    /////////////////////////////                Setting Request Specific Data                    /////////////////////////////

    public API setHeader(String Key, String Value){
        this.Headers.put(Key,Value);
        return this;
    }

    /// Overloaded to handle Hashtable inputs
    public API setHeader(Hashtable<String,String> HeaderTable){
        this.Headers.putAll(HeaderTable);
        return this;
    }

    public API params(String... parameters){
        this.Endpoint = Endpoint.param(parameters);
        return this;
    }

    public API data(String... data){
        this.Endpoint = Endpoint.with(data);
        return this;
    }

    public API setCookie(Cookie cookie){
        this.Cookies.add(cookie);
        return this;
    }


    /////////////////////////////                 Allow overwriting Prepared data           /////////////////////////////
    public API overwriteBody(JSONObject Body) {
        this.Body = Body;
        return this;
    }

    public API overwriteResponseData(ArrayList<String> getData){
        this.getData = getData;
        return this;
    }

    ////////////////////////                Sending the Request                         //////////////////////////////

    public BundledResponse send(){
        //// Create a new RestAssured RequestSpec with a default content type set to false
        RequestSpecification builder = new RequestSpecBuilder().setConfig(RestAssured.config().encoderConfig(encoderConfig().
                appendDefaultContentCharsetToContentTypeIfUndefined(false))).setContentType("").build();
        RequestSpecification request = RestAssured.given().spec(builder);
        request.log().headers();
        this._setHeaders(request);
        this._setCookies(request);
        if (Body != null){
            request.body(Body.toString());
        }
        request.log().uri();
        Response resp;
        switch (this.Method){
            case "GET":
                resp =  request.get(this.Endpoint.URL);
                break;
            case "POST":
                resp =  request.post(this.Endpoint.URL);
                break;
            case "PATCH":
                resp =  request.patch(this.Endpoint.URL);
                break;
            case "DELETE":
                resp =  request.delete(this.Endpoint.URL);
                break;
            default:
                return null;
        }
        return new BundledResponse(resp,getResponseData(resp,this.getData));
    }

    private RequestSpecification _setHeaders(RequestSpecification request){
        if (this.Headers == null | this.Headers.isEmpty()){return request;} /// Null Check
        request.headers(this.Headers);
        return request;
    }

    private RequestSpecification _setCookies(RequestSpecification request){
        if (this.Cookies == null | this.Cookies.isEmpty()){return request;}   //// Null Check
        for (Cookie cookie : this.Cookies){
            request.cookie(cookie);
        }
        return request;
    }

    /// Get Response Data
    private Hashtable<String,Object> getResponseData(Response response, List<String> getData){
        Hashtable<String,Object> ResponseData = new Hashtable<>();
        if (getData == null) {return ResponseData;}
        JsonPath body = new JsonPath(response.asString());
        for (String object : getData) {
            String keyName  = getKey(object);
            ResponseData.put(keyName,body.get(object));
        }
        return ResponseData;
    }

    /// Grabs Key name for hashtable by cutting the JsonPath
    private static String getKey(String key){
        return key.substring(key.lastIndexOf(".") + 1);
    }

    public static class BundledResponse {
        public Hashtable<String,Object> getData;
        public io.restassured.response.Response Response;

        public BundledResponse(io.restassured.response.Response Response, Hashtable<String,Object> getData){
            this.Response = Response;
            this.getData = getData;

        }

    }
/////////////////////////                    Helper Class to Create Body from Key Pairs //////////////////////////////
    public static class Body {
        private JSONObject body;
        public Body(){
            this.body = new JSONObject();
        }

        public Body add(String Key, Object Value){
            this.body.put(Key,Value);
            return this;
        }

        public JSONObject build(){return this.body;}
    }
}
