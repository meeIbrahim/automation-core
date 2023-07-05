package com.ztna.steps.web.service;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.Assert;
import postgresql.databases.ResourceDb;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.data.FindData;
import resource.data.MatchingResourceNotFound;
import resource.data.UnknownResource;
import resource.data.parameters.file.ConnectorJSON;
import resource.manager.RM;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.tab.remote.desktop.remoteDesktop;
import ui.com.ztna.web.pages.services.tab.secure.shell.secureShell;
import ui.com.ztna.web.pages.services.tab.web.webApp;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;
import ui.com.ztna.web.parameters.remoteDesktopParameters;
import ui.com.ztna.web.parameters.secureShellParameters;
import ui.com.ztna.web.parameters.webAppParameters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class AddServiceSteps {


    String CONNECTIVITY_STATUS = "connectivity_test";
    @ParameterType("HTTPS|HTTP|RDP|VNC|SSH")
    public static String serviceProtocol(String protocol){return protocol.toLowerCase();}

    @ParameterType("((((25[0-5]|(2[0-4]|1\\d|\\d|)\\d)\\.?){4})(:\\d+)?)")
    public static String serviceIP(String serviceIP){return serviceIP;}

    @ParameterType("(?i)(pass|fail)")
    public static Boolean connectivityStatus(String bool){
        return bool.equalsIgnoreCase("pass");
    }




    @When("User adds unhidden {serviceProtocol} webApp called {reference} with Connector {reference}")
    public void userAddsWebAppUnhidden(String type, String serviceReference,String connectorReference) throws cachedNotFound, MatchingResourceNotFound{
        ZTAParameters connectorParams = RM.connector().cache().retrieve(connectorReference);
        webAppParameters serviceParams = Data.webApp().connector(connectorParams).withProtocol(type);
        serviceParams.hidden = false;
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().create(serviceParams)
        );
        RM.service().webApp().cache().cache(serviceReference,serviceParams);
    }
    @When("User adds {serviceProtocol} webApp called {reference} with Connector {reference}")
    public void userAddsWebApp(String type, String serviceReference,String connectorReference) throws cachedNotFound, MatchingResourceNotFound {
        ZTAParameters connectorParams = RM.connector().cache().retrieve(connectorReference);
        webAppParameters serviceParams = Data.webApp().connector(connectorParams).withProtocol(type);
        theActorInTheSpotlight().attemptsTo(
        ZTA.services().webApp().create(serviceParams)
        );
        RM.service().webApp().cache().cache(serviceReference,serviceParams);
    }

    @Then("Service connectivity should {connectivityStatus}")
    public void checkServiceConnectivity(Boolean expectedStatus){
        Boolean test = theActorInTheSpotlight().recall(CONNECTIVITY_STATUS);
        assert test == expectedStatus;
    }
    @When("User tests connectivity for {serviceProtocol} Service with Connector {reference}")
    public void userTestsConnectivity(String protocol,String connectorReference) throws MatchingResourceNotFound, cachedNotFound {
        Boolean test = false;
        ZTAParameters connectorParams = RM.connector().cache().retrieve(connectorReference);
        switch (protocol.toLowerCase()){
            case "http":
            case "https":
                webAppParameters webAppParameters = Data.webApp().connector(connectorParams).withProtocol(protocol.toLowerCase());
                webApp webApp = new webApp();
                test = theActorInTheSpotlight().asksFor(webApp.testConnectivity(webAppParameters));
                break;
            case "rdp":
            case "vnc":
                remoteDesktopParameters remoteDesktopParameters = Data.remoteDesktop().connector(connectorParams).withProtocol(protocol.toLowerCase());
                remoteDesktop remoteDesktop = new remoteDesktop();
                test = theActorInTheSpotlight().asksFor(remoteDesktop.testConnectivity(remoteDesktopParameters));
                break;
            case "ssh":
                secureShellParameters secureShellParameters = Data.secureShell().connector(connectorParams).withProtocol(protocol.toLowerCase());
                secureShell secureShell = new secureShell();
                test = theActorInTheSpotlight().asksFor(secureShell.testConnectivity(secureShellParameters));
        }
        theActorInTheSpotlight().remember(CONNECTIVITY_STATUS,test);

    }
    @When("User tests connectivity for {serviceProtocol} Service with address {serviceIP} and Connector {reference}")
    public void userTestsConnectivity(String protocol,String address,String connectorReference) throws MatchingResourceNotFound, cachedNotFound {
        Boolean test = false;
        ZTAParameters connectorParams = RM.connector().cache().retrieve(connectorReference);
        switch (protocol.toLowerCase()){
            case "http":
            case "https":
                webAppParameters webAppParameters = Data.webApp().connector(connectorParams).withProtocol(protocol.toLowerCase());
                webAppParameters.url = address;
                webApp webApp = new webApp();
                test = theActorInTheSpotlight().asksFor(webApp.testConnectivity(webAppParameters));
                break;
            case "rdp":
            case "vnc":
                remoteDesktopParameters remoteDesktopParameters = Data.remoteDesktop().connector(connectorParams).withProtocol(protocol.toLowerCase());
                remoteDesktopParameters.url = address;
                if (address.contains(":")) {
                    remoteDesktopParameters.url  = address.substring(0,address.indexOf(":"));
                    remoteDesktopParameters.port = address.substring(address.indexOf(":") + 1);
                }
                remoteDesktop remoteDesktop = new remoteDesktop();
                test = theActorInTheSpotlight().asksFor(remoteDesktop.testConnectivity(remoteDesktopParameters));
                break;
            case "ssh":
                secureShellParameters secureShellParameters = Data.secureShell().connector(connectorParams).withProtocol(protocol.toLowerCase());
                secureShellParameters.url = address;
                if (address.contains(":")) {
                    secureShellParameters.url  = address.substring(0,address.indexOf(":"));
                    secureShellParameters.port = address.substring(address.indexOf(":") + 1);
                }
                secureShell secureShell = new secureShell();
                test = theActorInTheSpotlight().asksFor(secureShell.testConnectivity(secureShellParameters));
        }
        theActorInTheSpotlight().remember(CONNECTIVITY_STATUS,test);
    }

    @When("user adds connector {reference} as ssh service {reference}")
    public void ConnectorAsSSH(String connectorReference, String serviceReference) throws cachedNotFound, UnknownResource {
        ZTAParameters parameters = RM.connector().cache().retrieve(connectorReference);
        System.out.println("Identifier: " + parameters.identifier);
        ConnectorJSON connectorJSON = FindData.lookup(parameters.identifier, PrivateConnectorParameters.class);
        secureShellParameters serviceParameters = new secureShellParameters(Data.generateName(),
                "ssh",
                connectorJSON.host,
                String.valueOf(connectorJSON.port),
                parameters.name,parameters.parent);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().secureShell().create(serviceParameters)
        );
        RM.service().secureShell().cache().cache(serviceReference,serviceParameters);
    }


    @When("User adds ssh service called {reference} for Connector {reference}")
    public void userAddsSshService(String reference,String connector) throws cachedNotFound {
        ZTAParameters Connector = RM.connector().cache().retrieve(connector);
        secureShellParameters parameters = Data.secureShell().connector(Connector).any();
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().secureShell().create(parameters)
        );
        RM.service().secureShell().cache().cache(reference,new ZTAParameters(parameters.name,parameters.identifier,parameters.parent));
    }


    @When("User adds {serviceProtocol} {reference} with Connector {reference}")
    public void userAddsRdpVncService(String protocol,String serviceRef, String ConnectorRef ) throws cachedNotFound, MatchingResourceNotFound {
        ZTAParameters connectorParameters = RM.connector().cache().retrieve(ConnectorRef);
        switch (protocol) {
            case "rdp":
            case "vnc":
                remoteDesktopParameters parameters = Data.remoteDesktop().connector(connectorParameters).withProtocol(protocol);
                theActorInTheSpotlight().attemptsTo(
                    ZTA.services().remoteDesktop().create(parameters)
                );
                RM.service().remoteDesktop().cache().cache(serviceRef, parameters);
                break;
            case "ssh":
                secureShellParameters secureShellParameters = Data.secureShell().connector(connectorParameters).withProtocol(protocol);
                theActorInTheSpotlight().attemptsTo(
                        ZTA.services().secureShell().create(secureShellParameters)
                        );
                RM.service().secureShell().cache().cache(serviceRef,secureShellParameters);
                break;
            case "http":
            case "https":
                webAppParameters webAppParameters = Data.webApp().connector(connectorParameters).withProtocol(protocol);
                theActorInTheSpotlight().attemptsTo(
                        ZTA.services().webApp().create(webAppParameters)
                );
                RM.service().webApp().cache().cache(serviceRef,webAppParameters);
        }
    }



    @Then("User should see that service {reference} is active")
    public void userSeesServiceIsActive(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.service().cache().retrieve(reference);
        List<Performable> pages = List.of(ZTA.services().webApp().openPage(),
                ZTA.services().secureShell().openPage(),
                ZTA.services().remoteDesktop().openPage()
                                );
        boolean flag = false;
        for (Performable page : pages){
            theActorInTheSpotlight().attemptsTo(
                    page
            );
            if (ZTA.services().remoteDesktop().table().contains(parameters.name).answeredBy(
                    theActorInTheSpotlight()
            )){
                flag = true;
                Row service = theActorInTheSpotlight().asksFor(ZTA.services().secureShell().table().row(parameters.name));
                theActorInTheSpotlight().attemptsTo(
                        Ensure.thatTheAnswerTo(
                                service.contains(ServicesUI.ACTIVE_SERVICE)
                        ).isTrue());
            }
        }
        if (!flag){
            throw new IllegalArgumentException("Reference Service not Found!");
        }

    }

    @Then("run_as_agentless column in resource_service table should be false for Service {reference}")
    public void runAsAgentlessShouldBeFalse(String reference) throws SQLException, cachedNotFound {
        ZTAParameters service = RM.service().cache().retrieve(reference);
        ResultSet result;
        result = ResourceDb.rowContainingElement("resources_service", "name", service.name);
        if (result == null) {
            Assert.fail();
        }
        if (!Objects.equals(result.getBoolean("run_as_agentless"), false)) {
            Assert.fail();
        }
    }

    @Then("is_url_hidden should be set to {aBoolean} for Service {reference}")
    public void isUrlHiddenTrue(Boolean bool,String reference) throws SQLException, cachedNotFound {
        ZTAParameters service = RM.service().cache().retrieve(reference);
        ResultSet result;
        result = ResourceDb.rowContainingElement("resources_service", "name", service.name);
        if (result == null) {
            Assert.fail();
        }
        if (!Objects.equals(result.getBoolean("is_url_hidden"), bool)) {
            Assert.fail();
        }
    }
}
