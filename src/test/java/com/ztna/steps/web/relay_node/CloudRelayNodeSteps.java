package com.ztna.steps.web.relay_node;

import io.cucumber.java.en.Given;
import jsch.RemoteHost;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import postgresql.databases.ResourceDb;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import serenity.custom.interactions.Refresh;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.cloud.relays.RelayUI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.common.variables.CommonVariables.relayName;

public class CloudRelayNodeSteps {
//    public static String relayName;
    public static String relayId;
    public static String relayIp;

    @Given("user remembers Cloud Hosted Relay name")
    public void remembersCloudHostedRelayName() {
        relayName = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(1)).name();
        theActorInTheSpotlight().remember("relayName", relayName);
    }

    @Given("User remembers name for Relay {reference}")
    public void rememberCloudRelayName(String reference) throws cachedNotFound {
        relayName = RM.relay().cache().retrieve(reference).name;
        theActorInTheSpotlight().remember("relayName",relayName);
    }

    @Given("user remembers IP for Cloud Hosted Relay {reference}")
    public void remembersCloudHostedRelayIp(String Reference) throws SQLException, cachedNotFound {
        relayName = RM.relay().cache().retrieve(Reference).name;

        ResultSet result = ResourceDb.rowContainingElement("resources_relaynode", "name", relayName);
        if (result != null) {
            relayId = result.getString("id");
            System.out.println("relayId: " + relayId);
        }
        ResultSet resultSecond = ResourceDb.rowContainingElementWithoutWorkspaceId("resources_relaynodeinstance", "relay_node_id", relayId);
        if (resultSecond != null) {
            relayIp = resultSecond.getString("endpoint");
            System.out.println("relayIp: " + relayIp);
            theActorInTheSpotlight().remember("relayIp", relayIp);
        }

    }


    @Given("user remembers id for Cloud Hosted Relay {reference}")
    public void remembersCloudHostedRelayId(String reference) throws SQLException, cachedNotFound {
        relayName = RM.relay().cache().retrieve(reference).name;

        ResultSet result = ResourceDb.rowContainingElement("resources_relaynode", "name", relayName);
        if (result != null) {
            relayId = result.getString("id");
        }
    }

    @Given("user stops relayagent service for Relay {reference}")
    public void userStopsRelayAgentService(String reference) throws cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("env");
        String pemPath = String.format("src/test/resources/pem/cloud-hosted-relay-%s.pem", environment);

        try {
            RemoteHost vm = new RemoteHost().onUrl(relayIp).forUser("ubuntu").onPort(22).havingIdentityFileAt(pemPath).runningCommand("sudo systemctl stop relayagent.service");
            vm.createSession();
            vm.connectSession();
            vm.createChannel();
            vm.connectChannel();
            vm.runCommand();
            vm.disconnectChannel();
            vm.disconnectSession();

        } catch (Exception e) {
            System.err.println(e);
        }

        theActorInTheSpotlight().attemptsTo(
                ZTA.relays().openPage(),
                Wait.forQuestion(relay.contains(RelayUI.ACTIVE_RELAY))
                        .forTime(Duration.ofMinutes(6))
                        .untilEqualTo(false)
        );
    }

    @Given("user changes status to IN_PROGRESS for Relay {reference}")
    public void userChangesStatusToInProgress(String reference) throws cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        ResourceDb.updateDataBase("resources_relaynode", "id", relayId,"state","IN_PROGRESS");
        ResourceDb.updateDataBaseTime("resources_relaynode", "id", relayId,"last_status_update", "now()");
        theActorInTheSpotlight().attemptsTo(
                ZTA.relays().openPage()
        );
    }

    @Given("user changes state to DELETE_IN_PROGRESS for Relay {reference}")
    public void changeStateToDeleteInProgress(String reference) throws cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        ResourceDb.updateDataBase("resources_relaynode", "id", relayId,"state","DELETE_IN_PROGRESS");
        ResourceDb.updateDataBaseTime("resources_relaynode", "id", relayId,"last_status_update", "now()");
        theActorInTheSpotlight().attemptsTo(Refresh.theBrowser(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS_DELETION))
                        .untilEqualTo(true));
    }

    @Given("user changes status to DEPLOYMENT_IN_PROGRESS for Relay {reference}")
    public void userChangesStatusToDeploymentInProgress(String reference) throws SQLException, cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        ResourceDb.updateDataBase("resources_relaynode", "id", relayId,"state","DEPLOYMENT_IN_PROGRESS");
        ResourceDb.updateDataBaseTime("resources_relaynode", "id", relayId,"last_status_update", "now()");
        theActorInTheSpotlight().attemptsTo(Refresh.theBrowser(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS_IN_PROGRESS))
                        .untilEqualTo(true)
        );
    }

    @Given("user changes status to DEPLOYMENT_FAILED for Relay {reference}")
    public void userChangesStatusToDeploymentFailed(String reference) throws SQLException, cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        ResourceDb.updateDataBase("resources_relaynode", "id", relayId,"state","DEPLOYMENT_FAILED");
        ResourceDb.updateDataBaseTime("resources_relaynode", "id", relayId,"last_status_update", "now()");
        theActorInTheSpotlight().attemptsTo(Refresh.theBrowser(),
                Wait.forQuestion(relay.contains(RelayUI.RELAY_STATUS_DEPLOYMENT_FAILED))
                        .untilEqualTo(true));
    }

    @Given("user starts relayagent service for Relay {reference}")
    public void userStartsRelayAgentService(String reference) throws cachedNotFound {
        String relayName = RM.relay().cache().retrieve(reference).name;
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(relayName));
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("env");
        String pemPath = String.format("src/test/resources/pem/cloud-hosted-relay-%s.pem", environment);

        try {
            RemoteHost vm = new RemoteHost().onUrl(relayIp).forUser("ubuntu").onPort(22).havingIdentityFileAt(pemPath).runningCommand("sudo systemctl start relayagent.service");
            vm.createSession();
            vm.connectSession();
            vm.createChannel();
            vm.connectChannel();
            vm.runCommand();
            vm.disconnectChannel();
            vm.disconnectSession();

        } catch (Exception e) {
            System.err.println(e);
        }

        theActorInTheSpotlight().attemptsTo(
                ZTA.relays().openPage(),
                Wait.forQuestion(relay.contains(RelayUI.ACTIVE_RELAY))
                        .untilEqualTo(true)
                        .forTime(Duration.ofMinutes(6))
        );
    }

    @Given("user updates last_status_update column to current time")
    public void userUpdatesTimeToCurrentTime() {
        ResourceDb.updateDataBaseTime("resources_relaynode", "id", relayId,"last_status_update", "now()");
    }


    @Given("User verifies DELETE_IN_PROGRESS state for Cloud Relay {reference}")
    public void userVerifiesRemovalOfCloudRelayForDeleteInProgressState(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.relay().cache().retrieve(reference);
        Row relay = theActorInTheSpotlight().asksFor(ZTA.relays().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                relay.menu().open(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(Visibility.of(RelayUI.FORCE_REMOVE_RELAY_NODE_OPTION).asABoolean())
                                        .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                        .isSuccess())
                        .isFalse(),
                relay.menu().open(),
                Wait.forPresenceOf(RelayUI.FORCE_REMOVE_RELAY_NODE_OPTION)
                        .forTime(Duration.ofSeconds(30)),
                relay.menu().action(RelayUI.FORCE_REMOVE_RELAY_NODE_OPTION),
                Modal.waitUntilOpen(),
                Input.of(Input.BY_ORDER.of("1")).with(parameters.name),
                Modal.save(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(relay.exists())
                                        .untilEqualTo(false)
                                        .isSuccess())
                        .isTrue()
        );
    }
}
