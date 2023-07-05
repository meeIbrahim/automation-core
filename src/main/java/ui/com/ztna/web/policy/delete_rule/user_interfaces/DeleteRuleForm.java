package ui.com.ztna.web.policy.delete_rule.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class DeleteRuleForm {

    public static final Target REMOVE_RULE_BUTTON = Target.the("remove rule button")
            .locatedBy("//button[.//span[.='Remove Rule']]");

    public static final Target DELETE_RULE_FORM_TITLE = Target.the("delete rule form title")
            .locatedBy("//h2[.='Remove Rule']");
}
