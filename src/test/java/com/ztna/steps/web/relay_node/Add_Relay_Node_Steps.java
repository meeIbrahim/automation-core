package com.ztna.steps.web.relay_node;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.cloud.relays.Questions;
import ui.com.ztna.web.pages.cloud.relays.RelayUI;
import ui.com.ztna.web.parameters.RelayParameters;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Add_Relay_Node_Steps {


    @Given("User is on Cloud Relay Nodes page")
    public void onCloudHostedRelayPageNew() {
        theActorInTheSpotlight().attemptsTo(ZTA.relays().openPage());
    }

    @Given("user verifies Deployment Failed state for Cloud Relay {reference}")
    public void userVerifiesRemovalOfCloudRelay(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                relay.menu().open(),
                Wait.forPresenceOf(RelayUI.FORCE_REMOVE_ENABLED)
                        .forTime(Duration.ofSeconds(30)),
                relay.menu().action(RelayUI.FORCE_REMOVE_ENABLED),
                Modal.waitUntilOpen(),
                Modal.save(),
                relay.menu().open(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS.of("Remove In Progress")))
                        .untilEqualTo(true)
                        .forTime(Duration.ofSeconds(5)),
                Wait.forQuestion(relay.exists())
                        .forTime(Duration.ofMinutes(6).plusSeconds(5))
                        .untilEqualTo(false)
//                clickButton(CLOUD_RELAY_NODE_ACTION_ICON),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_BUTTON_ENABLED),
//                clickButton(REMOVE_CLOUD_RELAY_BUTTON_ENABLED),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                clickButton(REMOVE_CLOUD_RELAY_MODAL_BUTTON),
//                waitNotPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                waitPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS),
//                waitNotPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS, THREE_HUNDRED_AND_SIXTY)
        );
    }

    @Given("user verifies IN_PROGRESS state for Cloud Relay {reference}")
    public void userVerifiesRemovalOfCloudRelayForInProgressState(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                relay.menu().open(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(Visibility.of(RelayUI.FORCE_REMOVE_ENABLED).asABoolean())
                                        .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                        .isSuccess())
                        .isFalse(),
                Wait.forPresenceOf(RelayUI.FORCE_REMOVE_ENABLED)
                        .forTime(Duration.ofMinutes(3).plusSeconds(5)),  /// Relay goes from IN_PROGRESS To DEPLOYMENT_IN_PROGRESS which resets timer
                relay.menu().action(RelayUI.FORCE_REMOVE_ENABLED),
                Modal.waitUntilOpen(),
                Modal.save(),
                relay.menu().open(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS.of("Remove In Progress")))
                        .untilEqualTo(true)
                        .forTime(Duration.ofSeconds(5)),
                Wait.forQuestion(relay.exists())
                        .forTime(Duration.ofMinutes(6).plusSeconds(5))
                        .untilEqualTo(false)
//                clickButton(CLOUD_RELAY_NODE_ACTION_ICON),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_BUTTON_ENABLED, THREE_HUNDRED_AND_SIXTY),
//                clickButton(REMOVE_CLOUD_RELAY_BUTTON_ENABLED),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                clickButton(REMOVE_CLOUD_RELAY_MODAL_BUTTON),
//                waitNotPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                waitPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS),
//                waitNotPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS, THREE_HUNDRED_AND_SIXTY)
        );
    }



    @Given("user verifies DEPLOYMENT_IN_PROGRESS state for Cloud Relay {reference}")
    public void userVerifiesRemovalOfCloudRelayForDeploymentInProgressState(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                relay.menu().open(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(Visibility.of(RelayUI.FORCE_REMOVE_ENABLED).asABoolean())
                                        .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                        .isSuccess())
                        .isFalse(),
                Wait.forPresenceOf(RelayUI.FORCE_REMOVE_ENABLED)
                        .forTime(Duration.ofSeconds(30)),
                relay.menu().action(RelayUI.FORCE_REMOVE_ENABLED),
                Modal.waitUntilOpen(),
                Modal.save(),
                relay.menu().open(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS.of("Remove In Progress")))
                        .untilEqualTo(true)
                        .forTime(Duration.ofSeconds(5)),
                Wait.forQuestion(relay.exists())
                        .forTime(Duration.ofMinutes(6).plusSeconds(5))
                        .untilEqualTo(false)
//                clickButton(CLOUD_RELAY_NODE_ACTION_ICON),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_BUTTON_DISABLED, SIXTY),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_BUTTON_ENABLED, THREE_HUNDRED_AND_SIXTY),
//                clickButton(REMOVE_CLOUD_RELAY_BUTTON_ENABLED),
//                waitPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                clickButton(REMOVE_CLOUD_RELAY_MODAL_BUTTON),
//                waitNotPresenceOf(REMOVE_CLOUD_RELAY_MODAL),
//                waitPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS),
//                waitNotPresenceOf(FIRST_CLOUD_RELAY_NODE_WITH_REMOVE_IN_PROGRESS_STATUS, THREE_HUNDRED_AND_SIXTY)
        );
    }

    @When("user adds Cloud Relay Node")
    public void addCloudRelayNodes() {
        RelayParameters parameters = Data.relayAnyRegion().get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.relays().create(
                        Data.relayAnyRegion().get()
                )
        );
    }
    @When("User adds HA enabled Cloud Hosted Relay called {reference}")
    public void AddCloudRelayNodeWithEnabledHA(String reference) {
        RelayParameters parameters = Data.relayAnyRegion().setHA(true).get();
        theActorInTheSpotlight().attemptsTo(ZTA.relays().create(parameters));
        RM.relay().cache().cache(reference,parameters);
    }

    @Then("Relay {reference} should be HA enabled")
    public void relayIsHAEnabled(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.relays().openPage()
        );
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Questions.isHA(relay)
                ).isTrue()
        );
    }


}
