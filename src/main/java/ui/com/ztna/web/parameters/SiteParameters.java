package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;



import java.util.List;

public class SiteParameters extends ZTAParameters {

    public final String hostingProvider;
    public final List<String> relays;

    public SiteParameters(String name, String hostingProvider, List<String> relays){
        super(name,"",relays.get(0));
        this.relays = relays;
        this.hostingProvider = hostingProvider;

    }
}
