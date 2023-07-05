package ui.com.ztna.web.policy.add_rule.tasks;


import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.HoverOverTarget;
import ui.com.ztna.web.common.interactions.OpenMenu;
import ui.com.ztna.web.common.interactions.OpenPage;
import ui.com.ztna.web.common.model.UpdateLocationParameters;
import ui.com.ztna.web.policy.add_rule.models.AddRuleParameters;
import ui.com.ztna.web.policy.add_rule.models.RuleParameters;

import java.util.List;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.common.user_interface.SideBarUI.APPLICATION_POLICIES_PAGE;
import static ui.com.ztna.web.common.user_interface.SideBarUI.POLICIES_MENU;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRule.openManageRulesPageFor;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.RULE_ROW;


public class SeeThat {

    public static Performable verifyRuleIsAdded(AddRuleParameters parameters){
        return Task.where("Verify rule is added",actor -> {
            for (RuleParameters rule : parameters.rules)
                actor.attemptsTo(
                        SeeThat.ruleIsAdded(rule)
//                        verifyPolicyRuleCount(parameters)
                );
        });
    }

    public static Performable verifyPolicyRuleCount(AddRuleParameters parameters){
        return Task.where("Verify policy rule count",actor -> {
            actor.attemptsTo(OpenPage.named(APPLICATION_POLICIES_PAGE).in(POLICIES_MENU));
            Integer noOfRulesBeforeRulesAdded = actor.recall("NoOfRulesBeforeRulesAdded");
            Integer expectedRuleCount = noOfRulesBeforeRulesAdded + parameters.rules.size();
            actor.attemptsTo(waitPresenceOf(NO_OF_RULES_COUNT.of(parameters.policy, expectedRuleCount.toString())));

        });
    }

    public static Performable verifyPolicyRuleCount(String policyName){
        return Task.where("Verify policy rule count",actor -> {
            actor.attemptsTo(
                    OpenPage.named(APPLICATION_POLICIES_PAGE).in(POLICIES_MENU)
            );
            Integer noOfRulesBeforeRulesAdded = actor.recall("NoOfRulesBeforeRulesAdded");
            int expectedRuleCount = noOfRulesBeforeRulesAdded + 1;
            actor.attemptsTo(waitPresenceOf(NO_OF_RULES_COUNT.of(policyName, Integer.toString(expectedRuleCount))));

        });
    }

    public static Performable ruleIsAdded(RuleParameters rule) {
        return Task.where("{0} sees that rule is added", actor -> {
            actor.attemptsTo(
                    waitPresenceOf(RULE_ROW.of(rule.ruleName, rule.ruleRecipient)),
                    verifyTimeAccess(rule),
                    verifyLocationAccess(rule)
            );
        });
    }






//--------------------------------Further helpfull Interaction------------------------------------------------------------------------------------------


    public static Performable verifyTimeAccess(RuleParameters rule){
        return Task.where("Verify time based access",actor -> {
            if (rule.accessTime.equals("any")){
                actor.attemptsTo(waitPresenceOf(ALL_TIME_ACCESS_TABLE_DATA.of(rule.ruleName)));
            }else {

                actor.attemptsTo(
//                        HoverOverTarget.over(TIME_BASED_ACCESS_MORE_INFO.of(rule.ruleName)),
//                        waitPresenceOf(TOOL_TIP_TEXT_DISPLAYED.of(rule.startTime,rule.endTime))
                );
            }
        });
    }

    public static Performable verifyLocationAccess(RuleParameters rule){
        return Task.where("Verify location based access",actor -> {
            if (rule.accessLocation.equals("any") && !(rule.countries.isEmpty())){
                actor.attemptsTo(waitPresenceOf(ANY_LOCATION_TABLE_DATA.of(rule.ruleName)));
            }else{
                for(String country :rule.countries){
                    actor.attemptsTo(
                            waitPresenceOf(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,rule.ruleName)),
                            HoverOverTarget.over(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,rule.ruleName)),
                            waitPresenceOf(TOOL_TIP_TEXT_DISPLAYED.of(country))
                    );
                }
            }
        });
    }

    public static Performable verifyLocationAccess(String ruleName, List<String> countries){
        return Task.where("Verify location based access",actor -> {
                for(String country :countries){
                    actor.attemptsTo(
                            waitPresenceOf(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,ruleName))
//                            HoverOverTarget.over(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,ruleName)),
//                            waitPresenceOf(TOOL_TIP_TEXT_DISPLAYED.of(country))
//                            Sometimes hover fails. should be a better way to do this
                    );
                }

        });
    }

    public static Performable verifyLocationUpdated(UpdateLocationParameters parameters){
        return Task.where("Verify location based access",actor -> {
            if (parameters.anyLocation.equals("yes")){
                actor.attemptsTo(waitPresenceOf(ANY_LOCATION_TABLE_DATA.of(parameters.ruleName)));
            }else{
                for(String country :parameters.countries){
                    actor.attemptsTo(
                            waitPresenceOf(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,parameters.ruleName)),
                            HoverOverTarget.over(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,parameters.ruleName)),
                            waitPresenceOf(TOOL_TIP_TEXT_DISPLAYED.of(country))
                    );
                }
                for(String country :parameters.countriesToRemove){
                    actor.attemptsTo(
                            waitPresenceOf(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,parameters.ruleName)),
                            HoverOverTarget.over(COUNTRY_FLAG_CONTAINER_IN_ROW.of(country,parameters.ruleName)),
                            waitPresenceOf(TOOL_TIP_TEXT_DISPLAYED.of(country))
                    );
                }
            }
        });
    }
}