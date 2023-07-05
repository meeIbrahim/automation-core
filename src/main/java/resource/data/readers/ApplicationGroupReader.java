package resource.data.readers;

import resource.data.Data;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;

import java.util.ArrayList;
import java.util.List;

public class ApplicationGroupReader {
    String Name;
    String Description;
    ArrayList<String> Services = new ArrayList<>();

    public ApplicationGroupReader(String Name){
        this.Name = Name;
    }

    public static ApplicationGroupReader parameters(){
        return new ApplicationGroupReader(Data.generateName());
    }
    public ApplicationGroupReader withDescription(){
        this.Description = Data.generateDescription();
        return this;
    }

    public ApplicationGroupReader withServices(String... service){
        this.Services.addAll(List.of(service));
        return this;
    }
    public ApplicationGroupParameters get(){
        if (!Services.isEmpty())
        return new ApplicationGroupParameters(this.Name,Description, Services.toArray(String[]::new));
        else
            return new ApplicationGroupParameters(this.Name,Description);
    }


}
