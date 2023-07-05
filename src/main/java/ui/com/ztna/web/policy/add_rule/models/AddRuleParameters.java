package ui.com.ztna.web.policy.add_rule.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;
import ui.com.ztna.web.common.model.ServiceAccessParameters;
import ui.com.ztna.web.common.model.UpdateDevicePostureParameters;
import ui.com.ztna.web.common.model.UpdateLocationParameters;

import java.util.ArrayList;
import java.util.List;

public class AddRuleParameters extends IndexedPojo {

    public static final String POLICY_KEY = "Policy";
    @SerializedName(POLICY_KEY)
    public String policy = "";

    public static final String RULES_KEY = "Rules";
    @SerializedName(RULES_KEY)
    public List<RuleParameters> rules = new ArrayList<RuleParameters>();


    public static final String SERVICE_ACCESS_KEY =  "Service Access";
    @SerializedName(SERVICE_ACCESS_KEY)
    public List<ServiceAccessParameters> serviceAccess = new ArrayList<>();

    public static final String UPDATE_LOCATION_KEY = "Update Location";
    @SerializedName(UPDATE_LOCATION_KEY)
    public UpdateLocationParameters updateLocationParameters =new UpdateLocationParameters();

    public static final String UPDATE_DEVICE_POSTURE_KEY = "Update Device Posture";
    @SerializedName(UPDATE_DEVICE_POSTURE_KEY)
    public UpdateDevicePostureParameters updateDevicePostureParameters =new UpdateDevicePostureParameters();




}
