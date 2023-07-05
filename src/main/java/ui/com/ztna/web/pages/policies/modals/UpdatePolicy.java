package ui.com.ztna.web.pages.policies.modals;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Input;

public class UpdatePolicy {
    public static Target NAME = Input.BY_ORDER.of("1").called("Policy Name");
    public static Target DESCRIPTION = Input.BY_ORDER.of("2").called("Policy Description");
}
