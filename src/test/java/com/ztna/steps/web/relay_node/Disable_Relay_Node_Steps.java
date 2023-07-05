package com.ztna.steps.web.relay_node;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.ensure.Ensure;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Toggle;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.cloud.relays.Questions;
import ui.com.ztna.web.pages.cloud.relays.RelayUI;
import ui.com.ztna.web.pages.cloud.relays.modals.ManageHA;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Disable_Relay_Node_Steps {


    @Then("user disables HA for relay")
    public void DisableRelayHA() {
        String relayName = theActorInTheSpotlight().recall("relayName");
        Row relay = ZTA.relays().table().row(relayName).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                relay.action(RelayUI.MANAGE_HA_ACTION_MENU),
                Modal.waitUntilOpen(),
                Toggle.of(ManageHA.HA).enable(),  /// Toggle is switched for HA
                Modal.save(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(Questions.isHA(relay))
                                .untilEqualTo(false)
                                .isSuccess()).isTrue()
        );
    }

    @Then("user enables HA for relay")
    public void EnableHARelay() {
        String relayName = theActorInTheSpotlight().recall("relayName");
        Row relay = ZTA.relays().table().row(relayName).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                relay.action(RelayUI.MANAGE_HA_ACTION_MENU),
                Modal.waitUntilOpen(),
                Toggle.of(ManageHA.HA).disable(),  /// Toggle is switched for HA
                Modal.save(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(Questions.isHA(relay))
                                .untilEqualTo(false)
                                .isSuccess()).isTrue()
        );
    }
}
