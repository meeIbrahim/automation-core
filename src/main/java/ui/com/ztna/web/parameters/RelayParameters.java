package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class RelayParameters extends ZTAParameters {
    public String region;
    public String mtu;
    public Boolean ha;
    public RelayParameters(String Name,String region,String MTU,Boolean HA){
        super(Name,region,"");
        this.region = region;
        this.mtu = MTU;
        this.ha = HA;
    }

}
