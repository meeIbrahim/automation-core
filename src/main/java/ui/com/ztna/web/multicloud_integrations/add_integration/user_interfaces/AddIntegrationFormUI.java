package ui.com.ztna.web.multicloud_integrations.add_integration.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class AddIntegrationFormUI {

    public static final Target ADD_AWS_INTEGRATION_FORM_TITLE = Target.the("Add AWS Integration form title")
            .locatedBy("//div[@id='customized-dialog-title']//h2[text()='Add AWS Integration']");

    public static final Target ADD_AWS_INTEGRATION_FORM_CLOSE_BUTTON = Target.the("Add AWS Integration form close button")
            .locatedBy("//div[@role='dialog'][.//div[@id='customized-dialog-title']//h2[text()='Add AWS Integration']]//div[@role='button']");

    public static final Target ADD_AZURE_INTEGRATION_FORM_CLOSE_BUTTON = Target.the("Add AZURE Integration form close button")
            .locatedBy("//div[@role='dialog'][.//div[@id='customized-dialog-title']//h2[text()='Add Azure Integration']]//div[@role='button']");

    public static final Target ADD_GCP_INTEGRATION_FORM_CLOSE_BUTTON = Target.the("Add GCP Integration form close button")
            .locatedBy("//div[@role='dialog'][.//div[@id='customized-dialog-title']//h2[text()='Add GCP Integration']]//div[@role='button']");

    public static final Target ADD_AZURE_INTEGRATION_FORM_TITLE = Target.the("Add Azure Integration form title")
            .locatedBy("//div[@id='customized-dialog-title']//h2[text()='Add Azure Integration']");

    public static final Target ADD_GCP_INTEGRATION_FORM_TITLE = Target.the("Add GCP Integration form title")
            .locatedBy("//div[@id='customized-dialog-title']//h2[text()='Add GCP Integration']");

    public static final Target CONTINUE_BUTTON = Target.the("continue button")
            .locatedBy("//button[.='Continue']");

    public static final Target NEXT_BUTTON = Target.the("Next button")
            .locatedBy("//button[.='Next']");

    public static final Target INTEGRATE_AND_VALIDATE_BUTTON = Target.the("Integrate and Validate button")
            .locatedBy("//button[.='Integrate & Validate']");

    public static final Target ALREADY_EXIST_MESSAGE_FOR_AWS = Target.the("already exist message for AWS")
            .locatedBy("//p[text()='Aws account with this arn already exists']");

    public static final Target ALREADY_EXIST_MESSAGE_FOR_AZURE = Target.the("already exist message for AZURE")
            .locatedBy("//p[text()='Azure Integration with these details already exists']");

    public static final Target TRYING_TO_VALIDATE_ICON = Target.the("Trying to Validate icon")
            .locatedBy("//div[./div/img][./div/p[contains(text(), 'trying to validate')]]");

    public static final Target TRY_TO_VALIDATE_AGAIN_MESSAGE = Target.the("Try to validate again message")
            .locatedBy("//div[./div/p[contains(text(), 'Please try to validate again')]]");

    public static final Target TRY_AGAIN_BUTTON = Target.the("Try Again Button")
            .locatedBy("//div[./div/p[contains(text(), 'Please try to validate again')]]//button[./span[text()='Try Again']]");

    public static final Target GENERATE_SCRIPT_BUTTON = Target.the("generate script button")
            .locatedBy("//div//span[text()='Generate CloudFormation Stack Creation Script']");

    public static final Target INTEGRATION_NAME_INPUT_FIELD = Target.the("Integration Name input field")
            .locatedBy("//div//input[@name='integrationName']");

    public static final Target AWS_ACCESS_KEY_ID_INPUT_FIELD = Target.the("AWS Access Key ID input field")
            .locatedBy("//div//input[@name='awsAccessKeyId']");

    public static final Target AWS_SECRET_INPUT_FIELD = Target.the("AWS Secret input field")
            .locatedBy("//div//input[@name='awsSecret']");

    public static final Target SESSION_TOKEN_INPUT_FIELD = Target.the("Session Token input field")
            .locatedBy("//div//input[@name='sessionToken']");

    public static final Target AWS_ACCOUNT_ID_FIELD = Target.the("aws account id input field")
            .locatedBy("//div//input[@name='awsAccountId']");

    public static final Target PROCEED_BUTTON = Target.the("proceed button")
            .locatedBy("//button[.//span[text()='Proceed']]");

    public static final Target DONE_BUTTON = Target.the("done button")
            .locatedBy("//button[.//span[text()='Done']]");

    public static final Target CLOSE_BUTTON = Target.the("close button")
            .locatedBy("//button[.=' Close']");

    public static final Target CLOSE_NEW_BUTTON = Target.the("close button")
            .locatedBy("//button[.='Close']");

    public static final Target DONE_BUTTONS = Target.the("create project form title")
            .locatedBy("#en-ztna-validateIntegration-AddMultiCloudIntegration");

    public static final Target SUCCESS_AWS_TEXT = Target.the("success AWS text")
            .locatedBy("//div/p[text()='Your AWS account has been successfully connected with ExtremeCloud Zero Trust Access.']");

    public static final Target FAILED_AWS_TEXT = Target.the("failed AWS text")
            .locatedBy("//div/p[text()='aws cloud integration with this aws arn already exists.']");

    public static final Target SUCCESS_AZURE_TEXT = Target.the("success Azure text")
            .locatedBy("//div/p[text()='Your Azure account has been successfully connected with ExtremeCloud Zero Trust Access.']");

    public static final Target FAILED_AZURE_TEXT = Target.the("failed Azure text")
            .locatedBy("//div/p[text()='Azure Integration with these details already exists']");

    public static final Target SUCCESS_GCP_TEXT = Target.the("success GCP text")
            .locatedBy("//div/p[text()='Your GCP account has been successfully connected with ExtremeCloud Zero Trust Access.']");

    public static final Target FAILED_GCP_TEXT = Target.the("failed GCP text")
            .locatedBy("//div/p[text()='Integration with this project ID already exists.']");

    public static final Target AZURE_INTEGRATION_NAME_INPUT_FIELD = Target.the("Azure Integration Name input field")
            .locatedBy("//input[@placeholder='Enter integration name']");

    public static final Target GCP_INTEGRATION_NAME_INPUT_FIELD = Target.the("GCP Integration Name input field")
            .locatedBy("//input[@name='integrationName']");

    public static final Target GCP_PROJECT_ID_INPUT_FIELD = Target.the("GCP Project ID input field")
            .locatedBy("//input[@name='projectID']");

    public static final Target SERVICE_ACCOUNT_KEY_INPUT_FIELD = Target.the("Service Account Key input field")
            .locatedBy("//input[@id='file']");

    public static final Target SUBSCRIPTION_ID_INPUT_FIELD = Target.the("Subscription ID input field")
            .locatedBy("//input[@placeholder='Enter subscription ID']");

    public static final Target TENANT_ID_INPUT_FIELD = Target.the("Tenant ID input field")
            .locatedBy("//input[@placeholder='Enter tenant ID']");

    public static final Target APPLICATION_CLIENT_ID_INPUT_FIELD = Target.the("Application Client ID input field")
            .locatedBy("//input[@placeholder='Enter application client ID']");

    public static final Target APPLICATION_CLIENT_SECRET_INPUT_FIELD = Target.the("Application Client Secret input field")
            .locatedBy("//input[@placeholder='Enter application client secret']");

    public static final Target OBJECT_ID_INPUT_FIELD = Target.the("Object ID input field")
            .locatedBy("//input[@placeholder='Enter object ID']");
}
