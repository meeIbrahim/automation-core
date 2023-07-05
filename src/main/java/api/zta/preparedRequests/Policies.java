package api.zta.preparedRequests;

import api.zta.endpoints.Resource;
import restassured.Prepared;
import restassured.Method;

public class Policies {

    public static Prepared GetPolicies(){
        return new Prepared(Resource.policies, Method.GET);
    }

    public static Prepared DeletePolicy(){
        return new Prepared(Resource.policy_soft_delete , Method.PATCH);
    }
}
