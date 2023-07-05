package ui.com.ztna.web.policy.delete_policy.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class DeletePolicyForm {

    public static final Target REMOVE_POLICY_MODAL_BUTTON = Target.the("remove modal policy button")
            .locatedBy("//button[.//span[.='Remove Policy']][@data-testid='modal-saveButton']");

    public static final Target CONFIRM_BUTTON = Target.the("confirm button")
            .locatedBy("//button[.//span[.='Confirm']]");

    public static final Target REMOVE_POLICY_FORM_TITLE = Target.the("remove policy form title")
            .locatedBy("//h2[.='Remove Policy']");

    public static final Target POLICY_NAME_INPUT_FIELD = Target.the("policy name input field")
            .locatedBy("//input[@name='policyName']");



}
