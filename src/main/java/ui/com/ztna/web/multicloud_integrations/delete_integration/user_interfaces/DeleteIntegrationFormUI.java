package ui.com.ztna.web.multicloud_integrations.delete_integration.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class DeleteIntegrationFormUI {

    public static final Target DELETE_INTEGRATION_FORM = Target.the("Delete Integration form")
            .locatedBy("//div[@role='dialog'][.//h2[text()='Remove Integration']]");

//    public static final Target AWS_ACCOUNT_MENU_BUTTON = Target.the("AWS Account Menu Button")
//            .locatedBy("//div[./div/div/div/span/div[.='{0}']]//button[contains(@id,'en-ztna-CardRow')]");
//
//    public static final Target DELETE_AWS_ACCOUNT_BUTTON = Target.the("Delete AWS Account Button")
//            .locatedBy("#en-ztna-PopoverButtons-deleteAwsIntegration");

    public static final Target PROCEED_CONTINUE_BUTTON = Target.the("Proceed/Continue Button")
            .locatedBy("//button/span[.='Proceed'] | //button/span[.='Continue']");

    public static final Target AWS_ID_INPUT_FIELD = Target.the("AWS ID input field")
            .locatedBy("//input[@name='accountId'][@placeholder='Enter AWS ID']");

    public static final Target SUBSCRIPTION_ID_INPUT_FIELD = Target.the("Subscription ID input field")
            .locatedBy("//input[@name='inputSubscriptionId']");

    public static final Target DELETE_INTEGRATION_BUTTON = Target.the("Delete Integration Button")
            .locatedBy("//button[.//span[text()='Remove Integration']]");

    public static final Target SITES_ASSOCIATION = Target.the("Sites association")
            .locatedBy("//div[contains(@class, 'MuiDialogContent-root')]//p[contains(@class,'MuiTypography-root')][contains(.,'Sites')]");

    public static final Target SITES_ASSOCIATIONS_LINK = Target.the("Sites associations link")
            .locatedBy("//div[contains(@class, 'MuiDialogContent-root')]//p[contains(@class,'MuiTypography-root')][contains(.,'Sites')]//a");

    public static final Target OK_BUTTON = Target.the("ok button")
            .locatedBy("//button[.//span[.='OK']]");
}
