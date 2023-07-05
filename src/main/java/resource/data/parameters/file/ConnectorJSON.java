package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class ConnectorJSON extends IndexedPojo implements OneTimeResource {
    public static final String HOST_KEY = "host";
    @SerializedName(HOST_KEY)
    public String host = "";
    public static final String USER_KEY = "user";
    @SerializedName(USER_KEY)
    public String user = "ubuntu";
    public static final String PORT_KEY = "port";
    @SerializedName(PORT_KEY)
    public int port = 22;

    public static final String PEM_KEY = "pem";
    @SerializedName(PEM_KEY)
    public String pem = "";

    @Override
    public String primary() {
        return host;
    }
}
