package ui.com.ztna.web.pages.integrations.tabs.gcp.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.parameters.GCPIntegrationParameters;

import java.time.Duration;

import static constants.Waits.*;
import static files.utils.FileUtils.toAbsolutePath;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddGCPIntegration {
    public static Target INTEGRATION_NAME = Input.BY_ORDER.of("1").called("Integration Name");
    public static Target PROJECT_ID = Input.BY_ORDER.of("2").called("Project ID");
    public static final Target SERVICE_ACCOUNT_KEY_INPUT_FIELD = Target.the("Service Account Key input field")
            .locatedBy("//input[@id='file']");
    public static Target ALREADY_EXIST_MESSAGE_FOR_AZURE = Target.the("Integration already exist message for AZURE")
            .locatedBy("//*[@data-testid='error-container']");
    public static final Target TRYING_TO_VALIDATE_ICON = Target.the("Trying to Validate icon")
            .locatedBy("//div[1]/img");
    public static final Target SUCCESS_GCP_TEXT = Target.the("Success GCP text")
            .locatedBy("//div[./*[local-name()='svg']]/following-sibling::div[./*[contains(text(),'successfully connected')]]");
    public static final Target GCP_VALIDATION_FAILURE_TEXT = Target.the("Failed GCP validation text")
            .locatedBy("//div[./*[local-name()='svg']]/following-sibling::div[./*[contains(text(),'unable to connect')]]");

    public static Performable openGCPIntegrationTab() {
        return Task.where("{0} opens GCP Integration tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(IntegrationsUI.INTEGRATION_TAB_BUTTON_SELECTED.of("GCP")).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(IntegrationsUI.INTEGRATION_TAB_BUTTON.of("3"))),
                    waitPresenceOf(IntegrationsUI.ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }

    public static Performable fillGCPAccountDetails(GCPIntegrationParameters parameters){
        return Task.where("{0} fills details for GCP Integration",actor -> {

            actor.attemptsTo(
                    Input.of(INTEGRATION_NAME).with(parameters.name),
                    Input.of(PROJECT_ID).with(parameters.ProjectId),
                    SendKeys.of(toAbsolutePath(parameters.JsonFilePath)).into(SERVICE_ACCOUNT_KEY_INPUT_FIELD)
            );

        });
    }
    public static Performable validateGCPIntegration(GCPIntegrationParameters parameters){
        return Task.where("{0} validates GCP Integration",actor -> {
            Boolean success=true;
            int count=0;
            while(success && count<2){
            actor.attemptsTo(
                    Wait.forPresenceOf(TRYING_TO_VALIDATE_ICON).forTime(Duration.ofSeconds(30))
            );

            if(Presence.of(TRYING_TO_VALIDATE_ICON).asABoolean().answeredBy(actor)){
                actor.attemptsTo(
                        Wait.forInvisibilityOf(TRYING_TO_VALIDATE_ICON).forTime(Duration.ofSeconds(120))
                      );
            }

            if(Presence.of(SUCCESS_GCP_TEXT).asABoolean().answeredBy(actor)){
                success=false;

            } else if (Presence.of(GCP_VALIDATION_FAILURE_TEXT).asABoolean().answeredBy(actor)) {
                actor.attemptsTo(
                        Modal.previous(),
                        Input.of(PROJECT_ID).with(parameters.ProjectId),
                        SendKeys.of(toAbsolutePath(parameters.JsonFilePath)).into(SERVICE_ACCOUNT_KEY_INPUT_FIELD),
                        Modal.next()
                );
                count=count+1;
                success=true;
                }

            }

        });
    }
}
