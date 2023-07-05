package ui.com.ztna.web.policy.add_rule.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.policy.add_rule.models.RuleParameters;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.MANAGE_RULES_DROPDOWN_OPTION;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_ROW_OPTION_BUTTON;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.fillAddRuleForm;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.saveAddRuleForm;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRuleForPoliciesPageUI.ADD_RULE_BUTTON;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.*;

public class AddRule {
    public static Task openManageRulesPageFor(String policy) {
        return Task.where("{0} opens manage rules page for policy named #policy",
                WaitUntil.the(POLICY_ROW_OPTION_BUTTON.of(policy), isClickable()),
                Click.on(POLICY_ROW_OPTION_BUTTON.of(policy)),
                WaitUntil.the(MANAGE_RULES_DROPDOWN_OPTION, isClickable()),
                Click.on(MANAGE_RULES_DROPDOWN_OPTION)
        );
    }

    public static Performable named(RuleParameters rule) {
        return Task.where("{0} adds rule", actor -> {
            actor.attemptsTo(
                    clickButton(ADD_RULE_BUTTON),
                    waitPresenceOf(ADD_A_NEW_RULE_FORM_TITLE),
                    fillAddRuleForm(rule),
                    saveAddRuleForm(),
                    waitNotPresenceOf(ADD_A_NEW_RULE_FORM_TITLE)
            );
        });
    }

    public static Performable withConflict(RuleParameters rule) {
        return Task.where("{0} adds rule", actor -> {
            actor.attemptsTo(
                    clickButton(ADD_RULE_BUTTON),
                    waitPresenceOf(ADD_A_NEW_RULE_FORM_TITLE),
                    fillAddRuleForm(rule),
                    saveAddRuleForm()
            );
        });
    }


}


