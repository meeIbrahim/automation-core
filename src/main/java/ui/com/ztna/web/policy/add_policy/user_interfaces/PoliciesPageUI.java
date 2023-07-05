package ui.com.ztna.web.policy.add_policy.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class PoliciesPageUI {

    public static final Target ADD_POLICY_BUTTON = Target.the("add policy button")
            .locatedBy("//button[.//span[.='Add Policy']]");
}
