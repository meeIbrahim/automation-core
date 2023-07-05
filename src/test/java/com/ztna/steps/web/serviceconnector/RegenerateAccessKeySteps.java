package com.ztna.steps.web.serviceconnector;

import io.cucumber.java.en.When;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.modals.ManageAccess;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class RegenerateAccessKeySteps {

    @When("User regenerates access key for connector {reference}")
    public void regenerateConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage());
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ManageAccess.regenerateAccess(connector)
        );
    }

}
