package ui.com.ztna.web.policy.add_policy.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class PolicyParameters extends IndexedPojo {

    public static final String NAME_OF_POLICY_KEY = "Name of Policy";
    @SerializedName(NAME_OF_POLICY_KEY)
    public String nameOfPolicy = "";

    public static final String DESCRIPTION_KEY = "Description";
    @SerializedName(DESCRIPTION_KEY)
    public String description = "";

    public static final String SELECT_RESOURCE_KEY = "Select Resource";
    @SerializedName(SELECT_RESOURCE_KEY)
    public SelectResourceParameters selectResource = new SelectResourceParameters();
}
