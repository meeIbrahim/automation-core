package com.ztna.steps.web.serviceconnector;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.Questions;
import ui.com.ztna.web.pages.connectors.modals.ManageAccess;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class RevokeAccessServiceConnectorSteps {




    @When("User revokes access of connector {reference}")
    public void userRevokeConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage());
        Row row = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ManageAccess.revokeAccess(row));
    }
    @Then("Access of Connector {reference} should be revoked")
    public  void ConnectorIsRevoked(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage());
        Row row = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Questions.isRevoked(row)
                ).isTrue()
        );
    }
}
