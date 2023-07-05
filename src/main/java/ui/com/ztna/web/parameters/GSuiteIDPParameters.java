package ui.com.ztna.web.parameters;



public class GSuiteIDPParameters extends IDPParameters {
    public String clientId="";
    public String clientSecret="";
    public String approvedDomains="";

    public GSuiteIDPParameters(String clientId,String clientSecret,String approvedDomains) {
        super("GSuiteIDP", "", "");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.approvedDomains=approvedDomains;
    }
}
