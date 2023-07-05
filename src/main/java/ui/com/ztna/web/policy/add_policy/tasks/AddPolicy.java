package ui.com.ztna.web.policy.add_policy.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.common.interactions.OpenMenu;
import ui.com.ztna.web.policy.add_policy.models.PolicyParameters;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static ui.com.ztna.web.common.user_interface.SideBarUI.POLICIES_MENU;
import static ui.com.ztna.web.policy.add_policy.interactions.AddPolicy.fillAddPolicyForm;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesPageUI.ADD_POLICY_BUTTON;

public class AddPolicy {
    public static Task using(PolicyParameters policyParameters) {
        return Task.where("{0} adds policy",
                WaitUntil.the(ADD_POLICY_BUTTON,isClickable()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(ADD_POLICY_BUTTON),
                fillAddPolicyForm(policyParameters),
                OpenMenu.named(POLICIES_MENU)
        );
    }
}
