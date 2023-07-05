package ui.com.ztna.web.parameters;

import java.util.ArrayList;
import java.util.List;

public class GcpSiteParameters extends SiteParameters{
    public static final String HOSTING_PROVIDER = "GCP";
    public final String projectId;
    public final List<String> vpc;

    public GcpSiteParameters(String name, List<String> relays, String projectId, List<String> vpc){
        super(name,HOSTING_PROVIDER,relays);
        this.projectId = projectId;
        this.vpc = vpc;

    }
}
