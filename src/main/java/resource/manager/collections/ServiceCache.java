package resource.manager.collections;


import lombok.SneakyThrows;
import resource.cached.Cache;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.parameters.ServiceParameter;

import java.util.ArrayList;
import java.util.List;

public class ServiceCache {

    List<Cache<? extends ServiceParameter>> services;

    @SafeVarargs
    public ServiceCache(Cache<? extends ServiceParameter>... Services){
        this.services = List.of(Services);
    }

    @SneakyThrows
    public ZTAParameters retrieve(String Reference) throws cachedNotFound {
        ZTAParameters toReturn;
        for (Cache<?> cache : services){
            try {
            toReturn = cache.retrieve(Reference);
            return toReturn;
            }
            catch (Exception ignored){}
        }
        throw new cachedNotFound(Reference);
    }


}
