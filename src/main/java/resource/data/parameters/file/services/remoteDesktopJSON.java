package resource.data.parameters.file.services;

import com.google.gson.annotations.SerializedName;

public class remoteDesktopJSON extends ServiceJSON{
    public static final String PORT_KET = "port";
    @SerializedName(PORT_KET)
    public String port = "";


}
