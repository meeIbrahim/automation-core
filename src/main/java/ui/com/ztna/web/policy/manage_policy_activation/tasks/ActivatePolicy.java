package ui.com.ztna.web.policy.manage_policy_activation.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.Wait;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.ONE_HUNDRED_TWENTY;
import static constants.Waits.SIXTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.manage_policy_activation.Interactions.ActivationInteractions.openManageRulesPage;
import static ui.com.ztna.web.policy.manage_policy_activation.Interactions.ActivationInteractions.verifyStatus;

public class ActivatePolicy {

    public static Performable named(String policyName){

        return Task.where("Policy is activated",actor -> {
            actor.attemptsTo(
                    SeeThat.policyIsInactivated(policyName),
                    Click.on(POLICY_ROW_OPTION_BUTTON.of(policyName)),
                    WaitUntil.the(MANAGE_ACTIVATION_BUTTON,isPresent()),
                    Click.on(MANAGE_ACTIVATION_BUTTON),
                    WaitUntil.the(ACTIVATION_TOGGLE_BUTTON,isPresent()),
                    Click.on(ACTIVATION_TOGGLE_BUTTON),
                    Click.on(UPDATE_MANAGE_ACTIVATION_BUTTON));
        });
    }

    public static Performable rememberServiceName(String policyName){

        return Task.where("Remember service name",actor -> {

            actor.attemptsTo(waitPresenceOf(SERVICE_NAME.of(policyName)));
            String serviceName = SERVICE_NAME.of(policyName).resolveFor(actor).getText();
            actor.remember("Service",serviceName);

        });

    }

    public static Performable verifyRulesAreActivated(String policyName){
        return Task.where("Verify that all rules of the policy are activated",actor -> {
            actor.attemptsTo(
                    openManageRulesPage(policyName),
                    verifyStatus("Active"),
                    openEndUserPortal(),
                    waitPresenceOf(SERVICE_IN_END_USER_PORTAL.of(actor.recall("Service").toString()),ONE_HUNDRED_TWENTY),
                    openAdminPortal()

            );
        });
    }





}
