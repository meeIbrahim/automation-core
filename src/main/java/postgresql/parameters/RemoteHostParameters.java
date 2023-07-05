package postgresql.parameters;

import com.google.gson.annotations.SerializedName;

public class RemoteHostParameters {

    @SerializedName("host")
    public String host;

    @SerializedName("user")
    public String user;

    @SerializedName("port")
    public Integer port;

    @SerializedName("pemFilePath")
    public String pemFilePath;
}
