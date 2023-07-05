package ui.com.ztna.web.policy.add_policy.tasks;

import net.serenitybdd.screenplay.AnonymousPerformableFunction;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.policy.add_policy.models.PolicyParameters;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;

public class SeeThat {
    public static AnonymousPerformableFunction policyIsAdded(PolicyParameters policy) {
        return Task.where("{0} sees that policy is added", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(POLICY_ROW.of(policy.nameOfPolicy), isPresent()).forNoMoreThan(FIFTEEN).seconds(),
//                    WaitUntil.the(INACTIVE_LABEL.of(policy.nameOfPolicy), isPresent()),
                    WaitUntil.the(ACTIVE_LABEL.of(policy.nameOfPolicy), isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                    WaitUntil.the(POLICY_RESOURCE.of(policy.nameOfPolicy, policy.selectResource.resourceName), isPresent()).forNoMoreThan(FIFTEEN).seconds()
            );
            if (policy.selectResource.resourceType.equals("Service"))
                actor.attemptsTo(
                        WaitUntil.the(SERVICE_LABEL.of(policy.nameOfPolicy,policy.selectResource.resourceName), isPresent()).forNoMoreThan(FIFTEEN).seconds()
                );
            else if (policy.selectResource.resourceType.equals("Project"))
                actor.attemptsTo(
                        WaitUntil.the(PROJECT_LABEL.of(policy.nameOfPolicy ,policy.selectResource.resourceName), isPresent()).forNoMoreThan(FIFTEEN).seconds()
                );

        });
    }
}

