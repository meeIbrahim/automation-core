package resource.cached;

public interface CacheInterface <T extends  ZTAParameters>{
    void cache(String Reference, T parameters);
    void remove(String Reference);
    ZTAParameters retrieve(String Reference) throws cachedNotFound;
    void clear();
}
