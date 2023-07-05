package resource.data.readers.connectors;

import lombok.SneakyThrows;
import resource.data.Data;
import resource.data.FindData;
import resource.data.UnknownResource;
import resource.data.parameters.file.ConnectorJSON;
import resource.data.readers.reverseLookup;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;

import java.util.List;

public class PrivateConnectorReader extends ConnectorReader <PrivateConnectorParameters> implements reverseLookup<PrivateConnectorParameters> {
    final List<ConnectorJSON> CONNECTORS;

    public PrivateConnectorReader(){
        this.name = Data.generateName();
        this.CONNECTORS = Data.parameters().connector;
    }

    @SneakyThrows
    public PrivateConnectorParameters any(){
        ConnectorJSON connector = Data.freeResource(CONNECTORS,"Connector");
        Data.used(connector);
        return getConnectorParameters(connector,this.relay ,this.site);
    }

    public static PrivateConnectorParameters getConnectorParameters(ConnectorJSON json, String relay, String site){
        return new PrivateConnectorParameters(Data.generateName(), site, relay, json.user, json.host, json.port,json.pem);
    }
    @Override
    public PrivateConnectorParameters using(String parent, String identifier) throws UnknownResource {
        ConnectorJSON json = FindData.lookup(identifier, PrivateConnectorParameters.class);
        return (PrivateConnectorParameters) getConnectorParameters(json,"",parent);
    }

    @Override
    public ConnectorReader<PrivateConnectorParameters> name(String name) {
        return super.name(name);
    }

    @Override
    public ConnectorReader<PrivateConnectorParameters> relay(String relay) {
        return super.relay(relay);
    }

    @Override
    public ConnectorReader<PrivateConnectorParameters> site(String site) {
        return super.site(site);
    }
}
