package resource.cached;

import resource.manager.provider.*;
import resource.manager.provider.common.resourceEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Deprecated
public class Cached {
    /// FOR FUTURE: Provider Specific Caches

    private static HashMap<String,String> users = new HashMap<>();
    private static HashMap<String,String> accessGroups = new HashMap<>();
    private static HashMap<String,String> relays = new HashMap<>();
    private static HashMap<String,String> sites = new HashMap<>();

    private static HashMap<String,String> connectors = new HashMap<>();
    private static HashMap<String,String> services = new HashMap<>();
    private static HashMap<String,String> projects = new HashMap<>();
    private static HashMap<String,String> policies = new HashMap<>();
    private static HashMap<String,String> rules = new HashMap<>();

    private static final List<HashMap<String,String>> all = List.of(
            users,
            accessGroups,
            relays,
            services,
            connectors,
            projects,
            policies,
            rules,
            sites
    );

    public static void clean(){
        for(HashMap<String,String> item: all){
            item.clear();
        }
    }

    public static <S extends resourceEntity> String retrieve(String Reference, Class<S> object){
        for(Map.Entry<String,String> entry: getMap(object).entrySet()){
            if (Objects.equals(entry.getValue(), Reference)){return entry.getKey();}
        }
        return "";
    }

    public static <T extends ZTAParameters,S extends resourceEntity> void cache(String Reference, T parameters, Class<S> object){
    getMap(object).put(parameters.name, Reference);
    }

    public static <S extends resourceEntity> void cache(String Reference, String name, Class<S> object){
        getMap(object).put(name, Reference);
    }

    public static <S extends resourceEntity> void remove(String Reference, Class<S> object){
        String name = retrieve(Reference,object);
        if(!Objects.equals(name, "")){
            getMap(object).remove(name);
        }

    }

//    public static <S extends resourceEntity> IndexedPojo parameters(String name, Class<S> object) throws UnknownResource {
//        if (object == User.class){
//            return FindData.user(name);
//        }
//        else {throw new IllegalArgumentException();}
//    }

    private static <T extends resourceEntity> HashMap<String,String> getMap(Class<T> object){
        HashMap<String,String> map;
        if (object == User.class){
            map = users;
        } else if (object == AccessGroup.class) {
            map = accessGroups;
        } else if (object == Relay.class) {
            map = relays;
        } else if (object == Site.class) {
            map = sites;
        } else if (object == Connector.class) {
            map = connectors;
        } else if (object == webApp.class) {
            map = services;
        } else if (object == ApplicationGroup.class){
            map = projects;
        } else if (object == Policy.class){
            map = policies;
        } else if (object == Rule.class) {
            map = rules;
        }
        else {throw new IllegalArgumentException();}
        return map;
    }

}
