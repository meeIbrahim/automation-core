package resource.data;

import indexed.pojo.model.IndexedPojo;
import resource.cached.ZTAParameters;
import resource.data.parameters.file.ConnectorJSON;
import resource.data.parameters.file.UserJSON;
import resource.data.parameters.file.services.remoteDesktopJSON;
import resource.data.parameters.file.services.secureShellJSON;
import resource.data.parameters.file.services.webAppJSON;
import ui.com.ztna.web.parameters.*;

import java.util.List;
import java.util.Objects;

/**
 *  Below Class helps to retrieve parameters by matching unique elements
 *  JSON Objects need to be added in *allJSON* in order to search them
 */
public class FindData {


    //// Iterate through all JSON Objects
    @SuppressWarnings("unchecked")
    public static <T extends IndexedPojo> T lookup(String identifier) throws UnknownResource {
        IndexedPojo found = null;
        for(JsonLookup<?> item: allJSON){
            found = item.getMatchingParams(identifier);
            if (found!=null){
                return (T) found;
            }

        }
        throw new UnknownResource(identifier);
    }


    /// Find item in specific
    @SuppressWarnings("unchecked")
    public static <T extends IndexedPojo,S extends ZTAParameters> T lookup(String identifier, Class<S> object) throws UnknownResource {
        if (object == PrivateConnectorParameters.class){
            return (T) privateConnector.getMatchingParams(identifier);
        } else if (object == webAppParameters.class) {
            return (T) webApp.getMatchingParams(identifier);
        } else if (object == secureShellParameters.class) {
            return (T) secureShell.getMatchingParams(identifier);
        } else if (object == remoteDesktopParameters.class) {
            return (T) remoteDesktop.getMatchingParams(identifier);
        } else if (object == UserParameters.class) {
            IndexedPojo found = null;
            found = localUser.getMatchingParams(identifier);
            if (found != null){return (T) found;}
            found = googleUser.getMatchingParams(identifier);
            if (found != null){return (T) found;}
            found = microsoftUser.getMatchingParams(identifier);
            if (found != null){return (T) found;}
        }
        throw new UnknownResource(identifier);
    }

    private static final JsonLookup<UserJSON> localUser = new JsonLookup<UserJSON>() {
        @Override
        public UserJSON getMatchingParams(String identifier) {
            for (UserJSON user : Data.parameters().user.local){
                if (Objects.equals(user.email, identifier)){return user;}
            }
            return null;
        }
    };
    private static final JsonLookup<UserJSON> googleUser = new JsonLookup<UserJSON>() {
        @Override
        public UserJSON getMatchingParams(String identifier) {
            for (UserJSON user : Data.parameters().user.google){
                if (Objects.equals(user.email, identifier)){return user;}
            }
            return null;
        }
    };
    private static final JsonLookup<UserJSON> microsoftUser = new JsonLookup<UserJSON>() {
        @Override
        public UserJSON getMatchingParams(String identifier) {
            for (UserJSON user : Data.parameters().user.microsoft){
                if (Objects.equals(user.email, identifier)){return user;}
            }
            return null;
        }
    };
    private static final JsonLookup<ConnectorJSON> privateConnector = new JsonLookup<ConnectorJSON>() {
        @Override
        public ConnectorJSON getMatchingParams(String identifier) {
            for (ConnectorJSON connector : Data.parameters().connector){
                if (Objects.equals(connector.host, identifier)){return connector;}
            }
            return null;
        }
    };

    private static final JsonLookup<webAppJSON> webApp = new JsonLookup<webAppJSON>() {
        @Override
        public webAppJSON getMatchingParams(String identifier) {
            for (webAppJSON app: Data.parameters().service.webApp){
                if (Objects.equals(app.url,identifier)){return app;}
            }
            return null;
        }
    };

    private static final JsonLookup<secureShellJSON> secureShell = new JsonLookup<secureShellJSON>() {
        @Override
        public secureShellJSON getMatchingParams(String identifier) {
            for (secureShellJSON app: Data.parameters().service.secureShell){
                String host = app.url + ":" + app.port;
                if (Objects.equals(host,identifier)){return app;}
            }
            return null;
        }
    };
    private static final JsonLookup<remoteDesktopJSON> remoteDesktop = new JsonLookup<remoteDesktopJSON>() {
        @Override
        public remoteDesktopJSON getMatchingParams(String identifier) {
            for (remoteDesktopJSON app: Data.parameters().service.remoteDesktop){
                String host = app.url + ":" + app.port;
                if (Objects.equals(host,identifier)){return app;}
            }
            return null;
        }
    };
    public static List<JsonLookup<?>> allJSON = List.of(
            localUser,
            googleUser,
            microsoftUser,
            privateConnector,
            webApp,
            secureShell,
            remoteDesktop
    );

}

interface JsonLookup<T extends IndexedPojo>{
    T getMatchingParams(String identifier);

}
