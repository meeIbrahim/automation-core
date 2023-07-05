package ui.com.ztna.web.policy.delete_policy.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.FIFTEEN;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.common.user_interface.RemoveResourcesUI.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.REMOVE_POLICY_POPOVER_BUTTON;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_ROW_OPTION_BUTTON;
import static ui.com.ztna.web.policy.delete_policy.user_interfaces.DeleteAllPoliciesUI.FIRST_ROW_POLICY_NAME;
import static ui.com.ztna.web.policy.delete_policy.user_interfaces.DeleteAllPoliciesUI.First_ROW_POLICY_ACTION_ICON;
import static ui.com.ztna.web.policy.delete_policy.user_interfaces.DeletePolicyForm.*;

public class removePolicy {

    public static Task using(String policy) {
        return Task.where("{0} tries to delete policy named #policy",
                clickButton(POLICY_ROW_OPTION_BUTTON.of(policy)),
                clickButton(REMOVE_POLICY_POPOVER_BUTTON),
                waitPresenceOf(REMOVE_POLICY_FORM_TITLE),
                clickButton(REMOVE_POLICY_MODAL_BUTTON),
                waitNotPresenceOf(REMOVE_POLICY_FORM_TITLE)
        );
    }

}
