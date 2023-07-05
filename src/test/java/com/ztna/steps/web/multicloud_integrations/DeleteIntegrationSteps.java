package com.ztna.steps.web.multicloud_integrations;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.pages.integrations.tabs.aws.modals.AddAwsIntegration;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DeleteIntegrationSteps {



    @When("user tries to delete AWS integration {reference}")
    public void userTriesToDeleteAWSIntegration(String reference) throws cachedNotFound {
        ZTAParameters params= RM.awsIntegrations().cache().retrieve(reference);
        AWSIntegrationParameters parameters = Data.awsIntegration().withAwsAccountId(params.identifier);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().awsIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().awsIntegration().table().row(params.name));
        theActorInTheSpotlight().attemptsTo(
                integration.action(IntegrationsUI.REMOVE_INTEGRATION),
                Modal.waitUntilOpen(),
                Modal.next(),
                AddAwsIntegration.fillIntegrationCredentials(parameters),
                Modal.save(),
                Wait.forQuestion(
                        integration.contains(IntegrationsUI.INTEGRATION_DELETION_STATUS)
                ).forTime(Duration.ofSeconds(5))
        );
        Data.free(params.identifier);
    }
    @When("user tries to delete Azure integration {reference}")
    public void userTriesToDeleteAzureIntegration(String reference) throws cachedNotFound {
        ZTAParameters params= RM.azureIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().azureIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().azureIntegration().table().row(params.name));
        theActorInTheSpotlight().attemptsTo(
                integration.action(IntegrationsUI.REMOVE_INTEGRATION),
                Modal.waitUntilOpen(),
                Modal.save(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(integration.exists()).untilEqualTo(false)
                                .forTime(Duration.ofMinutes(2))
                                .isSuccess()).isTrue()

        );
        Data.free(params.identifier);
    }
    @When("user tries to delete GCP integration {reference}")
    public void userTriesToDeletegcpIntegration(String reference) throws cachedNotFound {
        ZTAParameters params= RM.gcpIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().gcpIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().gcpIntegration().table().row(params.name));
        theActorInTheSpotlight().attemptsTo(
                integration.action(IntegrationsUI.REMOVE_INTEGRATION),
                Modal.waitUntilOpen(),
                Modal.save(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(integration.exists()).untilEqualTo(false)
                                .forTime(Duration.ofMinutes(2))
                                .isSuccess()).isTrue()

        );
        Data.free(params.identifier);

    }


    @Then("AWS integration {reference} should be removed")
    public void awsIntegrationShouldBeRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.awsIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().awsIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().awsIntegration().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(integration.exists()).untilEqualTo(false)
                                .forTime(Duration.ofMinutes(2))
                                .isSuccess()).isTrue()
        );
    }
    @Then("Azure integration {reference} should be removed")
    public void azureIntegrationShouldBeRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.azureIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().azureIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().azureIntegration().table().row(parameters.name));
        assert integration==null:"Integration should have been removed but is still present";
    }
    @Then("GCP integration {reference} should be removed")
    public void gcpIntegrationShouldBeRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.gcpIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.cloudIntegrations().gcpIntegration().openPage());
        Row integration = theActorInTheSpotlight().asksFor(ZTA.cloudIntegrations().gcpIntegration().table().row(parameters.name));
        assert integration==null:"Integration should have been removed but is still present";
    }


}
