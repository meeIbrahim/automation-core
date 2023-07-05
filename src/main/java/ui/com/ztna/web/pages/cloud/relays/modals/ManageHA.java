package ui.com.ztna.web.pages.cloud.relays.modals;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Toggle;

public class ManageHA {
    public static Target HA = Toggle.BY_ORDER.of("1").called("HA Toggle");
}
