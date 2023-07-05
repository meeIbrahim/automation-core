package ui.com.ztna.web.policy.Update_Rule.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;
import ui.com.ztna.web.common.model.UpdateLocationParameters;
import ui.com.ztna.web.common.model.UpdateNameParameters;
import ui.com.ztna.web.common.model.UpdateTimeParameters;
import ui.com.ztna.web.policy.add_rule.models.AddRuleParameters;

public class UpdateRuleParameters extends IndexedPojo {

    public static final String RULE_KEY = "Rule";
    @SerializedName(RULE_KEY)
    public AddRuleParameters addRuleParameters = new AddRuleParameters();

    public static final String UPDATE_NAME_KEY = "Update Name";
    @SerializedName(UPDATE_NAME_KEY)
    public UpdateNameParameters updateNameParameters = new UpdateNameParameters();

    public static final String UPDATE_TIME_KEY = "Update Time";
    @SerializedName(UPDATE_TIME_KEY)
    public UpdateTimeParameters updateTimeParameters =new UpdateTimeParameters();


}
