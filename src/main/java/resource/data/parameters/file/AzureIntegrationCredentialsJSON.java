package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class AzureIntegrationCredentialsJSON extends IndexedPojo implements OneTimeResource {
    public static final String AZURE_TENANT_ID = "tenantId";
    @SerializedName(AZURE_TENANT_ID)
    public String tenantId = "";
    public static final String AZURE_APPLICATION_CLIENT_ID = "ApplicationClientId";
    @SerializedName(AZURE_APPLICATION_CLIENT_ID)
    public String ApplicationClientId = "";
    public static final String AZURE_OBJECT_ID = "ObjectId";
    @SerializedName(AZURE_OBJECT_ID)
    public String ObjectId = "";
    public static final String AZURE_APPLICATION_CLIENT_SECRET = "ApplicationClientSecret";
    @SerializedName(AZURE_APPLICATION_CLIENT_SECRET)
    public String ApplicationClientSecret = "";


    @Override
    public String primary() {
        return null;
    }
}
