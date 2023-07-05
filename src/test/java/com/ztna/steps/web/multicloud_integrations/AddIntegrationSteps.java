package com.ztna.steps.web.multicloud_integrations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.multicloud_integrations.add_integration.models.IntegrationParameters;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.integrations.modals.AddIntegration;
import ui.com.ztna.web.pages.integrations.tabs.aws.modals.AddAwsIntegration;
import ui.com.ztna.web.pages.integrations.tabs.gcp.modals.AddGCPIntegration;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;
import ui.com.ztna.web.parameters.GCPIntegrationParameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class AddIntegrationSteps {



    @When("User configures Azure integration {reference}")
    public void configureAzureIntegration(String integration) {
        ui.com.ztna.web.parameters.AzureIntegrationParameters params = Data.azureIntegration().any();

        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().azureIntegration().create(params)
        );
        RM.azureIntegrations().cache().cache(integration, new ZTAParameters(params.name,params.identifier,""));
    }
    @When("User configures GCP integration {reference}")
    public void configureGcpIntegration(String integration) {
        GCPIntegrationParameters params = Data.gcpIntegration().any();

        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().gcpIntegration().create(params)
        );
        RM.gcpIntegrations().cache().cache(integration, new ZTAParameters(params.name,params.identifier,""));
    }
    @Given("user adds AWS integration {word}")
    public void userAddsAWSIntegration(String AwsAccountId) {

        AWSIntegrationParameters parameters = Data.awsIntegration().withAwsAccountId(AwsAccountId);
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().awsIntegration().create(parameters)
        );
    }

    @When("User fails to configure AWS integration {reference}")
    public void FailsToConfigureAWSIntegration(String integration) throws cachedNotFound {
        ZTAParameters integrationParam = RM.awsIntegrations().cache().retrieve(integration);
        AWSIntegrationParameters parameters = Data.awsIntegration().withAwsAccountId(integrationParam.identifier);
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().awsIntegration().openPage(),
                AddAwsIntegration.openAwsIntegrationTab(),
                AddIntegration.openAddIntegrationModal(),
                AddAwsIntegration.fillGeneralSettings(parameters),
                Modal.next(),
                AddAwsIntegration.fillIntegrationCredentials(parameters),
                Modal.next(),
                AddIntegration.verifyAddIntegrationFailure(),
                Modal.close()
        );
    }
    @When("User fails to configure Azure integration {reference}")
    public void FailsToConfigureAzureIntegration(String integration) throws cachedNotFound {
        ZTAParameters integrationParam = RM.azureIntegrations().cache().retrieve(integration);

        ui.com.ztna.web.parameters.AzureIntegrationParameters parameters = Data.azureIntegration().withAzureSubscriptionId(integrationParam.identifier);
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().azureIntegration().openPage(),
                AddIntegration.openAddIntegrationModal(),
                Modal.next(),
                ui.com.ztna.web.pages.integrations.tabs.azure.modals.AddAzureIntegration.fillAzureAccountDetails(parameters),
                Modal.next(),
                AddIntegration.verifyAddIntegrationFailure(),
                Modal.close()

        );
    }

    @When("User fails to configure Gcp integration {reference}")
    public void FailsToConfigureGcpIntegration(String integration) throws cachedNotFound {
        ZTAParameters integrationParam = RM.gcpIntegrations().cache().retrieve(integration);

        GCPIntegrationParameters parameters = Data.gcpIntegration().withGcpProjectId(integrationParam.identifier,"");
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().gcpIntegration().openPage(),
                AddIntegration.openAddIntegrationModal(),
                Modal.next(),
                AddGCPIntegration.fillGCPAccountDetails(parameters),
                Modal.next(),
                AddIntegration.verifyAddIntegrationFailure(),
                Modal.close()

        );
    }

    @When("User configures AWS integration {reference}")
    public void configureAWSIntegrationWithRelay(String integration) {
        AWSIntegrationParameters params = Data.awsIntegration().any();

        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().awsIntegration().create(params)
        );
        RM.awsIntegrations().cache().cache(integration, new ZTAParameters(params.name,params.identifier,""));
    }
    @Given("user adds Azure integration {word}")
    public void userAddsAzureIntegration(String azureSubscriptionId) {
        //added temporarily to be removed after words
        String TenantId="b790f6ff-34c5-4074-bc2e-2f6be41e5870";
        String ApplicationClientId="01a81a6d-5911-44c1-bee1-2c50164b72c5";
        String ObjectId="ca93b951-7eff-4c04-a654-8b0e0a495cce";
        String ApplicationClientSecret="XMW8Q~Lct8_m4FwkM3~GTQtvi4gAYKjxafDjIazT";

        ui.com.ztna.web.parameters.AzureIntegrationParameters parameters = Data.azureIntegration().withAzureAccount(azureSubscriptionId,TenantId,ApplicationClientId,ObjectId,ApplicationClientSecret);
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().azureIntegration().create(parameters)
        );
    }

    @Then("AWS integration {reference} should be added")
    public void awsIntegrationAShouldBeAdded(String reference) throws cachedNotFound {
        ZTAParameters integrationParam = RM.awsIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.cloudIntegrations().awsIntegration().table().contains(integrationParam.name)).isTrue()
        );
    }
    @Then("Azure integration {reference} should be added")
    public void azureIntegrationAdded(String reference) throws cachedNotFound {
        ZTAParameters integrationParam = RM.azureIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.cloudIntegrations().azureIntegration().table().contains(integrationParam.name)).isTrue()
        );
    }
    @Then("GCP integration {reference} should be added")
    public void gcpIntegrationAdded(String reference) throws cachedNotFound {
        ZTAParameters integrationParam = RM.gcpIntegrations().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.cloudIntegrations().gcpIntegration().table().contains(integrationParam.name)).isTrue()
        );
    }

    @Given("user adds gcp integration {word}")
    public void userAddsGcpIntegration(String gcpProjectId) {
        //added temporarily to be removed after words
        String jsonFilePath="src/test/resources/test_data/web/multicloud_integrations/ztna-automation-project-1.json";

        GCPIntegrationParameters parameters = Data.gcpIntegration().withJsonFilePath(jsonFilePath).withGcpProjectId(gcpProjectId);
        theActorInTheSpotlight().attemptsTo(
                ZTA.cloudIntegrations().gcpIntegration().create(parameters)
        );
    }


}
