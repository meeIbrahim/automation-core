package ui.com.ztna.web.multicloud_integrations.edit_integration.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.multicloud_integrations.edit_integration.models.EditIntegrationParameters;

import static ui.com.ztna.web.multicloud_integrations.edit_integration.interactions.EditIntegration.*;

public class EditIntegration {

    public static Performable using(EditIntegrationParameters parameters) {
        return Task.where("{0} edits an integration",actor ->{
            actor.attemptsTo(
                    openRespectiveIntegrationTypeTab(parameters.integrationType),
                    openEditIntegrationForm(parameters.integrationNameToBeEdited),
                    fillsEditIntegrationForm(parameters.editedIntegrationName)
            );
        });
    }

}
