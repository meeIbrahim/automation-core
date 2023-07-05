package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class UserParameters extends ZTAParameters {
    public String email;
    public String password;
    public String type = "local";
    public String access_group = "";

    public UserParameters(String Email, String Password,String AccessGroup){
        super(Email,Email,"");
        this.email = Email;
        this.password = Password;
        this.access_group = AccessGroup;
    }
    public UserParameters(String Email, String Password,String AccessGroup,String type){
        super(Email,Email,"");
        this.email = Email;
        this.password = Password;
        this.access_group = AccessGroup;
        this.type = type;
    }
}
