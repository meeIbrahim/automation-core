package resource.data.readers;

import resource.data.Data;
import resource.data.parameters.file.SplunkJSON;
import ui.com.ztna.web.parameters.SplunkParameters;

public class SplunkReader {
    SplunkJSON json;
    Boolean SSL;
    Boolean isHTTPS;

    public SplunkReader(){
        this.json = Data.parameters().splunk;
        this.isHTTPS = this.json.protocol.toLowerCase().contains("https");
        this.SSL = false;
    }

    public SplunkReader verifySSL(){
        this.SSL = true;
        return this;
    }
    public SplunkParameters get(){
        return new SplunkParameters(json.host, json.port, json.token, isHTTPS,SSL);
    }

}
