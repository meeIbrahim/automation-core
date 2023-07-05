package resource.data.readers.sites;

import resource.data.Data;
import ui.com.ztna.web.parameters.OnPremSiteParameters;

import java.util.List;

public class OnPremSiteReader extends SiteReader<OnPremSiteParameters>{
    public OnPremSiteReader(){
        super(Data.generateName());
    }

    @Override
    public SiteReader<OnPremSiteParameters> withName(String name) {
        return super.withName(name);
    }

    @Override
    public SiteReader<OnPremSiteParameters> withRelay(String relay) {
        return super.withRelay(relay);
    }

    @Override
    public SiteReader<OnPremSiteParameters> withRelays(List<String> relay) {
        return super.withRelays(relay);
    }

    @Override
    public OnPremSiteParameters getParameters() {
        return new OnPremSiteParameters(name,"Automation",relay);
    }
}
