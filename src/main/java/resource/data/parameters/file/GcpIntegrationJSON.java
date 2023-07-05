package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class GcpIntegrationJSON extends IndexedPojo implements OneTimeResource {
    public static final String GCP_PROJECT_ID = "gcpProjectId";
    @SerializedName(GCP_PROJECT_ID)
    public String gcpProjectId = "";

    public static final String SERVICE_ACCOUNT_KEY_FILE_PATH_KEY = "serviceAccountKeyFilePath";
    @SerializedName(SERVICE_ACCOUNT_KEY_FILE_PATH_KEY)
    public String serviceAccountKeyFilePath = "";


    @Override
    public String primary() {
        return gcpProjectId;
    }
}
