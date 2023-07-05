package resource.data.readers.connectors;

import resource.data.Data;
import ui.com.ztna.web.parameters.ConnectorParameters;

public abstract class ConnectorReader <T extends ConnectorParameters>{
    String site;
    String relay;
    String name;
    protected ConnectorReader(){
        this.relay = "";
        this.name = Data.generateName().toLowerCase();
    }
    public ConnectorReader<T> name(String name){
        this.name = name;
        return this;
    }

    public ConnectorReader<T> relay(String relay){
        this.relay = relay;
        return this;
    }
    public ConnectorReader<T> site(String site){
        this.site = site;
        return this;
    }

    public abstract T any();
}
