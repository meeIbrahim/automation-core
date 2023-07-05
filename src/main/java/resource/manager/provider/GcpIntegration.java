package resource.manager.provider;


import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.provider.common.IndependentEntity;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.GCPIntegrationParameters;

public class GcpIntegration extends IndependentEntity<GCPIntegrationParameters> {

    public GcpIntegration() {
        super(new ui.com.ztna.web.pages.integrations.tabs.gcp.GcpIntegration());
    }

    @Override
    protected GCPIntegrationParameters getParameters() throws UsedResource {
        return Data.gcpIntegration().any();
    }
}
