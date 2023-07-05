package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

import java.util.ArrayList;
import java.util.List;

public class AGParameters extends ZTAParameters {
    public String description = "";
    public ArrayList<String> users = new ArrayList<>();

    public AGParameters(String Name, String Description){
        super(Name,"","");
        this.description = Description;
    }
    public AGParameters(String Name, String Description,String... Users){
        super(Name,"","");
        this.description = Description;
        this.users.addAll(List.of(Users));
    }
}
