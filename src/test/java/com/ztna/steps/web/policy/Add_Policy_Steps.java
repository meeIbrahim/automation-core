package com.ztna.steps.web.policy;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.interactions.OpenPage;
import ui.com.ztna.web.common.page.Page;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.policies.PoliciesUI;
import ui.com.ztna.web.policy.add_policy.models.AddPolicyParameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static ui.com.ztna.web.common.user_interface.SideBarUI.APPLICATION_POLICIES_PAGE;
import static ui.com.ztna.web.common.user_interface.SideBarUI.POLICIES_MENU;

public class Add_Policy_Steps {


    @Given("user is on policies page")
    public void onPoliciesPage() {
        theActorInTheSpotlight().attemptsTo(
                OpenPage.named(APPLICATION_POLICIES_PAGE).in(POLICIES_MENU),
//                OpenMenu.named(POLICIES_MENU),
                WaitUntil.the(Page.LOADER, isNotPresent())
        );
    }


    @When("User adds Policy {reference} for service {reference}")
    public void addPolicyFor(String policyRef,String serviceRef) throws cachedNotFound {
        ZTAParameters serviceParameters  = RM.service().cache().retrieve(serviceRef);
        ui.com.ztna.web.parameters.PolicyParameters policyParameters = Data.policyForService(serviceParameters.name).withDescription().get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().create(policyParameters)
        );
        RM.policy().cache().cache(policyRef,policyParameters);
    }
    @When("User adds Policy {reference} for Project {reference}")
    public void addPolicyForProject(String policyRef,String projectRef) throws cachedNotFound {
        ZTAParameters projectParameters  = RM.project().cache().retrieve(projectRef);
        ui.com.ztna.web.parameters.PolicyParameters policyParameters = Data.policyForProject(projectParameters.name).withDescription().get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().create(policyParameters)
        );
        RM.policy().cache().cache(policyRef,policyParameters);
    }

    @Then("Policy {reference} is added and is active")
    public void PolicyAdded(String reference) throws cachedNotFound {
        ZTAParameters policyParameters = RM.policy().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(policyParameters.name));
        assert policy != null;
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        policy.contains(PoliciesUI.ACTIVE_POLICY)
                ).isTrue()
        );
    }

    @When("User adds policy {reference} for Service {reference}")
    public void addPolicyForService(String referencePolicy,String referenceService) throws cachedNotFound {
        String ServiceName = RM.service().cache().retrieve(referenceService).name;
        ui.com.ztna.web.parameters.PolicyParameters parameters = Data.policyForService(ServiceName).get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().create(parameters)
        );
        RM.policy().cache().cache(referencePolicy, parameters);
    }

}
