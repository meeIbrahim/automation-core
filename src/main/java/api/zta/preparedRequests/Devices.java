package api.zta.preparedRequests;

import api.zta.endpoints.Resource;
import restassured.Prepared;
import restassured.Method;

public class Devices {
    public static Prepared GetDevicePostures(){
        return new Prepared(Resource.device_postures, Method.GET);
    }

    public static Prepared DeleteDevicePostures(){
        return new Prepared(Resource.device_posture_delete, Method.DELETE);
    }
}
