package ui.com.ztna.web.pages.cloud.relays.modals;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Input;

public class UpdateRelay {
    public static Target MTU = Input.BY_ORDER.of("1").called("Set MTU");
}
