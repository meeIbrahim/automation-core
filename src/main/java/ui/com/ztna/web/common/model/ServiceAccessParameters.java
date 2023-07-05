package ui.com.ztna.web.common.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class ServiceAccessParameters extends IndexedPojo {

    public static final String SERVICE_NAME_KEY =  "Service Name";
    @SerializedName(SERVICE_NAME_KEY)
    public String serviceName = "";

    public static final String SERVICE_SECRET_KEY =  "Service Secret";
    @SerializedName(SERVICE_SECRET_KEY)
    public String serviceSecret = "";



}
