package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class TenantParameter extends ZTAParameters {
    public String email;
    public String password;
    public TenantParameter(String Email, String Password){
        super(Email,Email,"");
        this.email = Email;
        this.password = Password;
    }
}
