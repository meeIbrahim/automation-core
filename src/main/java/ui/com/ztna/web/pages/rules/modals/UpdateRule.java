package ui.com.ztna.web.pages.rules.modals;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;

public class UpdateRule {

    public static Target NAME = Input.BY_ORDER.of("1").called("Rule Name");

    public static Target LOCATION_BASED_ACCESS_COUNTRIES = MultiSelect.BY_ORDER.of("3").called("Location based access countries");



}
