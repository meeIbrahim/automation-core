package resource.data.readers;

import resource.data.Data;
import ui.com.ztna.web.parameters.RelayParameters;

public class RelayReader {
    String REGION;
    String MTU;
    Boolean HA;

    public RelayReader(String Region){
        this.HA = false;
        this.MTU = "1460";
        REGION = Region;
    }
    public static RelayReader anyRegion(){
        return new RelayReader(Data.getAny(Data.parameters().regions));
    }
    public RelayReader setMTU(String MTU){
        this.MTU = MTU;
        return this;
    }
    public RelayReader setHA(Boolean HA){
        this.HA = HA;
        return this;
    }
    public RelayParameters get(){
        return new RelayParameters(Data.generateName(),REGION,MTU,HA);
    }
}
