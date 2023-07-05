package resource.cached;

public class cachedNotFound extends Exception{
    public cachedNotFound(String ID) {
        super("No Cached Resource with this ID: " + ID);
    }
}
