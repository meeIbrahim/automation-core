package com.ztna.steps.web.serviceconnector;

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
import ui.com.ztna.web.pages.connectors.ConnectorUI;
import ui.com.ztna.web.pages.connectors.Questions;
import ui.com.ztna.web.pages.connectors.modals.UpdateConnector;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class UpdateServiceConnectorSteps {

    @When("User updates Connector {reference} name")
    public void userUpatesConnectorName(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage()
        );
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        String newName = Data.generateName();
        theActorInTheSpotlight().attemptsTo(
                UpdateConnector.open(connector),
                UpdateConnector.fill(newName,""),
                Modal.save()
        );
        parameters.name = newName;
        RM.connector().cache().update(reference,parameters);

    }

    @Then("Connector {reference} should be updated")
    public void connectorUpdate(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.serviceConnectors().table().contains(parameters.name))
                        .isTrue()
        );
    }

    @Then("service connector {reference} should be disabled")
    public void serviceConnectorDisabled(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().table().refresh(),
                Ensure.thatTheAnswerTo(
                        Questions.isDisabled(connector)
                ).isTrue());
    }
    @Then("service connector {reference} should be enabled")
    public void serviceConnectorEnabled(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().table().refresh(),
                Ensure.thatTheAnswerTo(
                        Questions.isDisabled(connector)
                ).isFalse(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(
                                connector.contains(ConnectorUI.CONNECTOR_STATUS_ENABLED))
                                .forTime(Duration.ofSeconds(60))
                                .untilEqualTo(true)
                                .isSuccess()
                ).isTrue()
                );
    }

}
