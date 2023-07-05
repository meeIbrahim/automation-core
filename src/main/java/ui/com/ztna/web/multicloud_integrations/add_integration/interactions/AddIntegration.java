package ui.com.ztna.web.multicloud_integrations.add_integration.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.ONE_HUNDRED_EIGHTY;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.multicloud_integrations.add_integration.user_interfaces.AddIntegrationFormUI.*;
import static ui.com.ztna.web.multicloud_integrations.add_integration.user_interfaces.IntegrationPageUI.*;

public class AddIntegration {

    public static Performable openAwsIntegrationTab() {
        return Task.where("{0} opens AWS Integration tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(INTEGRATION_TAB_BUTTON_SELECTED.of(AWS)).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(INTEGRATION_TAB_BUTTON.of(AWS)))
            );
            actor.attemptsTo(
                    waitPresenceOf(ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }

    public static Performable openAzureIntegrationTab() {
        return Task.where("{0} opens Azure Integration tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(INTEGRATION_TAB_BUTTON_SELECTED.of(Azure)).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(INTEGRATION_TAB_BUTTON.of(Azure)))
            );
            actor.attemptsTo(
                    waitPresenceOf(ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }


    public static Performable handleValidateAgain() {
        return Task.where("{0} fills Add AWS Integration form ", actor ->{
            Boolean validateMessage = Presence.of(TRY_TO_VALIDATE_AGAIN_MESSAGE).asABoolean().answeredBy(actor);
            while(validateMessage) {
                actor.attemptsTo(
                        Click.on(TRY_AGAIN_BUTTON),
                        waitPresenceOf(TRYING_TO_VALIDATE_ICON),
                        WaitUntil.the(TRYING_TO_VALIDATE_ICON, isNotPresent()).forNoMoreThan(ONE_HUNDRED_EIGHTY).seconds()
                );
                validateMessage = Presence.of(TRY_TO_VALIDATE_AGAIN_MESSAGE).asABoolean().answeredBy(actor);
            }
        });
    }


}
