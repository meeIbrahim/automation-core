package ui.com.ztna.web.xiq_integration.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class ExtremeCloudXIQUI {
    public static final Target INTEGRATIONS_ICON = Target.the("integrations page icon")
            .locatedBy("(//div/*[name()='svg'])[6]");
    public static final Target EXTREME_CLOUD_IQ_TEXT = Target.the("extreme cloud IQ text")
            .locatedBy("(//div[@class='MuiListItemText-root']/span[.=text()])[3]");
    public static final Target INTEGRATE_EXTREME_CLOUD_IQ_Button = Target.the("Integrate extreme cloud IQ Button")
            .locatedBy("(//div/button/span[.=text()])[2]");
    public static final Target XIQ_CONFIRM_BUTTON = Target.the("confirm button")
            .locatedBy("//button[@id='en-ztna-modal-saveButton']");
    public static final Target SYNC_BUTTON = Target.the("sync button")
            .locatedBy("(//div/button)[3]");
    public static final Target INTEGRATION_SUCCESS_MESSAGE = Target.the("success message")
            .locatedBy("//div/p[contains(@class,'MuiTypography-subtitle1')]");
    public static final Target PROFILE_ID_COLUMN = Target.the("profile_id column")
            .locatedBy("//div/h6[text()='Profile ID']");
    public static final Target PROFILE_NAME_COLUMN = Target.the("profile_name column")
            .locatedBy("//div/h6[text()='Profile Name']");

    public static final Target EXTREME_CLOUD_IQ_PAGE_HEADING = Target.the("page heading")
            .locatedBy("//div/span[text()='ExtremeCloud IQ']");
    public static final Target EXTREME_CLOUD_IQ_PAGE_TITLE = Target.the("page title")
            .locatedBy("(//div/span[.=text()])[2]");



}
