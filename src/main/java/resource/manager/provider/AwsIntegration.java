package resource.manager.provider;


import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.provider.common.IndependentEntity;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;

public class AwsIntegration extends IndependentEntity<AWSIntegrationParameters> {

    public AwsIntegration() {
        super(new ui.com.ztna.web.pages.integrations.tabs.aws.AwsIntegration());
    }

    @Override
    protected AWSIntegrationParameters getParameters() throws UsedResource {
        return Data.awsIntegration().any();
    }
}
