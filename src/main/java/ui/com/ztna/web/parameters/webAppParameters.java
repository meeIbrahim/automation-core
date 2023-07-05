package ui.com.ztna.web.parameters;

public class webAppParameters extends ServiceParameter{
    public boolean hidden;
    public webAppParameters(String Name, String Protocol, String url, String Connector,String Site,Boolean Hidden) {
        super(Name, Protocol, url, Connector,Site);
        hidden = Hidden;
    }
}
