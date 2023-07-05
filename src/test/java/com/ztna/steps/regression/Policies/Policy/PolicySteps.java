package com.ztna.steps.regression.Policies.Policy;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.policies.PoliciesUI;
import ui.com.ztna.web.pages.policies.modals.UpdatePolicy;
import ui.com.ztna.web.policy.delete_policy.models.DeletePolicyParameters;
import ui.com.ztna.web.policy.update_policy.model.Update_Policy_Parameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.DUPLICATE_POLICY_ERROR_MESSAGE;


public class PolicySteps {

    Update_Policy_Parameters updatePolicyParameters;
    DeletePolicyParameters deletePolicyParameters;

    public static String updatedName = "updated_name";
    public static String updatedDescription = "updated_description";

    @When("User updates name for policy {reference}")
    public void userUpdatePolicyName(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        String newName = Data.generateName();
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                policy.action(PoliciesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Input.of(UpdatePolicy.NAME).with(newName),
                Modal.save()
        );
        theActorInTheSpotlight().remember(updatedName,newName);
    }
    @When("User updates description for policy {reference}")
    public void userUpdatePolicyDesc(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        String newDesc = Data.generateDescription();
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                policy.action(PoliciesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Input.of(UpdatePolicy.DESCRIPTION).with(newDesc),
                Modal.save()
        );
        theActorInTheSpotlight().remember(updatedDescription,newDesc);
    }
    @When("User updates name for policy {reference} with name of policy {reference}")
    public void updateDuplicateName(String referenceA, String referenceB) throws cachedNotFound {
        ZTAParameters policyA = RM.policy().cache().retrieve(referenceA);
        ZTAParameters policyB = RM.policy().cache().retrieve(referenceB);

        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );

        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(policyA.name));
        theActorInTheSpotlight().attemptsTo(
                policy.action(PoliciesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Input.of(UpdatePolicy.NAME).with(policyB.name),
                Modal.save()
        );
    }

    @Then("Name for policy {reference} should be updated")
    public void nameUpdatedForPolicy(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        String newName = theActorInTheSpotlight().recall(updatedName);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage(),
                Ensure.thatTheAnswerTo(ZTA.policies().table().contains(newName)).isTrue(),
                Ensure.thatTheAnswerTo(ZTA.policies().table().contains(parameters.name)).isFalse()
        );
    }
    @Then("Description for policy {reference} should be updated")
    public void descUpdatedForPolicy(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        String newDesc = theActorInTheSpotlight().recall(updatedDescription);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                policy.action(PoliciesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Ensure.thatTheAnswerTo(
                        Input.of(UpdatePolicy.DESCRIPTION).value()
                ).isEqualTo(newDesc)
        );
    }


    @Then("Duplicate Policy Conflict Error is shown")
    public void duplicateError(){
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(DUPLICATE_POLICY_ERROR_MESSAGE).isDisplayed(),
                Modal.close()
        );
    }




    @When("User removes policy {reference}")
    public void deletePolicy(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage()
        );
        Row policy = theActorInTheSpotlight().asksFor(ZTA.policies().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                policy.action(PoliciesUI.REMOVE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Modal.save()
        );
    }

    @Then("Policy {reference} should be deleted")
    public void policyDeleted(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.policy().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().openPage(),
                Ensure.thatTheAnswerTo(ZTA.policies().table().contains(parameters.name)).isFalse()
        );
    }
}
