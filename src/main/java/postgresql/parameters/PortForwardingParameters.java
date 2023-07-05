package postgresql.parameters;

import com.google.gson.annotations.SerializedName;

public class PortForwardingParameters {

    @SerializedName("localPort")
    public Integer localPort;

    @SerializedName("remotePort")
    public Integer remotePort;

    @SerializedName("dbHost")
    public String dbHost;
}
