package api.zta.tasks;

import api.zta.preparedRequests.*;
import org.openqa.selenium.WebDriver;
import restassured.Prepared;
import restassured.Client;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import static api.zta.tasks.Deletion.DeleteResources;

public class Deletion {

    public static boolean CleanEnv(String email, String password, WebDriver driver){
        Client client = new Client();
//        client.send(Auth.SignIn(email,password));
        XIQ.setCookies(driver,client);
        client.saveSessionHeaders("X-CSRFToken",client.getCookie("csrftoken"));
        return Start(client);
    }

    /// The deletion is divided in order to delete everything unrelated
    /// Returns False if any resource fails to delete
    public static boolean Start(Client client){
        boolean status = true;
        if(!Policy(client)){
            return false;
        }
        if (!Resources(client)){status = false;}
        if (!IAM(client)){status = false;}

        return status;

    }

    private static boolean Policy(Client client){
        return Policy.Delete(client);
    }
    private static boolean Resources(Client client){
        return Projects.Delete(client);
    }
    private static boolean IAM(Client client){
        return DevicePosture.Delete(client);
    }

    public static boolean DeleteResource(Client client, String resource, Prepared Call) {
        client.send(Call.with().data(resource));
        return client.OK();
    }

    public static boolean DeleteResources(Client client,String Query, String state,String inProgressState,String resourcePath, Prepared GetCall, Prepared DeleteCall){
        String EnabledState = Query + "=" + state;
        if (state.isEmpty()){
            EnabledState = "";
        }
        client.send(GetCall.with().params("limit=7",EnabledState));
        ArrayList<Object> List = (ArrayList<Object>) client.getPayload(resourcePath);
        while(List != null && !List.isEmpty()) {
            for (Object resource : List) {
                if (resource instanceof Integer) {resource = Integer.toString((Integer) resource);}
                DeleteResource(client, (String) resource, DeleteCall);
            }
            client.send(GetCall.with().params("limit=7",EnabledState));
            List = (ArrayList<Object>) client.getPayload(resourcePath);
        }
        return waitForElement(client,GetCall,resourcePath,Query,inProgressState);
    }

    public static boolean DeleteResources(Client client, String Query, String state,String inProgressState,String resourcePath, Prepared GetCall, Prepared DeleteCall,Prepared ForceDeleteCall) {
        if(DeleteResources(client, Query,state, inProgressState, resourcePath, GetCall, DeleteCall)){
           return true;
        }
        return DeleteResources(client,Query,inProgressState,inProgressState,resourcePath,GetCall,ForceDeleteCall);
    }

    public static Boolean waitForElement(Client client, Prepared GetCall,String resourcePath, String Query, String State){
        Instant Start = Instant.now();
        int resources = 1;
        String EnabledState = Query + "=" + State;
        if (State.isEmpty()){
            EnabledState = "";
        }
        ArrayList<Object> List;
        while (resources > 0){
            client.send(GetCall.with().params("limit=7",EnabledState));
            List = (ArrayList<Object>) client.getPayload(resourcePath);
            resources = List.size();
            if (Duration.between(Start,Instant.now()).toSeconds() > 180){
                return false;
            }
            try {
                Thread.sleep(10000);
            }
            catch (InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

}


class Policy {

    public static String Resource_Path = "payload.policies.id";
    public static String Enabled_State = "ACTIVE";
    public static String inProgress_State = "DELETE_IN_PROGRESS";

    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        return DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Policies.GetPolicies(), Policies.DeletePolicy());
    }
}

class Projects {
    public static String Resource_Path = "payload.projects.id";
    public static String Enabled_State = "";
    public static String inProgress_State = "";
    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Services.GetProjects(), Services.DeleteProject())){
            return Service.Delete(client);
        }
        return false;
    }
}

class Service {
    public static String Resource_Path = "payload.services.id";
    public static String Enabled_State = "PUBLISHED";
    public static String inProgress_State = "DELETE_IN_PROGRESS";
    public static String Query_Param = "status";

    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Services.GetServices(), Services.DeleteService(),Services.ForceDeleteService())){
            return Connectors.Delete(client);
        }
        return false;
    }
}

class Connectors  {
    public static String Resource_Path = "payload.hosts.id";
    public static String Enabled_State = "ENABLED";
    public static String inProgress_State = "DELETE_IN_PROGRESS";
    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Resources.GetConnectors(), Resources.DeleteConnector(), Resources.ForceDeleteConnector())){
            return Sites.Delete(client);
        }
        return false;
    }
}

class Sites {
    public static String Resource_Path = "payload.sites.id";
    public static String Enabled_State = "";
    public static String inProgress_State = "";
    public static String Query_Param = "state";
    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Resources.GetSites(), Resources.DeleteSites())){
            return Relays.Delete(client);
        }
        return false;
    }
}

class Relays {
    public static String Resource_Path = "payload.relay_nodes.id";
    public static String Enabled_State = "ENABLED";
    public static String inProgress_State = "DELETE_IN_PROGRESS";
    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        return DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Resources.GetCloudRelayNodes(), Resources.DeleteCloudRelayNode());
    }
}

class DevicePosture {
    public static String Resource_Path = "payload.device_postures.id";
    public static String Enabled_State = "";
    public static String inProgress_State = "";
    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                Devices.GetDevicePostures(), Devices.DeleteDevicePostures())){
            return AccessGroups.Delete(client);
        }
        return false;
    }
}

class AccessGroups {
    public static String Resource_Path = "payload.access_groups.id";
    public static String Enabled_State = "";
    public static String inProgress_State = "";
    public static String Query_Param = "state";

    public static boolean Delete(Client client){
        if (DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                IAM.GetAccessGroups(), IAM.DeleteAccessGroups())){
            return Users.Delete(client);
        }
        return false;
    }
}

class Users {
    public static String Resource_Path = "payload.users.findAll { users -> users.role.name != \"TENANT_SUPER_ADMIN\"}.id"; // Using Modified Json Path to filter out Tenant
    public static String Enabled_State = "";
    public static String inProgress_State = "";
    public static String Query_Param = "status";

    public static boolean Delete(Client client){
        return DeleteResources(client,Query_Param,Enabled_State,inProgress_State,Resource_Path,
                IAM.GetUsers(), IAM.DeleteUser());
    }
}