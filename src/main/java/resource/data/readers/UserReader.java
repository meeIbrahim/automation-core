package resource.data.readers;

import lombok.SneakyThrows;
import resource.data.Data;
import resource.data.FindData;
import resource.data.UnknownResource;
import resource.data.parameters.file.UserJSON;
import ui.com.ztna.web.parameters.UserParameters;

import java.util.List;

public class UserReader implements reverseLookup<UserParameters> {

    List<UserJSON> USERS;
    String type;
    String access_group = "";
    public UserReader(List<UserJSON> USERS,String type){
        this.USERS = USERS;
        this.type = type;
    }
    @SneakyThrows
    public UserParameters any(){
                return new UserParameters(Data.generateEmail(),"ZTNA@123",access_group,type);
    }

    public UserReader withGroup(String AccessGroup){
        this.access_group = AccessGroup;
        return this;
    }

    public static UserReader local(){
        return new UserReader(Data.parameters().user.local,"local");
    }
    public static UserReader google(){
        return new UserReader(Data.parameters().user.google,"google");
    }
    public static UserReader microsoft(){
        return new UserReader(Data.parameters().user.microsoft,"microsoft");
    }

    @Override
    public UserParameters using(String Parent, String identifier) throws UnknownResource {
        UserJSON json = FindData.lookup(identifier,UserParameters.class);
        return new UserParameters(json.email,json.password,type);
    }
}
