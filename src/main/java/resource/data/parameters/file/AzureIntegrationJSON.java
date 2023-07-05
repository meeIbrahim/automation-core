package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class AzureIntegrationJSON extends IndexedPojo implements OneTimeResource {
    public static final String AZURE_SUBSCRIPTION_ID = "azureSubscriptionId";
    @SerializedName(AZURE_SUBSCRIPTION_ID)
    public String azureSubscriptionId = "";


    @Override
    public String primary() {
        return azureSubscriptionId;
    }
}
