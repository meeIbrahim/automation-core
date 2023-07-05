package com.ztna.steps.web.site;

import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.parameters.AwsSiteParameters;
import ui.com.ztna.web.parameters.AzureSiteParameters;
import ui.com.ztna.web.parameters.GcpSiteParameters;
import ui.com.ztna.web.parameters.SiteParameters;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Add_Site_Steps {


    @Given("user is on sites page")
    public void onSitePage() {
        theActorInTheSpotlight().attemptsTo(ZTA.sites().openPage());
    }
    @Given("User is on sites page")
    public void openSitePage() {
        theActorInTheSpotlight().attemptsTo(ZTA.sites().openPage());
    }

    @When("user adds aws site {reference} with relay {reference} and integration {reference}")
    public void userAddsAwsSiteAWithRelayAAndIntegrationA(String siteRef, String relayRef, String integrationRef) throws cachedNotFound {
        ZTAParameters relayParams = RM.relay().cache().retrieve(relayRef);
        ZTAParameters integrationParams=RM.awsIntegrations().cache().retrieve(integrationRef);

        AwsSiteParameters siteParameters = Data.awsSite().accountID(integrationParams.identifier).withRelay(relayParams.name).getParameters();

        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().create(siteParameters)
        );
        RM.site().cache().cache(siteRef,siteParameters);
    }@When("user adds aws site {reference} in region {word} with relay {reference} and integration {reference}")
    public void awsSiteAWithRelayAAndIntegrationA(String siteRef, String region,String relayRef, String integrationRef) throws cachedNotFound {
        ZTAParameters relayParams = RM.relay().cache().retrieve(relayRef);
        ZTAParameters integrationParams=RM.awsIntegrations().cache().retrieve(integrationRef);

        AwsSiteParameters siteParameters = Data.awsSite().region(region).accountID(integrationParams.identifier).withRelay(relayParams.name).getParameters();

        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().createAwsSite(siteParameters)
        );
        RM.site().cache().cache(siteRef,siteParameters);
    }

    @When("user adds azure site {reference} with relay {reference} and integration {reference}")
    public void userAddsAzureSiteWithRelayandIntegration(String siteRef, String relayRef, String integrationRef) throws cachedNotFound {
        ZTAParameters relayParams = RM.relay().cache().retrieve(relayRef);
        ZTAParameters integrationParams=RM.azureIntegrations().cache().retrieve(integrationRef);
        AzureSiteParameters siteParameters = Data.azureSite().subscription(integrationParams.identifier).withRelay(relayParams.name).getParameters();

        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().create(siteParameters)
        );
        RM.site().cache().cache(siteRef,siteParameters);

    }

    @When("user adds azure site {reference} in resource group {word} with relay {reference} and integration {reference}")
    public void addsAzureSiteWithRelayandIntegration(String siteRef, String resourceGroup, String relayRef, String integrationRef) throws cachedNotFound {
        ZTAParameters relayParams = RM.relay().cache().retrieve(relayRef);
        ZTAParameters integrationParams=RM.azureIntegrations().cache().retrieve(integrationRef);
        AzureSiteParameters siteParameters = Data.azureSite().resourceGroup(resourceGroup).subscription(integrationParams.identifier).withRelay(relayParams.name).getParameters();

        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().createAzureSite(siteParameters)
        );
        RM.site().cache().cache(siteRef,siteParameters);

    }

    @When("user adds gcp site {reference} with relay {reference} and integration {reference}")
    public void userAddsGcpSiteWithRelayAndIntegration(String siteRef, String relayRef, String integrationRef) throws cachedNotFound {
        ZTAParameters relayParams = RM.relay().cache().retrieve(relayRef);
        ZTAParameters integrationParams=RM.gcpIntegrations().cache().retrieve(integrationRef);
        GcpSiteParameters siteParameters = Data.gcpSite().project(integrationParams.identifier).withRelay(relayParams.name).getParameters();

        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().create(siteParameters)
        );
        RM.site().cache().cache(siteRef,siteParameters);

    }

    @When("User adds site {reference} with relay {reference}")
    public void userAddsSiteA(String reference, String relayRef) throws cachedNotFound {
        ZTAParameters relayParameters = RM.relay().cache().retrieve(relayRef);
        SiteParameters siteParameters = Data.onPremSite().withRelay(relayParameters.name).getParameters();
        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().create(siteParameters)
        );
        RM.site().cache().cache(reference,siteParameters);
    }


    @Given("User adds site {reference} with relays")
    public void with_a_horizontal_list(String reference,@Transpose List<String> relays) throws cachedNotFound{

       ArrayList<String> relayNames= new ArrayList<String>();
        for (String relay : relays) {
            ZTAParameters relayParams = RM.relay().cache().retrieve(relay);
            relayNames.add(relayParams.name);
        }
        System.out.println(List.of(relayNames));
        SiteParameters siteParameters = Data.onPremSite().withRelays(relayNames).getParameters();
        theActorInTheSpotlight().attemptsTo(
                ZTA.sites().create(siteParameters)
        );
        RM.site().cache().cache(reference,siteParameters);

    }


    @Then("Site {reference} should be added")
    public void siteAShouldBeAdded(String reference) throws cachedNotFound {
        ZTAParameters siteParameters = RM.site().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.sites().table().contains(siteParameters.name)).isTrue()
        );
    }
    @Then("Site {reference} should be present")
    public void siteShouldBeAdded(String reference) throws cachedNotFound {
        siteAShouldBeAdded(reference);
    }
}