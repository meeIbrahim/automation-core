package ui.com.ztna.web.policy.manage_policy_activation.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class PolicyInactivationParameters extends IndexedPojo {

    public static final String NAME_OF_POLICY_KEY = "Policy Name";
    @SerializedName(NAME_OF_POLICY_KEY)
    public String nameOfPolicy = "";



}
