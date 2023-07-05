package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class SplunkParameters extends ZTAParameters {
    public String host;
    public String port;
    public String token;
    public Boolean isHttps;
    public Boolean verifySSL;

    public SplunkParameters(String Host,String Port,String AuthToken, Boolean isHttps, Boolean verifySSL) {
        super((Host + ":" + Port), (Host + ":" + Port),"");
        this.host = Host;
        this.port = Port;
        this.isHttps = isHttps;
        this.verifySSL = verifySSL;
        this.token = AuthToken;
    }
}
