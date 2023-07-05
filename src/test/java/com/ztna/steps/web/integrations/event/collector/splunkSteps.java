package com.ztna.steps.web.integrations.event.collector;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import resource.data.Data;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.integrations.event.collector.EventCollectorUI;
import ui.com.ztna.web.pages.integrations.event.collector.Splunk;
import ui.com.ztna.web.pages.integrations.event.collector.modals.EditSplunk;
import ui.com.ztna.web.parameters.SplunkParameters;

import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class splunkSteps {

    @Given("User is on Event Collector page")
    public void userIsOnEventCollectorPage(){
        theActorInTheSpotlight().attemptsTo(
                ZTA.eventCollector().openPage(),
                Ensure.thatTheAnswerTo(Visibility.of(EventCollectorUI.SPLUNK_CARD).asABoolean()).isTrue()
        );
    }

    @And("Splunk is not configured")
    public void splunkIsNotConfigured(){
    theActorInTheSpotlight().attemptsTo(
            Check.whether(
                    ZTA.eventCollector().splunk().isConnected()
            ).andIfSo(
                    ZTA.eventCollector().splunk().remove()
            ),
            Ensure.thatTheAnswerTo(ZTA.eventCollector().splunk().isConnected()).isFalse()
    );
    }

    @When("^User configures splunk (with|without) ssl$")
    public void userConfiguresSplunk(String ssl){
        SplunkParameters params = Objects.equals(ssl, "with") ? Data.splunk().verifySSL().get() : Data.splunk().get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.eventCollector().splunk().setUp(params)
        );
        String URI = "PROTOCOL://HOST:PORT" + Splunk.URI_ENDPOINT;
        URI = URI.replace("HOST",params.host).replace("PORT",params.port);
        URI = params.isHttps ? URI.replace("PROTOCOL","https") : URI.replace("PROTOCOL","http");
        theActorInTheSpotlight().remember("splunk_uri",URI);
    }
    @Then("User should see splunk is connected")
    public void splunkIsConnected(){
        String URI = theActorInTheSpotlight().recall("splunk_uri");
        theActorInTheSpotlight().attemptsTo(
                ZTA.eventCollector().openPage(),
                EditSplunk.open(),
                Ensure.thatTheAnswerTo(EditSplunk.splunkURI()).isEqualTo(URI),
                Modal.close()
        );
    }
}
