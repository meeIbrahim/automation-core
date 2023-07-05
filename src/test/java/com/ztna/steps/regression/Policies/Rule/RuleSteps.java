package com.ztna.steps.regression.Policies.Rule;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.RM;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.interactions.OpenPage;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.rules.Questions;
import ui.com.ztna.web.pages.rules.Rules;
import ui.com.ztna.web.pages.rules.RulesUI;
import ui.com.ztna.web.pages.rules.modals.UpdateRule;
import ui.com.ztna.web.policy.Update_Rule.model.UpdateRuleParameters;

import java.util.List;

import static com.ztna.steps.regression.Policies.Policy.PolicySteps.updatedName;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static regression.tasks.Policies.Rule.RuleTasks.updateRuleLocation;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.common.user_interface.CommonUI.CANCEL_BUTTON;
import static ui.com.ztna.web.common.user_interface.SideBarUI.APPLICATION_POLICIES_PAGE;
import static ui.com.ztna.web.common.user_interface.SideBarUI.POLICIES_MENU;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.handleTimeAccess;
import static ui.com.ztna.web.policy.add_rule.tasks.SeeThat.verifyLocationAccess;
import static ui.com.ztna.web.policy.add_rule.tasks.SeeThat.verifyPolicyRuleCount;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.RULE_ROW;

public class RuleSteps {
    UpdateRuleParameters updateRuleParameters;

