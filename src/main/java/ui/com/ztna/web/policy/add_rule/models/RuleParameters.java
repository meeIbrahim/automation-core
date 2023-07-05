package ui.com.ztna.web.policy.add_rule.models;

import com.google.gson.annotations.SerializedName;
import ui.com.ztna.web.common.model.UpdateLocationParameters;

import java.util.ArrayList;
import java.util.List;

public class RuleParameters {

    public static final String RULE_TYPE_KEY = "Rule Type";
    @SerializedName(RULE_TYPE_KEY)
    public String ruleType = "";

    public static final String RULE_RECIPIENT_KEY = "Rule Recipient";
    @SerializedName(RULE_RECIPIENT_KEY)
    public String ruleRecipient = "";

    public static final String RULE_NAME_KEY = "Rule Name";
    @SerializedName(RULE_NAME_KEY)
    public String ruleName = "";

    public static final String ACCESS_TIME_KEY = "Access Time";
    @SerializedName(ACCESS_TIME_KEY)
    public String accessTime = "any";

    public static final String START_TIME_KEY = "Start Time";
    @SerializedName(START_TIME_KEY)
    public String startTime = "";

    public static final String END_TIME_KEY = "End Time";
    @SerializedName(END_TIME_KEY)
    public String endTime = "";

    public static final String LOCATION_KEY = "Access Location";
    @SerializedName(LOCATION_KEY)
    public String accessLocation = "any";

    public static final String COUNTRIES_KEY = "Countries";
    @SerializedName(COUNTRIES_KEY)
    public List<String> countries = new ArrayList<>();

    public static final String START_TIME_AFTER_HOURS_KEY = "Start Time After Hours";
    @SerializedName(START_TIME_AFTER_HOURS_KEY)
    public String startTimeAfterHours = "";

    public static final String START_TIME_AFTER_MINS_KEY = "Start Time After Mins";
    @SerializedName(START_TIME_AFTER_MINS_KEY)
    public String startTimeAfterMins = "";

    public static final String END_TIME_AFTER_HOURS_KEY = "End Time After Hours";
    @SerializedName(END_TIME_AFTER_HOURS_KEY)
    public String endTimeAfterHours = "";

    public static final String END_TIME_AFTER_MINS_KEY = "End Time After Mins";
    @SerializedName(END_TIME_AFTER_MINS_KEY)
    public String endTimeAfterMins = "";

    public static final String DEVICE_POSTURE_KEY = "Device Posture";
    @SerializedName(DEVICE_POSTURE_KEY)
    public String devicePosture = "";


}