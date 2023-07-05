package resource.data.readers;

import resource.data.Data;
import resource.data.parameters.file.UserJSON;
import ui.com.ztna.web.parameters.TenantParameter;

public class TenantReader {
    /// For Future: Multiple Accounts
    UserJSON TENANT;
    protected TenantReader(){
        this.TENANT = Data.parameters().tenant;
    }
    public static TenantParameter get(){
        return new TenantParameter(Data.parameters().tenant.email,Data.parameters().tenant.password);
    }
}
