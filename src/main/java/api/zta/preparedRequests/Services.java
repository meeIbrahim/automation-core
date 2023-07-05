package api.zta.preparedRequests;

import api.zta.endpoints.Resource;
import restassured.Prepared;
import restassured.Method;

public class Services {
    public static Prepared GetProjects(){
        return new Prepared(Resource.projects, Method.GET);
    }


    public static Prepared DeleteProject(){
        return new Prepared(Resource.projects_delete, Method.DELETE);
    }

    public static Prepared GetServices(){
        return new Prepared(Resource.services, Method.GET);
    }

    public static Prepared DeleteService(){
        return new Prepared(Resource.services_soft_delete, Method.DELETE);
    }

    public static Prepared ForceDeleteService(){
        return new Prepared(Resource.services_hard_delete, Method.DELETE);
    }
}
