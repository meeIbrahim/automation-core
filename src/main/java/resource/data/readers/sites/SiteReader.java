package resource.data.readers.sites;

import ui.com.ztna.web.parameters.SiteParameters;

import java.util.List;

public abstract class SiteReader <T extends SiteParameters> {
    String name;
    List<String> relay;
    protected SiteReader(String name){
        this.name = name;
    }
    public SiteReader<T> withName(String name){
        this.name = name;
        return this;
    }
    public SiteReader<T> withRelay(String relay){
        this.relay = List.of(relay);
        return this;
    }
    public SiteReader<T> withRelays(List<String> relay){
        this.relay = relay;
        return this;
    }

    public abstract T getParameters();


}
