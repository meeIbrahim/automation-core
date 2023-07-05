package com.ztna.steps.vm;

import com.ztna.steps.web.relay_node.CloudRelayNodeSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import org.junit.Assert;
import postgresql.databases.ResourceDb;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.data.FindData;
import resource.data.UnknownResource;
import resource.data.UsedResource;
import resource.data.parameters.file.ConnectorJSON;
import resource.data.readers.connectors.PrivateConnectorReader;
import resource.manager.RM;
import ui.com.ztna.vm.service.connector.ConnectorVM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.ConnectorUI;
import ui.com.ztna.web.pages.connectors.Questions;
import ui.com.ztna.web.pages.connectors.modals.AddAwsConnector;
import ui.com.ztna.web.pages.connectors.modals.AddAzureConnector;
import ui.com.ztna.web.pages.connectors.modals.AddGcpConnector;
import ui.com.ztna.web.pages.connectors.modals.AddPrivateConnector;
import ui.com.ztna.web.parameters.AwsConnectorParameters;
import ui.com.ztna.web.parameters.AzureConnectorParameters;
import ui.com.ztna.web.parameters.GcpConnectorParameters;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.common.user_interface.CommonUI.FORCE_REMOVE_POP_OVER_BUTTON_DISABLED;
import static ui.com.ztna.web.common.user_interface.CommonUI.FORCE_REMOVE_POP_OVER_BUTTON_ENABLED;
import static ui.com.ztna.web.common.user_interface.RemoveResourcesUI.REMOVE_POPOVER_BUTTON;
import static ui.com.ztna.web.common.user_interface.RemoveResourcesUI.REMOVE_POPOVER_BUTTON_DISABLED;

public class AddServiceConnectorSteps {


    @And("User changes state of service connector {reference} to {word}")
    public void userChangesStateOfConnector(String reference,String state) throws cachedNotFound, SQLException {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        ResultSet result = ResourceDb.rowContainingElement("resources_host", "name", parameters.name);
        ResourceDb.updateDataBaseTime("resources_host","name",parameters.name,"last_status_update", "now()");
        ResourceDb.updateDataBase("resources_host","name",parameters.name,"state",state);
    }


