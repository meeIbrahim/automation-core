package com.ztna.steps.resource.manager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.applicationGroups.ApplicationGroups;
import ui.com.ztna.web.pages.connectors.Questions;
import ui.com.ztna.web.pages.connectors.modals.ManageAccess;
import ui.com.ztna.web.pages.connectors.modals.ManageState;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;
import ui.com.ztna.web.parameters.RuleParameters;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class ResourceManager {
    @Given("{word} {reference} is active")
    public void ResourceActive(String resource, String Reference) throws UsedResource, ResourceException {
        RM.resource(resource).getActive(Reference);
    }
    @Given("{word} {reference} is available")
    public void ResourceAvailable(String resource, String Reference) throws UsedResource, ResourceException {
        RM.resource(resource).getFree(Reference);

    }
    @Given("{word} Site {reference} is active")
    public void SiteAvailable(String host, String reference) throws UsedResource, ResourceException {
        RM.site().getActive(reference,host);
    }

    @Given("rule {reference} for {word} on {word} is available")
    public void
    ResourceMadeAvailable(String ruleRef, String ruleattachment, String policyAttachment ) throws UsedResource, ResourceException, cachedNotFound {
        String policy="";
        RuleParameters parameters = null;
        if(policyAttachment.equals("project")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",true).name;

            }
        }else if(policyAttachment.equals("service")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",false).name;

            }
        }else {
            throw new IllegalArgumentException();
        }
        if (ruleattachment.equals("user")) {
            parameters = Data.ruleForAnyUser().withPolicy(policy);
        }else if(ruleattachment.equals("accessgroup")){
            parameters = Data.ruleForAnyGroup().withPolicy(policy);
        }else{
            throw new IllegalArgumentException();
        }
        theActorInTheSpotlight().attemptsTo(RM.rule().page().create(parameters));
        RM.rule().cache().cache(ruleRef,parameters);

    }

    @Given("rule {reference} for {word} on {word} is available with time starting after {word} H {word} M and Ending after {word} H and {word} M")
    public void
    ResourceMadeAvailableTimeBased(String ruleRef, String ruleattachment, String policyAttachment, String sth, String stm, String eth, String etm ) throws UsedResource, ResourceException, cachedNotFound {
        String policy="";
        RuleParameters parameters = null;
        if(policyAttachment.equals("project")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",true).name;

            }
        }else if(policyAttachment.equals("service")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",false).name;

            }
        }else {
            throw new IllegalArgumentException();
        }
        if (ruleattachment.equals("user")) {
            parameters = Data.ruleForAnyUser().timeBased(sth,stm,eth,etm).withPolicy(policy);
        }else if(ruleattachment.equals("accessgroup")){
            parameters = Data.ruleForAnyGroup().timeBased(sth,stm,eth,etm).withPolicy(policy);
        }else{
            throw new IllegalArgumentException();
        }
        theActorInTheSpotlight().attemptsTo(RM.rule().page().create(parameters));
        RM.rule().cache().cache(ruleRef,parameters);

    }

    @Given("rule {reference} for {word} on {word} is available with countries {list}")
    public void
    ResourceMadeAvailableLocationBased(String ruleRef, String ruleattachment, String policyAttachment, List<String> countries ) throws UsedResource, ResourceException, cachedNotFound {
        String policy="";
        RuleParameters parameters = null;
        if(policyAttachment.equals("project")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",true).name;

            }
        }else if(policyAttachment.equals("service")){
            if(RM.policy().cache().isPresent("A")){
                policy = RM.policy().cache().retrieve("A").name;
            } else{
                policy = RM.policy().getFree("A",false).name;

            }
        }else {
            throw new IllegalArgumentException();
        }
        if (ruleattachment.equals("user")) {
            parameters = Data.ruleForAnyUser().locationBased(countries).withPolicy(policy);
        }else if(ruleattachment.equals("accessgroup")){
            parameters = Data.ruleForAnyGroup().locationBased(countries).withPolicy(policy);
        }else{
            throw new IllegalArgumentException();
        }
        theActorInTheSpotlight().attemptsTo(RM.rule().page().create(parameters));
        RM.rule().cache().cache(ruleRef,parameters);

    }




    @Given("policy {reference} is available with project")
    public void PolicyAvailable(String Reference) throws UsedResource, ResourceException {
        RM.policy().getFree(Reference,true);
    }

    @Given("policy {reference} is available with service")
    public void PolicyAvailableWUser(String Reference) throws UsedResource, ResourceException {
        RM.policy().getFree(Reference,false);
    }


    @Given("{word} connector {reference} is active")
    public void ConnectorAvailable(String host, String reference) throws UsedResource, ResourceException {
        RM.connector().getActive(reference,host);
    }

    @Given("HA Relay {reference} is active")
    public void HARelayActive(String reference) throws UsedResource {
        RM.relay().getActive(reference,true);
    }
    @Given("HA Relay {reference} is available")
    public void HARelayFree(String reference) throws UsedResource {
        RM.relay().getFree(reference,true);
    }
    @Given("Non-HA Relay {reference} is active")
    public void NonHARelayActive(String reference) throws UsedResource {
        RM.relay().getActive(reference,false);
    }
    @Given("Non-HA {reference} is available")
    public void NonHARelayFree(String reference) throws UsedResource {
        RM.relay().getFree(reference,false);
    }

    @Given("{word} {reference} is attached to {word} {reference}")
    public void ResourceAttached(String resourceA, String ReferenceA,String resourceB, String ReferenceB) throws UsedResource {
        RM.dependentResource(resourceA).getActive(ReferenceA,ReferenceB);
    }

    @Given("Project {reference} is created with webApp {reference}")
    public void ProjectAttached(String projectRef, String serviceRef) throws UsedResource {
        try {
            RM.service().webApp().getActive(serviceRef);
            ZTAParameters service = RM.service().cache().retrieve(serviceRef);
            ApplicationGroups page = new ApplicationGroups();
            ApplicationGroupParameters parameters = Data.applicationGroup().withServices(service.name).get();
            theActorInTheSpotlight().attemptsTo(
                    page.create(parameters)
            );
            RM.project().cache().cache(projectRef,parameters);

        } catch (Exception e)
        {
            throw new ResourceException("Project with Service");
        }

    }

    @Given("Access group {reference} contains users {list}")
    public void AttachedResource(String Reference, List<String> users){

    }

    @Given("{word} is created with references {list}")
    public void createResource(String resource, List<String> references) throws UsedResource {
        for(String reference : references ){
             RM.resource(resource).create(reference);
        }
    }

    @Given("Connector {reference} is attached to HA Relay {reference}")
    public void connectorTOHA(String Connector,String Relay) throws UsedResource {
        RM.relay().getActive(Relay,true);
        RM.site().create("Z",Relay);
        RM.connector().create(Connector,"Z");
    }
    @Given("Site {reference} is attached to HA Relay {reference}")
    public void siteTOHA(String Site,String Relay) throws UsedResource, cachedNotFound {
        RM.relay().getActive(Relay,true);
        RM.site().getActive(Site,Relay);

    }

    @Given("HA Relay attached to Connector {reference} and Service {reference} is disabled")
    public void HARelayIsDisabled(String Connector,String Service) throws UsedResource {
        RM.relay().getFree("",true);
    }

    @Given("webApp {reference} is disabled")
    public void webAppDisabled(String reference){
        ZTAParameters disabledService = null;
        try {
            disabledService = RM.service().cache().retrieve(reference);
        } catch (cachedNotFound ignored) {}
        if (disabledService == null){
            try {
                theActorInTheSpotlight().attemptsTo(
                        ZTA.services().webApp().openPage()
                );
                Iterator<Row> iterator = ZTA.services().webApp().table().rowIterator().answeredBy(theActorInTheSpotlight());
                while (iterator.hasNext()){
                    Row webApp = iterator.next();
                    if (webApp.contains(ServicesUI.DISABLED_STATUS).answeredBy(theActorInTheSpotlight())) {
                        disabledService = ZTA.services().webApp().question().getParameters(webApp).answeredBy(theActorInTheSpotlight());
                        RM.service().webApp().cache().cache(reference,disabledService);
                        break;
                    }
                }
            } catch (Exception e){
                throw new ResourceException("Disabled WebApp");
            }
        }
    }
    @Given("Connector {reference} is disabled")
    public void ConnectorDisabled(String reference) throws UsedResource, cachedNotFound {
        ZTAParameters disabledConnector = null;
        try {
            disabledConnector = RM.connector().cache().retrieve(reference);
        }
        catch (cachedNotFound ignored) {}
        if (disabledConnector == null) {
            try {
                theActorInTheSpotlight().attemptsTo(
                        ZTA.serviceConnectors().openPage()
                );
                Iterator<Row> iterator = ZTA.serviceConnectors().table().rowIterator().answeredBy(theActorInTheSpotlight());
                while (iterator.hasNext()) {
                    Row connector = iterator.next();
                    if (Questions.isDisabled(connector).answeredBy(theActorInTheSpotlight())) {
                        disabledConnector = ZTA.serviceConnectors().question().getParameters(connector).answeredBy(theActorInTheSpotlight());
                        RM.connector().cache().cache(reference, disabledConnector);
                        break;
                    }
                }
            } catch (Exception e) {
                throw new ResourceException("Disabled Connector");
            }
        }
        if (disabledConnector == null) {
            RM.connector().getActive(reference);
            disabledConnector = RM.connector().cache().retrieve(reference);
        }
        theActorInTheSpotlight().attemptsTo(
                ZTA.serviceConnectors().openPage()
        );
        Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(disabledConnector.name));
        theActorInTheSpotlight().attemptsTo(
                ManageState.disableConnector(connector)
        );

    }

    @Given("Disabled Connector {reference} that is attached to service {reference}")
    public void ConnectorWithServiceDisabled(String connectorRef,String serviceRef) throws UsedResource, cachedNotFound {
        RM.service().webApp().getActive(serviceRef, connectorRef);
        ZTAParameters Connector = RM.connector().cache().retrieve(connectorRef);
        try {
            theActorInTheSpotlight().attemptsTo(
                    ZTA.serviceConnectors().openPage()
            );
            Row connector = theActorInTheSpotlight().asksFor(
                    ZTA.serviceConnectors().table().row(Connector.name)
            );
            theActorInTheSpotlight().attemptsTo(
                    ManageState.disableConnector(connector)
            );
        } catch (Exception e) {
            throw new ResourceException("Disabled Connector - " + connectorRef);
        }
    }

    @Given("Connector {reference} is revoked")
    public void setUpRevokedConnector(String reference) throws UsedResource, cachedNotFound {
        ZTAParameters revokedConnector = null;
        try {
            theActorInTheSpotlight().attemptsTo(
                    ZTA.serviceConnectors().openPage()
            );
            Iterator<Row> iterator = ZTA.serviceConnectors().table().rowIterator().answeredBy(theActorInTheSpotlight());
            while(iterator.hasNext()){
                Row connector = iterator.next();
                if (Questions.isRevoked(connector).answeredBy(theActorInTheSpotlight())){
                    revokedConnector = ZTA.serviceConnectors().question().getParameters(connector).answeredBy(theActorInTheSpotlight());
                    RM.connector().cache().cache(reference,revokedConnector);
                    break;
                }
            }
        } catch (Exception e)
        {
            throw new ResourceException("Revoked Connector");
        }
        if (revokedConnector == null){
            RM.connector().getActive(reference);
            revokedConnector = RM.connector().cache().retrieve(reference);
            Row connector = theActorInTheSpotlight().asksFor(ZTA.serviceConnectors().table().row(revokedConnector.name));
            theActorInTheSpotlight().attemptsTo(
                    ManageAccess.revokeAccess(connector)
            );
        }
    }

    @Given("Revoked Connector {reference} that is attached to service {reference}")
    public void RevokedConnectorWithService(String connectorRef,String serviceRef) throws UsedResource, cachedNotFound {
        RM.service().webApp().getActive(serviceRef, connectorRef);
        ZTAParameters Connector = RM.connector().cache().retrieve(connectorRef);
        try {
            theActorInTheSpotlight().attemptsTo(
                    ZTA.serviceConnectors().openPage()
            );
            Row connector = theActorInTheSpotlight().asksFor(
                    ZTA.serviceConnectors().table().row(Connector.name)
            );
            theActorInTheSpotlight().attemptsTo(
                    ManageAccess.revokeAccess(connector)
            );
        } catch (Exception e) {
            throw new ResourceException("Revoked Connector - " + connectorRef);
        }
    }


    @Then("Policy {reference} should be active")
    public void PolicyIsActive(String Reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(Reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(ZTA.policies().isActive(policy))
                                .forTime(Duration.ofSeconds(50))
                                .untilEqualTo(true)
                                .isSuccess()
                        ).isTrue()
        );
    }

    @Given("Empty Project {reference} is available")
    public void emptyProject(String reference){
        ZTAParameters emptyProject = null;
        try {
            theActorInTheSpotlight().attemptsTo(
                    ZTA.projects().openPage()
            );
            Iterator<Row> allProjects = theActorInTheSpotlight().asksFor(ZTA.projects().table().rowIterator());
            while (allProjects.hasNext()){
                Row project = allProjects.next();
                if (!ui.com.ztna.web.pages.applicationGroups.Questions.hasServices(project).answeredBy(theActorInTheSpotlight())){
                    emptyProject = ZTA.projects().question().getParameters(project).answeredBy(theActorInTheSpotlight());
                }
            }
            if (emptyProject == null){
                RM.project().create(reference);
            }
            else {
                RM.project().cache().cache(reference,emptyProject);
            }
        } catch (Exception e){
            throw new ResourceException("Empty Project - " + reference);
        }
    }
    @Given("Project {reference} is available with Services")
    public void ProjectWithServices(String reference){
        ZTAParameters ServiceProject = null;
        try {
            theActorInTheSpotlight().attemptsTo(
                    ZTA.projects().openPage()
            );
            Iterator<Row> allProjects = theActorInTheSpotlight().asksFor(ZTA.projects().table().rowIterator());
            while (allProjects.hasNext()){
                Row project = allProjects.next();
                if (ui.com.ztna.web.pages.applicationGroups.Questions.hasServices(project).answeredBy(theActorInTheSpotlight())){
                    ServiceProject = ZTA.projects().question().getParameters(project).answeredBy(theActorInTheSpotlight());
                }
            }
            if (ServiceProject == null){
                RM.service().webApp().getActive("Z");
                ZTAParameters service = RM.service().cache().retrieve("Z");
                ApplicationGroups page = new ApplicationGroups();
                ApplicationGroupParameters parameters = Data.applicationGroup().withServices(service.name).get();
                theActorInTheSpotlight().attemptsTo(
                        page.create(parameters)
                );
                RM.project().cache().cache(reference,parameters);
            }
            else {
                RM.project().cache().cache(reference,ServiceProject);
            }
        } catch (Exception e){
            throw new ResourceException("Empty Project - " + reference);
        }
    }



}
