package api.zta.preparedRequests;

import api.zta.endpoints.Auth;
import api.zta.endpoints.Resource;
import restassured.Prepared;
import restassured.Method;

public class IAM {
    public static Prepared GetAccessGroups(){
        return new Prepared(Resource.access_groups, Method.GET);
    }

    public static Prepared DeleteAccessGroups(){
        return new Prepared(Resource.access_group_delete, Method.DELETE);
    }
    public static Prepared GetUsers(){
        return new Prepared(api.zta.endpoints.Auth.users, Method.GET);
    }

    public static Prepared DeleteUser(){
        return new Prepared(Auth.user_delete, Method.DELETE);
    }
}
