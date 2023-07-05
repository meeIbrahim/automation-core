package ui.com.ztna.web.policy.delete_rule.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class DeleteRuleParameters extends IndexedPojo {

    public static final String NAME_OF_POLICY_KEY = "Name of Policy";
    @SerializedName(NAME_OF_POLICY_KEY)
    public String nameOfPolicy = "";

    public static final String RULES_KEY = "Rules";
    @SerializedName(RULES_KEY)
    public List<String> rules = new ArrayList<String>();
}
