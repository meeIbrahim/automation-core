package ui.com.ztna.web.pages.integrations.tabs.azure;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.pages.integrations.modals.AddIntegration;
import ui.com.ztna.web.pages.integrations.Integrations;
import ui.com.ztna.web.pages.integrations.tabs.azure.modals.AddAzureIntegration;
import ui.com.ztna.web.parameters.AzureIntegrationParameters;

public class AzureIntegration extends Integrations<AzureIntegrationParameters> {

    Table table = new Table(this,
            "Azure Integrations",
            "Integration Name",
            "Subscription ID",
            "Status",
            "Resource Groups",
            "VNETs");



    @Override
    public Table table() {
        return table;
    }
    @Override
    public Performable create(AzureIntegrationParameters parameters) {
        return Task.where("{0} adds azure Integration", actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddAzureIntegration.openAzureIntegrationTab(),
                                    AddIntegration.openAddIntegrationModal(),
                                    Modal.next(),
                                    AddAzureIntegration.fillAzureAccountDetails(parameters),
                                    Modal.next(),
                                    AddAzureIntegration.validateAzureIntegration(),
                                    Modal.save()
                            );
                        }
                    },300,IntegrationsUI.ENABLED_INTEGRATION,IntegrationsUI.INTEGRATION_ACTIVE_STATUS)
            );
        });
    }

    @Override
    protected Integer tab() {
        return 2;
    }
}
