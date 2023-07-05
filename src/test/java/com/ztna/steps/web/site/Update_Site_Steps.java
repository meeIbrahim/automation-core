package com.ztna.steps.web.site;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.sites.modals.UpdateSite;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Update_Site_Steps {


    @When("User updates Site {reference} name")
    public void userUpatesConnectorName(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.site().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().openPage()
        );
        Row site= theActorInTheSpotlight().asksFor(ZTA.sites().table().row(parameters.name));
        String newName = Data.generateName();
        theActorInTheSpotlight().attemptsTo(
                UpdateSite.open(site),
                UpdateSite.updateName(newName),
                Modal.save()
        );
        parameters.name = newName;
        RM.site().cache().update(reference,parameters);

    }
    @Then("Site {reference} should be updated")
    public void connectorUpdate(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.site().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.sites().table().contains(parameters.name))
                        .isTrue()
        );
    }
}
