package ui.com.ztna.web.pages.integrations.tabs.aws;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.integrations.Integrations;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.pages.integrations.modals.AddIntegration;
import ui.com.ztna.web.pages.integrations.tabs.aws.modals.AddAwsIntegration;

import ui.com.ztna.web.parameters.AWSIntegrationParameters;


public class AwsIntegration extends Integrations<AWSIntegrationParameters> {

    Table table = new Table(this,
            "AWS Integration",
            "Integration Name",
            "AWS Account ID",
            "Status",
            "Regions",
            "VPCs");



    @Override
    public Table table() {
        return table;
    }
    @Override
    public Performable create(AWSIntegrationParameters parameters) {
        return Task.where("{0} adds aws Integration",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddAwsIntegration.openAwsIntegrationTab(),
                                    AddIntegration.openAddIntegrationModal(),
                                    AddAwsIntegration.fillGeneralSettings(parameters),
                                    Modal.next(),
                                    AddAwsIntegration.fillIntegrationCredentials(parameters),
                                    Modal.save()
                            );
                        }
                    }, 300,IntegrationsUI.ENABLED_INTEGRATION,IntegrationsUI.INTEGRATION_ACTIVE_STATUS)
            );
        });
    }

    @Override
    protected Integer tab() {
        return 1;
    }
}
