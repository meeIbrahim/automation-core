package resource.cached;

import indexed.pojo.model.IndexedPojo;
import io.cucumber.java.bs.A;

import java.util.*;

public class Cache <T extends ZTAParameters> implements CacheInterface<T>{
    public static List<Cache<?>> ALL = new ArrayList<>();
    public HashMap<String,ZTAParameters> data = new HashMap<>();

    public static void clearAll(){
        for(Cache<?> item: Cache.ALL){
            item.clear();
        }
    }
    public Cache(){
        Cache.ALL.add(this);
    }
    public void clear(){
        data.clear();
    }
    @Override
    public ZTAParameters retrieve(String Reference) throws cachedNotFound {
        ZTAParameters toReturn = data.get(Reference);
        if (toReturn==null){throw new cachedNotFound(Reference);}
        return data.get(Reference);
    }

    public Boolean isPresent(String Reference){
        String name="";
        try {
            name = data.get(Reference).name;
        }catch (NullPointerException e){
        }
        return !name.isEmpty();
    }

    public void update(String Reference, ZTAParameters parameters){
        data.replace(Reference,parameters);
    }

    public String lookFor(String Name){
        for (Map.Entry<String,ZTAParameters> entry : data.entrySet()){
            if (Objects.equals(Name, entry.getValue().name)){
                return entry.getKey();
            }
        }
        return "";
    }
    public List<ZTAParameters> allCached(){
        return new ArrayList<>(data.values());
    }
    @Override
    public void cache(String Reference,ZTAParameters parameters){
        data.put(Reference,parameters);
    }
    @Override

    public void remove(String Reference){
        data.remove(Reference);
    }

}
