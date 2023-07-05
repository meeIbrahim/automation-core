package resource.manager.provider;


import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.provider.common.IndependentEntity;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.AzureIntegrationParameters;

public class AzureIntegration extends IndependentEntity<AzureIntegrationParameters> {

    public AzureIntegration() {
        super(new ui.com.ztna.web.pages.integrations.tabs.azure.AzureIntegration());
    }

    @Override
    protected AzureIntegrationParameters getParameters() throws UsedResource {
        return Data.azureIntegration().any();
    }
}
