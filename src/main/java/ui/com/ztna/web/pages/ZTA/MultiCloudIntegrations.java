package ui.com.ztna.web.pages.ZTA;

import ui.com.ztna.web.pages.integrations.tabs.aws.AwsIntegration;
import ui.com.ztna.web.pages.integrations.tabs.azure.AzureIntegration;
import ui.com.ztna.web.pages.integrations.tabs.gcp.GcpIntegration;

public class MultiCloudIntegrations {
    private AwsIntegration awsIntegration= new AwsIntegration();
    private AzureIntegration azureIntegration=new AzureIntegration();
    private GcpIntegration gcpIntegration=new GcpIntegration();


    public AwsIntegration awsIntegration(){return awsIntegration;}
    public AzureIntegration azureIntegration(){return azureIntegration;}
    public GcpIntegration gcpIntegration(){return gcpIntegration;}

}
