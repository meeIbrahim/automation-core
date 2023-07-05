package resource.cached;

public class ZTAParameters {
    public String name;
    public String parent = "";
    public String identifier = "";

    public ZTAParameters(String name,String identifier,String parent){
        this.name = name;
        this.identifier = identifier;
        this.parent = parent;
    }

    public String getName(){
        return name;
    }
}
