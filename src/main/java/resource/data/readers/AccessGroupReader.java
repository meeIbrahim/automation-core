package resource.data.readers;

import resource.data.Data;
import ui.com.ztna.web.parameters.AGParameters;

import java.util.ArrayList;
import java.util.List;

public class AccessGroupReader {
    String Name;
    String Description;
    ArrayList<String> users;
    public AccessGroupReader(String Name){
        this.Name = Name;
        users = new ArrayList<>();
        this.Description = "";
    }

    public AccessGroupReader withDescription(){
        this.Description = Data.generateDescription();
        return this;
    }

    public AccessGroupReader withUsers(String... email){
        this.users.addAll(List.of(email));
        return this;
    }
    public AGParameters get(){
        return new AGParameters(Name,Description,users.toArray(String[]::new));
    }
    public static AccessGroupReader parameters(){
        return new AccessGroupReader(Data.generateName());
    }

}
