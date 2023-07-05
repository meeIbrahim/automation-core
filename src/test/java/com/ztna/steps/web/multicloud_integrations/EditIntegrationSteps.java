package com.ztna.steps.web.multicloud_integrations;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import resource.manager.provider.common.IndependentEntity;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.multicloud_integrations.edit_integration.models.EditIntegrationParameters;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.integrations.Integrations;
import ui.com.ztna.web.pages.integrations.modals.UpdateIntegration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class EditIntegrationSteps {
    private EditIntegrationParameters editIntegrationParameters;


    @Then("{word} integration {reference} name should be updated")
    public void awsIntegrationUpdate(String host, String reference) throws cachedNotFound {
        IndependentEntity<?> integration;
        Integrations<?> page;
        switch(host.toLowerCase()){
            case "aws":
                integration=RM.awsIntegrations();
                page=ZTA.cloudIntegrations().awsIntegration();
                break;
            case "gcp":
                integration=RM.gcpIntegrations();
                page=ZTA.cloudIntegrations().gcpIntegration();
                break;
            case "azure":
                integration=RM.azureIntegrations();
                page=ZTA.cloudIntegrations().azureIntegration();
                break;
            default:
                throw new IllegalArgumentException();
        }
        ZTAParameters parameters = integration.cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(page.table().contains(parameters.name))
                        .isTrue()
        );
    }


    @When("user tries to edit {word} integration {reference} name")
    public void userTriesToEditAWSIntegrationAName(String host,String ref) throws cachedNotFound {
        IndependentEntity<?> integration;
        Integrations<?> page;
        switch(host.toLowerCase()){
            case "aws":
                integration=RM.awsIntegrations();
                page=ZTA.cloudIntegrations().awsIntegration();
                break;
            case "gcp":
                integration=RM.gcpIntegrations();
                page=ZTA.cloudIntegrations().gcpIntegration();
                break;
            case "azure":
                integration=RM.azureIntegrations();
                page=ZTA.cloudIntegrations().azureIntegration();
                break;
            default:
                throw new IllegalArgumentException();
        }
        ZTAParameters params= integration.cache().retrieve(ref);
        theActorInTheSpotlight().attemptsTo(
                page.openPage()
        );
        Row row = theActorInTheSpotlight().asksFor(page.table().row(params.name));
        String newName = Data.generateName();
        theActorInTheSpotlight().attemptsTo(
                UpdateIntegration.open(row),
                UpdateIntegration.updateName(newName),
                Modal.save()
        );
        params.name = newName;
        integration.cache().update(ref,params);
    }
}
