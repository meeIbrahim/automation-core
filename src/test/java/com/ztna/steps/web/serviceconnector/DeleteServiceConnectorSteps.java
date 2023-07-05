package com.ztna.steps.web.serviceconnector;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.ConnectorUI;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DeleteServiceConnectorSteps {


    @Then("Connector {reference} should be removed")
    public void connectorIsDelete(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.serviceConnectors().openPage());
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                Wait.forQuestion(connector.exists()).untilEqualTo(false)
                        .forTime(Duration.ofMinutes(3))
                        .isSuccess()).isTrue()
                );
    }
    @When("User removes connector {reference}")
    public void connectorRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.serviceConnectors().openPage());
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                connector.action(ConnectorUI.REMOVE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Modal.save(),
                Wait.forQuestion(
                        connector.contains(ConnectorUI.CONNECTOR_STATUS_DELETE_IN_PROGRESS)
                ).forTime(Duration.ofSeconds(5))
        );
    }


}
