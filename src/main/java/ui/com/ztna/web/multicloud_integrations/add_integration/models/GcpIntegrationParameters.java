package ui.com.ztna.web.multicloud_integrations.add_integration.models;

import com.google.gson.annotations.SerializedName;

public class GcpIntegrationParameters {

    public static final String INTEGRATION_NAME_KEY = "Integration Name";
    @SerializedName(INTEGRATION_NAME_KEY)
    public String integrationName = "";

    public static final String PROJECT_ID_KEY = "Project ID";
    @SerializedName(PROJECT_ID_KEY)
    public String projectId = "";

    public static final String SERVICE_ACCOUNT_KEY_FILE_PATH_KEY = "Service Account Key File Path";
    @SerializedName(SERVICE_ACCOUNT_KEY_FILE_PATH_KEY)
    public String serviceAccountKeyFilePath = "";
}
