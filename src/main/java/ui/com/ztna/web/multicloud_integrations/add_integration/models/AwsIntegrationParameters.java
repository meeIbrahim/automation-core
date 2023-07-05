package ui.com.ztna.web.multicloud_integrations.add_integration.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class AwsIntegrationParameters extends IndexedPojo {

    public static final String INTEGRATION_NAME_KEY = "Integration Name";
    @SerializedName(INTEGRATION_NAME_KEY)
    public String integrationName = "";

    public static final String AWS_ACCOUNT_ID_KEY = "AWS Account ID";
    @SerializedName(AWS_ACCOUNT_ID_KEY)
    public String awsAccountId = "";

    public static final String REGION_KEY = "Region";
    @SerializedName(REGION_KEY)
    public String region = "";

    public static final String AWS_PROFILE = "AWS Profile";
    @SerializedName(AWS_PROFILE)
    public String profile = "";

    public static final String AWS_MFA_SECRET = "AWS mfa secret";
    @SerializedName(AWS_MFA_SECRET)
    public String AwsMfaSecret = "";

    public static final String AWS_MFA_DEVICE_SERIAL_NO = "AWS Serial Number";
    @SerializedName(AWS_MFA_DEVICE_SERIAL_NO)
    public String awsSerialNumber = "";
}
