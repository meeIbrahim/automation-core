package resource.data.readers.sites;

import resource.data.Data;
import ui.com.ztna.web.parameters.GcpSiteParameters;

import java.util.List;

public class GcpSiteReader extends SiteReader<GcpSiteParameters> {
    String projectId;
    List<String> vpc;

    public GcpSiteReader(){
        super(Data.generateName());
    }
    public GcpSiteReader project(String projectId){
        this.projectId = projectId;
        return this;
    }
    public GcpSiteReader vpc(String vpc){
        this.vpc = List.of(vpc);
        return this;
    }
    public GcpSiteReader vpc(List<String > vpc){
        this.vpc = vpc;
        return this;
    }
    @Override
    public GcpSiteParameters getParameters() {
        return new GcpSiteParameters(name,relay,projectId,vpc);
    }

    @Override
    public SiteReader<GcpSiteParameters> withName(String name) {
        return super.withName(name.toLowerCase());
    }

    @Override
    public SiteReader<GcpSiteParameters> withRelay(String relay) {
        return super.withRelay(relay);
    }

    @Override
    public SiteReader<GcpSiteParameters> withRelays(List<String> relay) {
        return super.withRelays(relay);
    }
}
