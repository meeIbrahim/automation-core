package ui.com.ztna.web.policy.add_policy.interactions;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.policy.add_policy.models.PolicyParameters;

import static constants.Waits.FIFTEEN;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.AddPolicyFormUI.*;

public class AddPolicy {

    public static Task fillAddPolicyForm(PolicyParameters policyParameters) {
        return Task.where("{0} fills add policy form",

                Enter.keyValues(policyParameters.nameOfPolicy).into(NAME_OF_POLICY_FIELD),

                Enter.keyValues(policyParameters.description).into(DESCRIPTION_FIELD),

                WaitUntil.the(RESOURCE_TYPE_DROPDOWN_BUTTON,isClickable()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(RESOURCE_TYPE_DROPDOWN_BUTTON),

                WaitUntil.the(RESOURCE_TYPE_DROPDOWN_OPTION.of(policyParameters.selectResource.resourceType),isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(RESOURCE_TYPE_DROPDOWN_OPTION.of(policyParameters.selectResource.resourceType)),

                WaitUntil.the(RESOURCE_NAME_DROPDOWN_BUTTON,isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(RESOURCE_NAME_DROPDOWN_BUTTON),

                WaitUntil.the(RESOURCE_NAME_DROPDOWN_OPTION.of(policyParameters.selectResource.resourceName),isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(RESOURCE_NAME_DROPDOWN_OPTION.of(policyParameters.selectResource.resourceName)),

                WaitUntil.the(ADD_POLICY_BUTTON,isPresent()).forNoMoreThan(THIRTY).seconds(),
                Click.on(ADD_POLICY_BUTTON),

                WaitUntil.the(ADD_POLICY_FORM_TITLE, isNotPresent()).forNoMoreThan(FIFTEEN).seconds()
        );
    }
}
