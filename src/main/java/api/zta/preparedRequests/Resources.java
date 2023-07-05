package api.zta.preparedRequests;

import api.zta.endpoints.Resource;
import restassured.Prepared;
import restassured.Method;

public class Resources {

    public static Prepared GetConnectors(){
        return new Prepared(Resource.connectors, Method.GET);
    }

    public static Prepared DeleteConnector(){
        return new Prepared(Resource.connector_soft_delete, Method.DELETE);
    }

    public static Prepared ForceDeleteConnector(){
        return new Prepared(Resource.connector_hard_delete, Method.DELETE);
    }

    public static Prepared GetCloudRelayNodes(){
        return new Prepared(Resource.cloud_relays, Method.GET);
    }

    public static Prepared DeleteCloudRelayNode(){
        return new Prepared(Resource.cloud_relay_soft_delete, Method.DELETE);
    }

    public static Prepared GetSites(){
        return new Prepared(Resource.sites, Method.GET);
    }

    public static Prepared DeleteSites(){
        return new Prepared(Resource.sites_hard_delete, Method.DELETE);
    }
}
