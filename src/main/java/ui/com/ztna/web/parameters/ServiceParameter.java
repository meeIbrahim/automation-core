package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class ServiceParameter extends ZTAParameters {
    public String protocol;
    public String url;
    public String connector;
    public String site;
    public ServiceParameter(String Name, String Protocol, String url,String Connector,String Site){
        super(Name,url,Connector);
        this.protocol=  Protocol;
        this.url = url;
        this.connector = Connector;
        this.site = Site;
        this.parent = this.connector;
    }
}
