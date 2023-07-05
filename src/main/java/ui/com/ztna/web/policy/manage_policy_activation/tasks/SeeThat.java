package ui.com.ztna.web.policy.manage_policy_activation.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_ACTIVATED_STATUS;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_INACTIVATED_STATUS;

public class SeeThat {


    public static Performable policyIsActivated(String policyName){

        return Task.where("Ensure that policy is activated", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(POLICY_ACTIVATED_STATUS.of(policyName),isPresent()).forNoMoreThan(10).seconds());
        });
    }

    public static Performable policyIsInactivated(String policyName){

        return Task.where("Ensure that policy is deactivated",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(POLICY_INACTIVATED_STATUS.of(policyName),isPresent()).forNoMoreThan(10).seconds());
        });
    }
}
