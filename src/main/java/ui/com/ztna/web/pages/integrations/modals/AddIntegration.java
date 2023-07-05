package ui.com.ztna.web.pages.integrations.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;

import java.time.Duration;

public class AddIntegration {

    public static Performable openAddIntegrationModal(){
        return Task.where("[0} opens add integration modal", actor -> {
            actor.attemptsTo(
                    Wait.forPresenceOf(IntegrationsUI.ADD_INTEGRATION_BUTTON).forTime(Duration.ofSeconds(30)),
                    Click.on(IntegrationsUI.ADD_INTEGRATION_BUTTON),
                    Wait.forPresenceOf(Modal.MODAL_BOX).forTime(Duration.ofSeconds(30))
            );
        });
    }
    public static Performable verifyAddIntegrationFailure() {
        return Task.where("{0} sees add integration failure msg.",actor -> {
            actor.attemptsTo(
                    Wait.forPresenceOf(IntegrationsUI.INTEGRATION_ALREADY_EXITS).forTime(Duration.ofSeconds(360))
            );
        });
    }
}
