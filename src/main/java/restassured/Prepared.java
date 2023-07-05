package restassured;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


// This Prepared Request Object Holds basic data defines a Generic Actionable Request i.e. METHOD + ENDPOINT + BODY + What to Grab from Response
public class Prepared extends RequestObject{



        public Prepared(restassured.Endpoint Endpoint, String Method) {
            super( Endpoint, Method);
        }

        public Prepared setBody(JSONObject Body) {
            this.Body = Body;
            this.getData = new ArrayList<>();
            return this;
        }

         public Prepared setHeader(String Key, String Value){
            this.Headers.put(Key,Value);
            return this;
        }

        /// Overloaded to handle Hashtable inputs
        public Prepared setHeader(Hashtable<String,String> HeaderTable){
            this.Headers.putAll(HeaderTable);
            return this;
    }


    //// Allows Client to grab Data from Response
    //// Pass JsonPaths in a List
        public Prepared getResponseData(List<String> getData) {
            this.getData.addAll(getData);
            return this;
        }

        ///
        public API with(){
            return new API(this);
        }

}

/// Abstract for a RequestObject
abstract class RequestObject {
    public restassured.Endpoint Endpoint;
    public String Method = "";
    protected ArrayList<String> getData;

    protected Hashtable<String,String> Headers = new Hashtable<>();
    protected JSONObject Body;
    public RequestObject(restassured.Endpoint Endpoint, String Method) {
        this.Endpoint = Endpoint;
        this.Method = Method;
    }


}
