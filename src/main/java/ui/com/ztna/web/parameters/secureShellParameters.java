package ui.com.ztna.web.parameters;

public class secureShellParameters extends ServiceParameter{
    public String port;
    public secureShellParameters(String Name, String Protocol, String url,String port, String Connector,String Site) {
        super(Name, Protocol, url, Connector,Site);
        this.port = port;
        this.identifier = this.url + ":" + this.port;
    }
}
