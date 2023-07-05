package resource.data.readers;

import resource.cached.ZTAParameters;
import resource.data.Data;
import resource.data.FindData;
import resource.data.MatchingResourceNotFound;
import resource.data.UnknownResource;
import resource.data.parameters.file.ServicesJSON;
import resource.data.parameters.file.services.ServiceJSON;
import resource.data.parameters.file.services.remoteDesktopJSON;
import resource.data.parameters.file.services.secureShellJSON;
import resource.data.parameters.file.services.webAppJSON;
import ui.com.ztna.web.parameters.*;

import java.util.List;
import java.util.Objects;

public class ServiceReader<T extends ServiceJSON, S extends ServiceParameter> implements reverseLookup<S> {
    public static ServicesJSON SERVICES = Data.parameters().service;
    String connector;
    String site = "";
    List<T> services;
    Class<S> parameters;

    public static ServiceReader<webAppJSON,webAppParameters> webApp(){
        return new ServiceReader<webAppJSON,webAppParameters>(SERVICES.webApp,webAppParameters.class);
    }
    public static ServiceReader<secureShellJSON,secureShellParameters> secureShell(){
        return new ServiceReader<secureShellJSON,secureShellParameters>(SERVICES.secureShell, secureShellParameters.class);
    }
    public static ServiceReader<remoteDesktopJSON,remoteDesktopParameters> remoteDesktop(){
        return new ServiceReader<remoteDesktopJSON,remoteDesktopParameters>(SERVICES.remoteDesktop, remoteDesktopParameters.class);
    }

    public ServiceReader(List<T> resources, Class<S> parameters){
        this.services = resources;
        this.parameters = parameters;
    }
    public  ServiceReader<T,S> connector(String Connector){
        this.connector = Connector;
        return this;
    }

    public ServiceReader<T,S> connector(ZTAParameters Connector){
        this.connector = Connector.name;
        this.site = Connector.parent;
        return this;
    }
    public S any(){
        return getServiceParameters(connector,site,Data.getAny(services));
    }

    public S withProtocol(String protocol) throws MatchingResourceNotFound {
        for (T service : services){
            if (Objects.equals(service.protocol, protocol)){
                return getServiceParameters(connector,site,service);
            }
        }
        throw new MatchingResourceNotFound(protocol);
    }

    @SuppressWarnings("unchecked")
    private S getServiceParameters(String Connector,String Site, T JSON) {
        if (this.parameters == webAppParameters.class){
            return (S) new webAppParameters(Data.generateName(),JSON.protocol,JSON.url,Connector,Site,true);
        } else if (this.parameters == secureShellParameters.class) {
            secureShellJSON newJSON = (secureShellJSON) JSON;
            return (S) new secureShellParameters(Data.generateName(),newJSON.protocol,newJSON.url,newJSON.port,Connector,Site);
        } else if (this.parameters == remoteDesktopParameters.class) {
            remoteDesktopJSON newJSON = (remoteDesktopJSON) JSON;
            return (S) new remoteDesktopParameters(Data.generateName(),newJSON.protocol,newJSON.url,newJSON.port,Connector,Site);
        }
        else { throw new IllegalArgumentException();}
    }


    @Override
    public S using(String Parent, String identifier) throws UnknownResource {
        T json = FindData.lookup(identifier);
        return getServiceParameters(Parent,"",json);
    }
}
