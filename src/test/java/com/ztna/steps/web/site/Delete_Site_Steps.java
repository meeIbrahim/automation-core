package com.ztna.steps.web.site;

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
import ui.com.ztna.web.pages.sites.modals.UpdateSite;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Delete_Site_Steps {


    @Then("user removes site {reference}")
    public void userRemovesSiteA(String reference) throws cachedNotFound {
        ZTAParameters parameters= RM.site().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.sites().purge(parameters));

    }
    @Then("Site {reference} should be removed")
    public void siteAShouldBeRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.site().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.sites().openPage());
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(
                                ZTA.sites().table().contains(parameters.name))
                                .forTime(Duration.ofMinutes(1))
                                .untilEqualTo(false).isSuccess()
                ).isTrue()
        );
    }


    @When("user removes active relay {reference} from site {reference}")
    public void removeCHRFromSite(String relay, String site) throws cachedNotFound {
        ZTAParameters Relayparams= RM.relay().cache().retrieve(relay);
        System.out.println(Relayparams.name);
        ZTAParameters Siteparams= RM.site().cache().retrieve(site);
        System.out.println(Siteparams.name);
        theActorInTheSpotlight().attemptsTo(ZTA.sites().openPage());
        Row Site = theActorInTheSpotlight().asksFor(ZTA.sites().table().row(Siteparams.name));
        theActorInTheSpotlight().attemptsTo(
                UpdateSite.open(Site),
                UpdateSite.removeRelay(Relayparams.name),
                Modal.save()
        );
    }
}
