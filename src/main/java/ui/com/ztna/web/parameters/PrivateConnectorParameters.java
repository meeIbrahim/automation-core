package ui.com.ztna.web.parameters;

public class PrivateConnectorParameters extends ConnectorParameters {
    public final String host;
    public final String user;
    public final Integer port;
    public final String pem;
    public static final String HOSTING_PROVIDER = "On-Prem";

    public PrivateConnectorParameters(String name, String site, String relay, String user, String host,
                                      Integer port, String pemPath){
        super(name,host,site,relay,HOSTING_PROVIDER);
        this.host = host;
        this.user = user;
        this.pem = pemPath;
        this.port = port;
    }

}
