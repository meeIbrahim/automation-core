package ui.com.ztna.web.policy.manage_policy_activation.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.apache.hc.core5.util.Args;

import static constants.Waits.ONE_HUNDRED_TWENTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.manage_policy_activation.Interactions.ActivationInteractions.openManageRulesPage;
import static ui.com.ztna.web.policy.manage_policy_activation.Interactions.ActivationInteractions.verifyStatus;

public class InactivatePolicy {

    public static Performable named(String policyName){

        return Task.where("Policy is inactivated", actor -> {

            actor.attemptsTo(
                    SeeThat.policyIsActivated(policyName),
                    clickButton(POLICY_ROW_OPTION_BUTTON.of(policyName)),
                    clickButton(MANAGE_ACTIVATION_BUTTON),
                    clickButton(ACTIVATION_TOGGLE_BUTTON),
                    clickButton(UPDATE_MANAGE_ACTIVATION_BUTTON));
        });



    }

    public static Performable verifyRulesAreInactivated(String policyName){
        return Task.where("Verify that all rules of the policy are activated",actor -> {
            actor.attemptsTo(
                    openManageRulesPage(policyName),
                    verifyStatus("Inactive"),
                    openEndUserPortal(),
                    waitNotPresenceOf(SERVICE_IN_END_USER_PORTAL.of(actor.recall("Service").toString())),
                    openAdminPortal()

            );
        });
    }



}
