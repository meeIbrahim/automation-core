package ui.com.ztna.web.pages.integrations.tabs.azure.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.parameters.AzureIntegrationParameters;

import static constants.Waits.ONE_HUNDRED_EIGHTY;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.multicloud_integrations.add_integration.interactions.AddIntegration.handleValidateAgain;

public class AddAzureIntegration {
    public static Target INTEGRATION_NAME = Input.BY_ORDER.of("1").called("Integration Name");
    public static Target SUBSCRIPTION_ID = Input.BY_ORDER.of("2").called("Subscription ID");
    public static Target TENANT_ID = Input.BY_ORDER.of("3").called("Tenant ID");
    public static Target APPLICATION_CLIENT_ID = Input.BY_ORDER.of("4").called("Application Client ID");
    public static Target OBJECT_ID = Input.BY_ORDER.of("5").called("Object ID");
    public static Target APPLICATION_CLIENT_SECRET = Input.BY_ORDER.of("6").called("Application Client Secret");
    public static Target ALREADY_EXIST_MESSAGE_FOR_AZURE = Target.the("Integration already exist message for AZURE")
            .locatedBy("//*[@data-testid='error-container']");
    public static final Target TRYING_TO_VALIDATE_ICON = Target.the("Trying to Validate icon")
            .locatedBy("//div[1]/img");
    public static final Target SUCCESS_AZURE_TEXT = Target.the("success Azure text")
            .locatedBy("//div/p[text()='Your Azure account has been successfully connected with ExtremeCloud Zero Trust Access.']");

    public static Performable openAzureIntegrationTab() {
        return Task.where("{0} opens Azure Integration tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(IntegrationsUI.INTEGRATION_TAB_BUTTON_SELECTED.of("Azure")).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(IntegrationsUI.INTEGRATION_TAB_BUTTON.of("2"))),
                    waitPresenceOf(IntegrationsUI.ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }

    public static Performable fillAzureAccountDetails(AzureIntegrationParameters parameters){
        return Task.where("{0} fills details for Azure Integration",actor -> {
            actor.attemptsTo(
                    Input.of(INTEGRATION_NAME).with(parameters.name),
                    Input.of(SUBSCRIPTION_ID).with(parameters.SubscriptionId),
                    Input.of(TENANT_ID).with(parameters.TenantId),
                    Input.of(APPLICATION_CLIENT_ID).with(parameters.ApplicationClientId),
                    Input.of(OBJECT_ID).with(parameters.ObjectId),
                    Input.of(APPLICATION_CLIENT_SECRET).with(parameters.ApplicationClientSecret)
            );

        });
    }
    public static Performable validateAzureIntegration(){
        return Task.where("{0} validates Azure Integration",actor -> {
            actor.attemptsTo(
                    Check.whether(Presence.of(ALREADY_EXIST_MESSAGE_FOR_AZURE).asABoolean().answeredBy(actor)).andIfSo(
                            Modal.close()
                    ).otherwise(
                            waitPresenceOf(TRYING_TO_VALIDATE_ICON),
                            WaitUntil.the(TRYING_TO_VALIDATE_ICON, isNotPresent()).forNoMoreThan(ONE_HUNDRED_EIGHTY).seconds(),
                            handleValidateAgain(),
                            WaitUntil.the(SUCCESS_AZURE_TEXT, isPresent()).forNoMoreThan(ONE_HUNDRED_EIGHTY).seconds()
                    )
            );

        });
    }

}
