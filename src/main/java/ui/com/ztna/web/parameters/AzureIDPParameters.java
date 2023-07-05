package ui.com.ztna.web.parameters;

public class AzureIDPParameters extends IDPParameters {

    public String clientId="";
    public String clientSecret="";
    public String tenantId="";
    public String approvedDomains="";

    public AzureIDPParameters(String clientId,String clientSecret,String tenantId,String approvedDomains) {
        super("azureIDP", "", "");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tenantId=tenantId;
        this.approvedDomains=approvedDomains;
    }
}