        @And("State of service connector {reference} is IN_PROGRESS")
    public void userChangesStateOfConnectorToInProgress(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().table().refresh(),
                Ensure.thatTheAnswerTo(
                Wait.forQuestion(connector.contains(ConnectorUI.CONNECTOR_STATUS_IN_PROGRESS))
                        .forTime(Duration.ofSeconds(125))
                        .untilEqualTo(true)
                        .isSuccess())
                        .isTrue()
        );

    }
    @And("State of service connector {reference} is DEPLOYMENT_FAILED")
    public void userChangesStateOfConnectorToDeploymentFailed(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().table().refresh(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(connector.contains(ConnectorUI.CONNECTOR_STATUS_DEPLOYMENT_FAILED))
                                        .forTime(Duration.ofSeconds(125))
                                        .untilEqualTo(true)
                                        .isSuccess())
                        .isTrue()
        );

    }

    @Then("User verifies In-Progress state of connector {reference}")
    public void userVerifiesInProgressConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                connector.menu().open(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(Visibility.of(REMOVE_POPOVER_BUTTON_DISABLED).asABoolean())
                                .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                .untilEqualTo(false)
                                .isSuccess())
                        .isTrue(),
                Wait.forPresenceOf(REMOVE_POPOVER_BUTTON)
                        .forTime(Duration.ofSeconds(30))
        );
    }
    @Then("User verifies Deployment-failure state of connector {reference}")
    public void userVerifiesDeploymentFailedConnector(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                connector.menu().open(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(Visibility.of(ConnectorUI.REMOVE_ACTION_MENU).asABoolean())
                                        .forTime(Duration.ofSeconds(30))
                                        .untilEqualTo(true)
                                        .isSuccess())
                        .isTrue()
        );
    }

    @When("User verifies DELETE_IN_PROGRESS state of connector {reference}")
    public void connectorStatusChangedToDelete(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                connector.menu().close(),
                ZTA.serviceConnectors().table().refresh(),
                Wait.forQuestion(
                        connector.contains(ConnectorUI.CONNECTOR_STATUS_DELETE_IN_PROGRESS)
                ),
                connector.menu().open(),
                Ensure.thatTheAnswerTo(Visibility.of(FORCE_REMOVE_POP_OVER_BUTTON_DISABLED).asABoolean()).isTrue(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(Visibility.of(FORCE_REMOVE_POP_OVER_BUTTON_ENABLED).asABoolean())
                                .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                .untilEqualTo(true)
                                .isSuccess())
                        .isFalse(),
                connector.menu().open(),
                Wait.forPresenceOf(FORCE_REMOVE_POP_OVER_BUTTON_ENABLED)
                        .forTime(Duration.ofSeconds(30)),
                connector.menu().action(FORCE_REMOVE_POP_OVER_BUTTON_ENABLED),
                Modal.waitUntilOpen(),
                Input.of(Input.BY_ORDER.of("1")).with(parameters.name),
                Modal.save(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(connector.exists())
                                .untilEqualTo(false)
                                .isSuccess())
                        .isTrue()
                );
    }

    @When("User configures service connector {reference} with site {reference}")
    public void userConfiguresServiceConnector(String connector,String site) throws cachedNotFound {
        String Site = RM.site().cache().retrieve(site).name;
        PrivateConnectorParameters parameters = Data.privateConnector().site(Site).any();
        theActorInTheSpotlight().remember("ConnectorParameters",parameters);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage(),
                AddPrivateConnector.open(),
                AddPrivateConnector.configure(parameters.name,parameters.site,parameters.relay));
        theActorInTheSpotlight().attemptsTo(
                Modal.close()
        );
        RM.connector().cache().cache(connector, new ZTAParameters(parameters.name,parameters.identifier,""));
    }

    @When("User configures aws service connector {reference} with site {reference}")
    public void userConfiguresAwsSC(String connector,String site) throws cachedNotFound {
        String Site = RM.site().cache().retrieve(site).name;
        AwsConnectorParameters params=Data.awsConnector().site(Site).any();
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage(),
                AddAwsConnector.open(),
                AddAwsConnector.fill(params),
                Modal.close()
                );

        RM.connector().cache().cache(connector, new ZTAParameters(params.name,params.identifier,""));
    }

    @When("User configures azure service connector {reference} with site {reference}")
    public void userConfiguresAzureSC(String connector,String site) throws cachedNotFound {
        String Site = RM.site().cache().retrieve(site).name;
        AzureConnectorParameters params=Data.azureConnector().site(Site).any();

        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage(),
                AddAzureConnector.open(),
                AddAzureConnector.fill(params),
                Modal.close()
        );

        RM.connector().cache().cache(connector, new ZTAParameters(params.name,params.identifier,""));
    }

    @When("User configures gcp service connector {reference} with site {reference}")
    public void userConfiguresGcpSC(String connector,String site) throws cachedNotFound {
        String Site = RM.site().cache().retrieve(site).name;
        GcpConnectorParameters params=Data.gcpConnector().site(Site).any();
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage(),
                AddGcpConnector.open(),
                AddGcpConnector.fill(params),
                Modal.save()
        );

        RM.connector().cache().cache(connector, new ZTAParameters(params.name,params.identifier,""));
    }


    @When("User installs connector agent on connector {reference}")
    public void agentIsInstalledAgain(String reference) throws cachedNotFound, UnknownResource, UsedResource {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        ConnectorJSON connectorJSON = FindData.lookup(parameters.identifier, PrivateConnectorParameters.class);
        ZTAParameters siteParameters = RM.site().getActive("");
        PrivateConnectorParameters connectorParameters = PrivateConnectorReader.getConnectorParameters(connectorJSON,siteParameters.parent,siteParameters.name);
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage(),
                AddPrivateConnector.open(),
                AddPrivateConnector.configure(connectorParameters.name,connectorParameters.site,connectorParameters.relay));
                String command = theActorInTheSpotlight().recall("command");
        theActorInTheSpotlight().attemptsTo(
                ConnectorVM.install(command,connectorParameters),
                Modal.close()
        );
        theActorInTheSpotlight().remember("TCZT-1546",connectorParameters.name);

    }
    @Then("User installs service connector on vm")
    public void userInstallsServiceConnectorNew() throws IOException {
        String Command = theActorInTheSpotlight().recall("command");
        PrivateConnectorParameters parameters = theActorInTheSpotlight().recall("ConnectorParameters");
        theActorInTheSpotlight().attemptsTo(
//                S3PemReader.downloadPemFileAt(addServiceConnectorParameters.privateKeyPath),
                ConnectorVM.install(Command,parameters)
//                S3PemReader.deletePemFileAt(addServiceConnectorParameters.privateKeyPath)
        );
    }

    @And("User stops agent on Connector {reference}")
    public void stopAgentConnector(String reference) throws cachedNotFound, UnknownResource {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        ConnectorJSON json = FindData.lookup(parameters.identifier, PrivateConnectorParameters.class);
        PrivateConnectorParameters connectorParameters = new PrivateConnectorParameters(parameters.name, parameters.parent, "", json.user, json.host, json.port,json.pem);

        theActorInTheSpotlight().attemptsTo(
                ConnectorVM.stopAgent(connectorParameters),
                ZTA.serviceConnectors().openPage()
        );
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(connectorParameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(
                                connector.contains(ConnectorUI.ACTIVE_CONNECTOR)
                        )
                                .untilEqualTo(false)
                                .forTime(Duration.ofMinutes(3))
                                .isSuccess()
                ).isTrue()
        );
    }

    @And("User deletes agent on Connector {reference}")
    public void deleteAgentConnector(String reference) throws cachedNotFound, UnknownResource {
        ZTAParameters parameters = RM.connector().cache().retrieve(reference);
        ConnectorJSON json = FindData.lookup(parameters.identifier, PrivateConnectorParameters.class);
        PrivateConnectorParameters connectorParameters = new PrivateConnectorParameters(Data.generateName(), parameters.parent, "", json.user, json.host, json.port,json.pem);
        theActorInTheSpotlight().attemptsTo(
                ConnectorVM.deleteAgent(connectorParameters)
        );
    }



    @And("service connector {reference} is attached to Relay {reference}")
    public void connectorIsAttachedToRelay(String connectorRef,String relayRef) throws cachedNotFound {
        ZTAParameters connector = RM.connector().cache().retrieve(connectorRef);
        ZTAParameters relay = RM.relay().cache().retrieve(relayRef);
        Row connectorRow = ZTA.serviceConnectors().table().row(connector.name).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(Questions.relay(connectorRow)).isEqualTo(relay.name)
        );
    }
    @And("Service Connector {reference} is attached to Site {reference}")
    public void connectorIsAttachedToSite(String connectorRef,String SiteRef) throws cachedNotFound {
        ZTAParameters connector = RM.connector().cache().retrieve(connectorRef);
        ZTAParameters site = RM.site().cache().retrieve(SiteRef);
        Row connectorRow = ZTA.serviceConnectors().table().row(connector.name).answeredBy(theActorInTheSpotlight());
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(Questions.site(connectorRow)).isEqualTo(site.name)
        );
    }
    @And("service connector {reference} is enabled")
    public void serviceConnectorIsEnabled(String reference) throws cachedNotFound {
        String connectorName = RM.connector().cache().retrieve(reference).name;
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        ZTA.serviceConnectors().table().contains(connectorName)
                ).isTrue()
        );
        Row Connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(connectorName));
        theActorInTheSpotlight().attemptsTo(
                Wait.forQuestion(Connector.contains(ConnectorUI.CONNECTOR_STATUS_ENABLED))
                                .forTime(Duration.ofSeconds(200)),
            Wait.forQuestion(Connector.contains(ConnectorUI.ACTIVE_CONNECTOR))
                            .forTime(Duration.ofSeconds(200))
        );
    }


    @And("service connector is not added")
    public void serviceConnectorIsNotAdded() {
        String SC_name=theActorInTheSpotlight().recall("TCZT-1546");
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                Wait.forQuestion(
                        ZTA.serviceConnectors().table().contains(SC_name)
                ).untilEqualTo(true)
                        .forTime(Duration.ofMinutes(5))
                        .isSuccess()
                ).isFalse()
        );
    }


    @And("resources_host table should contain Service Connector {reference}")
    public void resources_hostTableShouldContainTheRecentlyAddedServiceConnectorEntry(String reference) throws SQLException, cachedNotFound {
        String connectorName = RM.connector().cache().retrieve(reference).name;
        ResultSet result = ResourceDb.rowContainingElement("resources_host", "name", connectorName);
        if (result != null) {
            Assert.assertEquals(connectorName, result.getString("name"));
        } else {
            Assert.fail();
        }
    }

    @And("resources_host table should contain the corresponding relay_node_id for Connector {reference}")
    public void resources_hostTableShouldContainTheCorrespondingRelay_node_id(String reference) throws cachedNotFound {
        String connectorName = RM.connector().cache().retrieve(reference).name;
        if (!ResourceDb.elementPresent("resources_host", "name", connectorName,"relay_node_id",CloudRelayNodeSteps.relayId) ){
            Assert.fail();
        }
    }


}
