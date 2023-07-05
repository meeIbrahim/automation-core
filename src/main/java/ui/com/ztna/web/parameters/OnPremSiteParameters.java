package ui.com.ztna.web.parameters;

import java.util.List;

public class OnPremSiteParameters extends SiteParameters {

    public final String region;
    public static final String HOSTING_PROVIDER = "On-Prem";
    public OnPremSiteParameters(String name, String region, List<String> relays){
        super(name,HOSTING_PROVIDER,relays);
        this.region = region;

    }

}
