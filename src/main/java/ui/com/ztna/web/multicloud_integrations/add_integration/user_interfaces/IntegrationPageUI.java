package ui.com.ztna.web.multicloud_integrations.add_integration.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class IntegrationPageUI {

    public static final Target ADD_INTEGRATION_BUTTON = Target.the("add integration button")
            .locatedBy("//button[.='Add Integration']");

    public static final Target AWS_INTEGRATION_TAB_BUTTON = Target.the("aws integration tab button")
            .locatedBy("//button[.='AWS Integration']");

    public static final Target AZURE_INTEGRATION_TAB_BUTTON = Target.the("azure integration tab button")
            .locatedBy("//button[.='Azure Integration']");

    public static final Target ADD_AZURE_INTEGRATION_BUTTON = Target.the("add azure integration button")
            .locatedBy("//button[.='Add Integration']");

    public static final Target ADD_GCP_INTEGRATION_BUTTON = Target.the("add GCP integration button")
            .locatedBy("//button[.='Add Integration']");

    public static final Target ADD_AWS_INTEGRATION_BUTTON = Target.the("add aws integration button")
            .locatedBy("//button[.='Add AWS Integration']");

    public static final Target AWS_INTEGRATION_ROW = Target.the("AWS Integration row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='AWS Account ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Active']]");

    public static final Target REGIONS_COUNT_ZERO = Target.the("zero regions count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='AWS Account ID']]//p[.='{1}']]//div[./span[.='Regions']]//p[text()='0']");

    public static final Target VPC_COUNT_ZERO = Target.the("zero vpc count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='AWS Account ID']]//p[.='{1}']]//div[./span[.='VPCs']]//p[text()='0']");

    public static final Target AZURE_INTEGRATION_ROW = Target.the("Azure Integration row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Subscription ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]");

    public static final Target RESOURCE_GROUPS_ZERO_COUNT = Target.the("Resource Groups zero count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Subscription ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]//div[./span[.='Resource Groups']]//p[text()='0']");

    public static final Target VNETS_ZERO_COUNT = Target.the("VNETs zero count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Subscription ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]//div[./span[.='VNETs']]//p[text()='0']");

    public static final Target GCP_INTEGRATION_ROW = Target.the("GCP Integration row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Project ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]");

    public static final Target VPCS_ZERO_COUNT = Target.the("VPCS zero count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Project ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]//div[./span[.='VPCs']]//p[text()='0']");

    public static final Target REGIONS_ZERO_COUNT = Target.the("Regions zero count")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']][.//div[./span[.='Project ID']]//p[.='{1}']][.//div[./span[.='Status']]//span[.='Linked']]//div[./span[.='Regions']]//p[text()='0']");

//    public static final Target AZURE_INTEGRATION_ROW_WITH_ASSOCIATIONS = Target.the("azure integration row with type, id and status association")
//            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[.='{0}']][.//div[.='{1}']][.//div[.='Linked']]");

    public static final Target INTEGRATION_TAB_BUTTON = Target.the("Integration tab button")
            .locatedBy("//button[@role='tab'][.//span[contains(text(), '{0}')]]");

    public static final Target INTEGRATION_TAB_BUTTON_SELECTED = Target.the("Integration tab button selected")
            .locatedBy("//button[@role='tab'][@aria-selected='true'][.//span[contains(text(), '{0}')]]");

    public static final Target INTEGRATION_ROW = Target.the("Integration row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[text()='Integration Name']]//p[text()='{0}']]");

    public static final Target INTEGRATION_ROW_OF_NAME_ACTION_BUTTON = Target.the("Integration row of name action button")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[text()='Integration Name']]//p[text()='{0}']]//button[contains(@id, 'actionIcon')]");

    public static final Target AWS_ACCOUNT_ID_OF_INTEGRATION = Target.the("AWS Account ID of integration")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[text()='Integration Name']]//p[text()='{0}']]//div[./span[text()='AWS Account ID']]//p");

    public static final Target AZURE_SUBSCRIPTION_ID_OF_INTEGRATION = Target.the("Azure Subscription ID of integration")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Integration Name']]//p[.='{0}']]//div[./span[text()='Subscription ID']]//p");

    public static final Target EDIT_INTEGRATION_DROPDOWN_OPTION = Target.the("Edit Integration dropdown option")
            .locatedBy("//div[@id='en-ztna-PopoverButtons-editIntegration'] | //div[@id='en-ztna-PopoverButtons-editGCPIntegration']");

    public static final Target DELETE_INTEGRATION_DROPDOWN_OPTION = Target.the("Delete Integration dropdown option")
            .locatedBy("//div[@id='en-ztna-PopoverButtons-removeAWSIntegration'] | //div[@id='en-ztna-PopoverButtons-removeAzureIntegration'] | //div[@id='en-ztna-PopoverButtons-removeGCPIntegration']");

    public static final String AWS = "AWS";

    public static final String Azure = "Azure";

    public static final String GCP = "GCP";
}
