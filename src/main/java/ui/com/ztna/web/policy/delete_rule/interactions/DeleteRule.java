package ui.com.ztna.web.policy.delete_rule.interactions;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.MANAGE_RULES_DROPDOWN_OPTION;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_ROW_OPTION_BUTTON;

public class DeleteRule {

    public static Task openManageRulesPageForPolicyNamed(String nameOfPolicy) {
        return Task.where("{0} opens manage rules page for policy named #nameOfPolicy",
                Click.on(POLICY_ROW_OPTION_BUTTON.of(nameOfPolicy)),
                Click.on(MANAGE_RULES_DROPDOWN_OPTION)
        );
    }
}
