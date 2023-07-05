package ui.com.ztna.web.pages.identityProviders;

import net.serenitybdd.screenplay.targets.Target;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.page.ResourceUI;

public class IdentityProvidersUI implements ResourceUI {


public static Target IDENTITY_PROVIDER_SELECTED = Target.the("Identity Provider selected")
        .locatedBy("//div[./p[contains(text(),'Identity Provider')]][.//p[contains(text(),'{0}')]]");
    public static Target DISCONNECT_IDP = Target.the("Disconnect Identity Provider")
            .locatedBy("//button[./*[contains(text(),'Disconnect')]]");
    public static Target DISCONNECT_BUTTON = Target.the("Disconnect modal button")
            .locatedBy("#en-ztna-modal-saveButton");
    public static Target IDP_TABS = Target.the("IDP tabs")
            .locatedBy("//div[contains(@class,'MuiPaper-elevation0')][./h3[contains(text(),'{0}')]]");
    public static Target CONTINUE_BUTTON = Target.the("Continue button")
            .locatedBy("//button[./*[contains(text(),'Continue')]]");
    public static Target VALIDATE_INFORMATION_BUTTON = Target.the("Validate Information button")
            .locatedBy("//button[.//*[contains(text(),'Validate Information')]]");
    public static Target RESET_BUTTON = Target.the("Validate Information button")
            .locatedBy("//button[.//*[contains(text(),'Reset')]]");
    public static Target VALIDATION_SUCCESSFUL_MESSAGE = Target.the("Validation successful message")
            .locatedBy("//div[@data-testid=\"success-container\"]");
    public static Target UPDATE_BUTTON = Target.the("Validate Information button")
            .locatedBy("//button[.//*[contains(text(),'Update')]]");


    @NotNull
    @Override
    public Target removeActionMenu() {
        return null;
    }

    @NotNull
    @Override
    public Target activeStatus() {
        return null;
    }

    @NotNull
    @Override
    public Target enabledStatus() {
        return null;
    }

    @NotNull
    @Override
    public Target deletionStatus() {
        return null;
    }
}