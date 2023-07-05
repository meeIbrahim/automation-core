package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class DataJSON extends IndexedPojo {
    public static final String CONNECTOR_KEY = "connector";
    @SerializedName(CONNECTOR_KEY)
    public List<ConnectorJSON> connector = new ArrayList<>();

    public static final String AWS_INTEGRATION_KEY = "awsIntegration";
    @SerializedName(AWS_INTEGRATION_KEY)
    public List<AwsIntegrationJSON> awsIntegration = new ArrayList<>();
    public static final String AZURE_INTEGRATION_KEY = "azureIntegration";
    @SerializedName(AZURE_INTEGRATION_KEY)
    public List<AzureIntegrationJSON> azureIntegration = new ArrayList<>();
    public static final String AZURE_INTEGRATION_CREDENTISALS_KEY = "azureIntegrationCredentials";
    @SerializedName(AZURE_INTEGRATION_CREDENTISALS_KEY)
    public AzureIntegrationCredentialsJSON azureIntegrationCredentials;

    public static final String GCP_INTEGRATION_KEY = "gcpIntegration";
    @SerializedName(GCP_INTEGRATION_KEY)
    public List<GcpIntegrationJSON> gcpIntegration = new ArrayList<>();

    public static final String RELAYS_KEY = "relay";
    @SerializedName(RELAYS_KEY)
    public List<String> regions = new ArrayList<>();
    public static final String SERVICE_KEY = "service";
    @SerializedName(SERVICE_KEY)
    public ServicesJSON service;

    public static final String SITE_KEY = "site";
    @SerializedName(SITE_KEY)
    public SitesJSON site;

    public static final String TENANT_KEY = "tenant";
    @SerializedName(TENANT_KEY)
    public UserJSON tenant;
    public static final String SECOND_TENANT_KEY = "tenantTwo";
    @SerializedName(SECOND_TENANT_KEY)
    public UserJSON tenantTwo;

    public static final String USERS_KEY = "user";
    @SerializedName(USERS_KEY)
    public UsersJSON user;

    public static final String SPLUNK_KEY = "splunk";
    @SerializedName(SPLUNK_KEY)
    public SplunkJSON splunk;
}
