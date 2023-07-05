package com.ztna.steps.web.serviceconnector;

import io.cucumber.java.en.When;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.modals.ManageState;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DisableServiceConnectorSteps {
//    private UpdateServiceConnectorParameters disableServiceConnector;
    @When("user enables connector {reference}")
    public void enableConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage());
        Row connector = ZTA.serviceConnectors().table().row(parameters.name).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                ManageState.enableConnector(connector)
        );

    }
    @When("user disables connector {reference}")
    public void disableConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage());
        Row connector = ZTA.serviceConnectors().table().row(parameters.name).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                ManageState.disableConnector(connector)
        );

    }
}
