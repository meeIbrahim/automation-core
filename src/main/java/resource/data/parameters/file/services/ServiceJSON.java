package resource.data.parameters.file.services;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class ServiceJSON extends IndexedPojo{
    public static final String DOMAIN_KEY = "url";
    @SerializedName(DOMAIN_KEY)
    public String url = "";

    public static final String NAME_KEY = "name";
    @SerializedName(NAME_KEY)
    public String name = "";
    public static final String PROTOCOL_KEY = "protocol";
    @SerializedName(PROTOCOL_KEY)
    public String protocol = "";
}
