package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class SplunkJSON extends IndexedPojo {
    public static final String HOST_KEY = "host";
    @SerializedName(HOST_KEY)
    public String host;
    public static final String PORT_KEY = "port";
    @SerializedName(PORT_KEY)
    public String port;
    public static final String PROTOCOL_KEY = "protocol";
    @SerializedName(PROTOCOL_KEY)
    public String protocol;
    public static final String TOKEN_KEY = "token";
    @SerializedName(TOKEN_KEY)
    public String token;
}
