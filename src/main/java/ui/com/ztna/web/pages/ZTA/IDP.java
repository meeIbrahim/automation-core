package ui.com.ztna.web.pages.ZTA;

import ui.com.ztna.web.pages.identityProviders.tab.azure.AzureIDP;
import ui.com.ztna.web.pages.identityProviders.tab.gsuite.GSuiteIDP;

public class IDP {
    private AzureIDP azureIDP = new AzureIDP();
    private GSuiteIDP gSuiteIDP = new GSuiteIDP();

    public AzureIDP azureIDP(){return azureIDP;}
    public GSuiteIDP gSuiteIDP(){return gSuiteIDP;}
}
