package ui.com.ztna.web.common.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;
import ui.com.ztna.web.policy.add_rule.models.AddRuleParameters;

import java.util.ArrayList;
import java.util.List;

public class UpdateLocationParameters extends IndexedPojo {

    public static final String RULE_NAME_KEY = "Rule Name";
    @SerializedName(RULE_NAME_KEY)
    public String ruleName = "";
    public static final String ANY_LOCATION_KEY = "Any Location";
    @SerializedName(ANY_LOCATION_KEY)
    public String anyLocation = "";

    public static final String COUNTRIES_KEY = "Countries";
    @SerializedName(COUNTRIES_KEY)
    public List<String> countries = new ArrayList<>();

    public static final String COUNTRIES_TO_REMOVE_KEY = "Countries To Remove";
    @SerializedName(COUNTRIES_TO_REMOVE_KEY)
    public List<String> countriesToRemove = new ArrayList<>();


}
