package ui.com.ztna.web.multicloud_integrations.add_integration.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class IntegrationParameters extends IndexedPojo {

    public static final String AWS_INTEGRATIONS = "AWS Integrations";
    @SerializedName(AWS_INTEGRATIONS)
    public List<AwsIntegrationParameters> awsIntegrations = new ArrayList<>();

    public static final String AZURE_INTEGRATIONS = "Azure Integrations";
    @SerializedName(AZURE_INTEGRATIONS)
    public List<AzureIntegrationParameters> azureIntegrations = new ArrayList<>();

    public static final String GCP_INTEGRATIONS = "GCP Integrations";
    @SerializedName(GCP_INTEGRATIONS)
    public List<GcpIntegrationParameters> gcpIntegrations = new ArrayList<>();

    public static final String INTEGRATION_NAMES_KEY = "Integration Names";
    @SerializedName(INTEGRATION_NAMES_KEY)
    public List<String> integrationNames = new ArrayList<>();
}

