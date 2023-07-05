package com.ztna.steps.web.relay_node;

import io.cucumber.java.en.Then;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.pages.ZTA.ZTA;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Delete_Relay_Node_Steps {


    @Then("user removes Cloud Hosted Relay {reference}")
    public void removesAllPolicies(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.relays().purge(parameters));
    }


}