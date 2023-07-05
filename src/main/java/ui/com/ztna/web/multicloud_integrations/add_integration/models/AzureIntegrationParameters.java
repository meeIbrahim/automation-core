package ui.com.ztna.web.multicloud_integrations.add_integration.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class AzureIntegrationParameters extends IndexedPojo {

    public static final String INTEGRATION_NAME_KEY = "Integration Name";
    @SerializedName(INTEGRATION_NAME_KEY)
    public String integrationName = "";

    public static final String SUBSCRIPTION_ID_KEY = "Subscription ID";
    @SerializedName(SUBSCRIPTION_ID_KEY)
    public String subscriptionId = "";

    public static final String TENANT_ID_KEY = "Tenant ID";
    @SerializedName(TENANT_ID_KEY)
    public String tenantId = "";

    public static final String APPLICATION_CLIENT_ID_KEY = "Application Client ID";
    @SerializedName(APPLICATION_CLIENT_ID_KEY)
    public String applicationClientId = "";

    public static final String OBJECT_ID_KEY = "Object ID";
    @SerializedName(OBJECT_ID_KEY)
    public String objectId = "";

    public static final String APPLICATION_CLIENT_SECRET_KEY = "Application Client Secret";
    @SerializedName(APPLICATION_CLIENT_SECRET_KEY)
    public String applicationClientSecret = "";
}
