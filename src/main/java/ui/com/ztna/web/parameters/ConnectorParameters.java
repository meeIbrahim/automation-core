package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class ConnectorParameters extends ZTAParameters {
    public final String site;
    public final String relay;
    public final String hostingProvider;
    public ConnectorParameters(String name, String identifier, String site,String relay,String hostingProvider) {
        super(name, identifier, site);
        this.site = site;
        this.relay = relay;
        this.hostingProvider = hostingProvider;
    }
}
