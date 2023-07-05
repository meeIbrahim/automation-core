package ui.com.ztna.web.policy.add_policy.models;

import com.google.gson.annotations.SerializedName;

public class SelectResourceParameters {

    public static final String RESOURCE_TYPE_KEY = "Resource Type";
    @SerializedName(RESOURCE_TYPE_KEY)
    public String resourceType = "";

    public static final String RESOURCE_NAME_KEY = "Resource Name";
    @SerializedName(RESOURCE_NAME_KEY)
    public String resourceName = "";
}
