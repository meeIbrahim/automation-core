package ui.com.ztna.web.policy.delete_rule.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.FIFTEEN;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.*;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.REMOVE_RULE_ACTION_BUTTON;
import static ui.com.ztna.web.policy.delete_rule.user_interfaces.DeleteRuleForm.REMOVE_RULE_BUTTON;
import static ui.com.ztna.web.policy.delete_rule.user_interfaces.DeleteRuleForm.DELETE_RULE_FORM_TITLE;

public class DeleteRule {
    private static String rule;

    public static Task named(String rule) {
        DeleteRule.rule = rule;
        return Task.where("{0} deletes rule named #rule",
                Click.on(RULE_ROW_ACTION_BUTTON.of(rule)),
                WaitUntil.the(REMOVE_RULE_ACTION_BUTTON, isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(REMOVE_RULE_ACTION_BUTTON),
                WaitUntil.the(REMOVE_RULE_BUTTON, isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(REMOVE_RULE_BUTTON),
                WaitUntil.the(DELETE_RULE_FORM_TITLE, isNotPresent()).forNoMoreThan(THIRTY).seconds()
        );
    }
}
