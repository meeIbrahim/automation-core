package ui.com.ztna.web.pages.integrations.tabs.gcp;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.integrations.Integrations;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.pages.integrations.modals.AddIntegration;
import ui.com.ztna.web.pages.integrations.tabs.gcp.modals.AddGCPIntegration;
import ui.com.ztna.web.parameters.GCPIntegrationParameters;

public class GcpIntegration extends Integrations<GCPIntegrationParameters> {

    Table table = new Table(this,
            "GCP Integration",
            "Integration Name",
            "Project ID",
            "Status",
            "VPCs",
            "Regions");



    @Override
    public Table table() {
        return table;
    }
    @Override
    public Performable create(GCPIntegrationParameters parameters) {
        return Task.where("{0} adds azure Integration", actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddGCPIntegration.openGCPIntegrationTab(),
                                    AddIntegration.openAddIntegrationModal(),
                                    Modal.next(),
                                    AddGCPIntegration.fillGCPAccountDetails(parameters),
                                    Modal.next(),
                                    AddGCPIntegration.validateGCPIntegration(parameters),
                                    Modal.save()
                            );
                        }
                    },300,IntegrationsUI.ENABLED_INTEGRATION, IntegrationsUI.INTEGRATION_ACTIVE_STATUS)
            );
        });
    }

    @Override
    protected Integer tab() {
        return 3;
    }
}
