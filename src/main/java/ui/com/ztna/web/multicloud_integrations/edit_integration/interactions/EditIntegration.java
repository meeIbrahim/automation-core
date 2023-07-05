package ui.com.ztna.web.multicloud_integrations.edit_integration.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.multicloud_integrations.add_integration.user_interfaces.IntegrationPageUI.*;
import static ui.com.ztna.web.multicloud_integrations.edit_integration.user_interfaces.EditIntegrationFormUI.*;

public class EditIntegration {

    public static Performable openRespectiveIntegrationTypeTab(String integrationType) {
        return Task.where("{0} opens Respective Integration Type tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(INTEGRATION_TAB_BUTTON_SELECTED.of(integrationType)).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(INTEGRATION_TAB_BUTTON.of(integrationType)))
            );
            actor.attemptsTo(
                    waitPresenceOf(ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }

    public static Performable openEditIntegrationForm(String integrationNameToBeEdited) {
        return Task.where("{0} opens Edit Integration form",actor ->{
            actor.attemptsTo(
                    WaitUntil.the(INTEGRATION_ROW_OF_NAME_ACTION_BUTTON.of(integrationNameToBeEdited), isClickable()),
                    Click.on(INTEGRATION_ROW_OF_NAME_ACTION_BUTTON.of(integrationNameToBeEdited)),
                    WaitUntil.the(EDIT_INTEGRATION_DROPDOWN_OPTION, isPresent()),
                    WaitUntil.the(EDIT_INTEGRATION_DROPDOWN_OPTION, isClickable()),
                    Click.on(EDIT_INTEGRATION_DROPDOWN_OPTION),
                    WaitUntil.the(UPDATE_INTEGRATION_FORM, isPresent())
            );
        });
    }

    public static Performable fillsEditIntegrationForm(String editedIntegrationName) {
        return Task.where("{0} fills edit integration form",actor ->{
            actor.attemptsTo(
                    Clear.field(UPDATE_INTEGRATION_INPUT_FIELD),
                    Enter.keyValues(editedIntegrationName).into(UPDATE_INTEGRATION_INPUT_FIELD),
                    WaitUntil.the(UPDATE_BUTTON, isClickable()),
                    Click.on(UPDATE_BUTTON),
                    WaitUntil.the(UPDATE_INTEGRATION_FORM, isNotPresent())
            );
        });
    }



}