    @When("User adds Rule {reference} for Policy {reference}")
    public void addRuleForPolicy(String ruleRef, String policyRef) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyUser().withPolicy(policy.name);
        Rules rulePage = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy.name));
        theActorInTheSpotlight().attemptsTo(
                rulePage.create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @When("User adds Rule {reference} on Access Group for Policy {reference}")
    public void addRuleForPolicyAccessGroup(String ruleRef, String policyRef) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyGroup().withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @When("User adds Rule {reference} on Access Group for Policy {reference} starting after {word} H {word} M and Ending after {word} H and {word} M")
    public void addRuleForPolicyAccessGroupTimeBased(String ruleRef, String policyRef,String sth,String stm,String eth,String etm) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyGroup().timeBased(sth,stm,eth,etm).withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @When("User adds Rule {reference} on User for Policy {reference} starting after {word} H {word} M and Ending after {word} H and {word} M")
    public void addRuleForPolicyUserTimeBased(String ruleRef, String policyRef,String sth,String stm,String eth,String etm) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyUser().timeBased(sth,stm,eth,etm).withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @When("User adds Rule {reference} on User for Policy {reference} for countries {list}")
    public void addRuleForPolicyUserlocation(String ruleRef, String policyRef, List<String> countries) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyUser().locationBased(countries).withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                OpenPage.named(APPLICATION_POLICIES_PAGE).in(POLICIES_MENU)
                );
        theActorInTheSpotlight().remember("NoOfRulesBeforeRulesAdded", Text.of(NO_OF_RULES.of(policy.name)).asInteger().answeredBy(theActorInTheSpotlight()));
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @When("User adds Rule {reference} on Access Group for Policy {reference} for countries {list}")
    public void addRuleForPolicyAGlocation(String ruleRef, String policyRef, List<String> countries) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyGroup().locationBased(countries).withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                OpenPage.named(APPLICATION_POLICIES_PAGE).in(POLICIES_MENU)
        );
        theActorInTheSpotlight().remember("NoOfRulesBeforeRulesAdded", Text.of(NO_OF_RULES.of(policy.name)).asInteger().answeredBy(theActorInTheSpotlight()));
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }



    @When("User adds Rule {reference} on User for Policy {reference}")
    public void addRuleForPolicyUser(String ruleRef, String policyRef) throws cachedNotFound, UsedResource {
        ZTAParameters policy = RM.policy().cache().retrieve(policyRef);
        ui.com.ztna.web.parameters.RuleParameters parameters = Data.ruleForAnyUser().withPolicy(policy.name);
        theActorInTheSpotlight().attemptsTo(
                ZTA.policies().rules(policy.name).answeredBy(theActorInTheSpotlight()).create(parameters)
        );
        RM.rule().cache().cache(ruleRef,parameters);
    }

    @Then("Rule {reference} should be added")
    public void ruleIsAdded(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(reference);
        Rules rulePage = theActorInTheSpotlight().asksFor(ZTA.policies().rules(parameters.parent));
        Row rule = theActorInTheSpotlight().asksFor(
                rulePage.table().row(parameters.name)
        );
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Questions.isActive(rule)
                ).isTrue()
        );
    }


    @When("User adds rule {reference} in policy {reference} for any user")
    public void addRulesInPolicy(String referenceRule, String referencePolicy) throws UsedResource, cachedNotFound {
        ZTAParameters user = RM.user().getFree("");
        ZTAParameters policyParams = RM.policy().cache().retrieve(referencePolicy);
        ui.com.ztna.web.parameters.RuleParameters ruleParameters = Data.ruleForUser(user.name).withPolicy(policyParams.name);
        Rules rulePage = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policyParams.name));
        theActorInTheSpotlight().attemptsTo(
                rulePage.create(ruleParameters)
        );
    }


    @When("rule {reference} is {word}")
    public void rulesStatusWait(String ruleRef, String STATUS) throws cachedNotFound {

        String ruleName = RM.rule().cache().retrieve(ruleRef).name;
        theActorInTheSpotlight().attemptsTo(waitPresenceOf(RULE_ROW.of(ruleName,STATUS)));

    }

    @When("rule {reference} is {word} after {word} mins")
    public void rulesStatusWait(String ruleRef, String STATUS, String mins) throws cachedNotFound {
        String ruleName = RM.rule().cache().retrieve(ruleRef).name;
        theActorInTheSpotlight().attemptsTo(waitPresenceOf(RULE_ROW.of(ruleName,STATUS),Integer.parseInt(mins)*60));

    }


    @Then("Duplicate Rule Conflict Error is shown")
    public void duplicateError(){
        theActorInTheSpotlight().attemptsTo(
                waitPresenceOf(DUPLICATE_RULE_ERROR_MESSAGE),
                clickButton(CANCEL_BUTTON),
                waitNotPresenceOf(CANCEL_BUTTON)
        );
    }

    @Then("user verifies that rule {reference} is added for {list}")
    public void ruleIsAddedForCountries(String ruleRef, List<String> countries) throws cachedNotFound {
        String ruleName = RM.rule().cache().retrieve(ruleRef).name;
        theActorInTheSpotlight().attemptsTo(verifyLocationAccess(ruleName,countries));
    }

    @Then("user verifies that policy {reference} rule count has increased")
    public void policyCountVerification(String policyRef) throws cachedNotFound {
        String policyName = RM.policy().cache().retrieve(policyRef).name;
        theActorInTheSpotlight().attemptsTo(verifyPolicyRuleCount(policyName));

    }

    @When("User updates name for rule {reference}")
    public void userUpdateRuleName(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(reference);
        String newName = Data.generateName();
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(parameters.parent).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Input.of(UpdateRule.NAME).with(newName),
                Modal.save()
        );
        theActorInTheSpotlight().remember(updatedName,newName);
    }

    @Then("Name for rule {reference} should be updated")
    public void nameUpdatedForPolicy(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(reference);
        String newName = theActorInTheSpotlight().recall(updatedName);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.policies().rules(parameters.parent).answeredBy(theActorInTheSpotlight()).table().contains(parameters.name)).isFalse(),
                Ensure.thatTheAnswerTo(ZTA.policies().rules(parameters.parent).answeredBy(theActorInTheSpotlight()).table().contains(newName)).isTrue(),
                waitPresenceOf(RULE_ROW.of(newName,"Active"),30)
        );
    }

    @When("User updates name for rule {reference} with name of rule {reference}")
    public void updateDuplicateName(String referenceA, String referenceB) throws cachedNotFound {
        ZTAParameters ruleA = RM.rule().cache().retrieve(referenceA);
        ZTAParameters ruleB = RM.rule().cache().retrieve(referenceB);
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(ruleA.parent).answeredBy(theActorInTheSpotlight()).table().row(ruleA.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Input.of(UpdateRule.NAME).with(ruleB.name),
                Modal.next()
        );
    }


    @When("user updates location access for rule {word} and now rule is accessible in {list}")
    public void updateLocationAccessForRule(String ruleRef, List<String> countries) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        String policy = parameters.parent;
        theActorInTheSpotlight().attemptsTo(ZTA.policies().manageRules(policy));
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                updateRuleLocation(countries),
                Modal.save()
        );
    }

    @When("user updates time based access for rule {word} and now rule is accessible starting after {word} H {word} M and Ending after {word} H and {word} M")
    public void updateTimeBasedAccessForRule(String ruleRef,String sth,String stm,String eth,String etm) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        String policy = parameters.parent;
        theActorInTheSpotlight().attemptsTo(ZTA.policies().manageRules(policy));
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                handleTimeAccess(sth,stm,eth,etm),
                Modal.save()
        );
    }

    @When("user updates time based access for time based rule {word} and now rule is accessible starting after {word} H {word} M and Ending after {word} H and {word} M")
    public void updateTimeBasedAccessForTBRule(String ruleRef,String sth,String stm,String eth,String etm) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        String policy = parameters.parent;
        theActorInTheSpotlight().attemptsTo(ZTA.policies().manageRules(policy));
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                waitPresenceOf(TIME_BASED_ACCESS_CHECKBOX_UNCHECKED),
                Click.on(TIME_BASED_ACCESS_CHECKBOX),
                waitPresenceOf(TIME_BASED_ACCESS_CHECKBOX_CHECKED),
                handleTimeAccess(sth,stm,eth,etm),
                Modal.save()
        );
    }

    @When("user removes time based access for rule {word}")
    public void updateTimeBasedAccessForRule(String ruleRef) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        String policy = parameters.parent;
        theActorInTheSpotlight().attemptsTo(ZTA.policies().manageRules(policy));
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                waitPresenceOf(TIME_BASED_ACCESS_CHECKBOX_UNCHECKED),
                Click.on(TIME_BASED_ACCESS_CHECKBOX),
                waitPresenceOf(TIME_BASED_ACCESS_CHECKBOX_CHECKED),
                Modal.save()
        );
    }

    @When("user removes location based access for rule {word}")
    public void updatelocationBasedAccessForRule(String ruleRef) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        String policy = parameters.parent;
        theActorInTheSpotlight().attemptsTo(ZTA.policies().manageRules(policy));
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(policy).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.UPDATE_ACTION_MENU),
                waitPresenceOf(LOCATION_BASED_ACCESS_CHECKBOX_UNCHECKED),
                Click.on(LOCATION_BASED_ACCESS_CHECKBOX),
                waitPresenceOf(LOCATION_BASED_ACCESS_CHECKBOX_CHECKED),
                Modal.save()
        );
    }

    @When("location access is removed for rule {word}")
    public void locationAccessIsRemoved(String ruleRef) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(ruleRef);
        theActorInTheSpotlight().attemptsTo(waitPresenceOf(ANY_LOCATION_TABLE_DATA.of(parameters.name)));
    }

    @When("User removes rule {reference}")
    public void userRemovesRule(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.rule().cache().retrieve(reference);
        Row rule = theActorInTheSpotlight().asksFor(ZTA.policies().rules(parameters.parent).answeredBy(theActorInTheSpotlight()).table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                rule.action(RulesUI.REMOVE_ACTION_MENU),
                Modal.waitUntilOpen(),
                Modal.save()
        );

    }

    @When("rule {reference} is removed successfully")
    public void ruleIsRemoved(String reference) throws  cachedNotFound{
        ZTAParameters parameters = RM.rule().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(waitNotPresenceOf(RULE_ROW.of(parameters.name),60));
    }


}

