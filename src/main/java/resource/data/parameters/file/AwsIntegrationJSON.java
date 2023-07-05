package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class AwsIntegrationJSON extends IndexedPojo implements OneTimeResource {
    public static final String AWS_ACCOUNT_ID = "awsAccountId";
    @SerializedName(AWS_ACCOUNT_ID)
    public String awsAccountId = "";


    @Override
    public String primary() {
        return awsAccountId;
    }
}
