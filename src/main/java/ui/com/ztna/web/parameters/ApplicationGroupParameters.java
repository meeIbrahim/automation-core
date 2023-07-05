package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

import java.util.ArrayList;
import java.util.List;

public class ApplicationGroupParameters extends ZTAParameters {
    public String description;
    public ArrayList<String> services = new ArrayList<>();

    public ApplicationGroupParameters(String Name, String Description){
        super(Name,"","");
        this.description = Description;
    }
    public ApplicationGroupParameters(String Name, String Description, String... Services){
        super(Name,"","");
        this.description = Description;
        this.services.addAll(List.of(Services));
    }
}
