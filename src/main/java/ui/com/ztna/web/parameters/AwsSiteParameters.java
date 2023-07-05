package ui.com.ztna.web.parameters;

import java.util.ArrayList;
import java.util.List;

public class AwsSiteParameters extends SiteParameters {

    public static final String HOSTING_PROVIDER = "AWS";
    public final String awsAccountId;
    public final List<String> vpc;
    public final String region;

    public AwsSiteParameters(String name, List<String> relays, String awsAccountId, String region, List<String> vpc){
        super(name,HOSTING_PROVIDER,relays);
        this.awsAccountId = awsAccountId;
        this.vpc = vpc;
        this.region = region;

    }

}


