package ui.com.ztna.web.policy.update_policy.model;

import com.google.gson.annotations.SerializedName;
import ui.com.ztna.web.policy.add_policy.models.PolicyParameters;

public class Update_Policy_Parameters extends PolicyParameters {

    public static final String EDITED_POLICY_NAME_KEY = "Edited Policy Name";
    @SerializedName(EDITED_POLICY_NAME_KEY)
    public String editedPolicyName = "";

    public static final String NEW_DESCRIPTION_KEY = "New Description";
    @SerializedName(NEW_DESCRIPTION_KEY)
    public String newDescription = "";



}
