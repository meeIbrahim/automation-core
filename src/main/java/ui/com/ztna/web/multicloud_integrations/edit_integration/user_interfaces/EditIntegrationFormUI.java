package ui.com.ztna.web.multicloud_integrations.edit_integration.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class EditIntegrationFormUI {

    public static final Target UPDATE_INTEGRATION_FORM = Target.the("Update Integration form")
            .locatedBy("//div[@role='dialog'][.//h2[text()='Update Integration']]");

    public static final Target UPDATE_INTEGRATION_INPUT_FIELD = Target.the("Update Integration form input field")
            .locatedBy("//div[@role='dialog'][.//h2[text()='Update Integration']]//input[@name='name']");

    public static final Target UPDATE_BUTTON = Target.the("Update button")
            .locatedBy("//div[@role='dialog'][.//h2[text()='Update Integration']]//button[.//span[text()='Update']]");
}
