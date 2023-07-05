package ui.com.ztna.web.common.filters;

import net.serenitybdd.screenplay.targets.Target;

public class FilterUI {
    public static Target BAR = Target.the("Filter Bar")
            .locatedBy("//*[@id=\"tags-filled\"]");
    public static String SELECTED_VALUE = "../following-sibling::*[1]//*[@role=\"button\"]"; /// For Relative Use
    public static String VALUE_CLEAR = ".//*[contains(@class,\"deleteIcon\")]";
    public static Target FILTERS = Target.the("All Filters")
            .locatedBy("(" + BAR.getCssOrXPathSelector()
                    + "/..//*[@role=\"button\"])[position() mod 2 = 1]");
    public static Target SELECTED_FILTER = Target.the("Selected Filter")
            .locatedBy(FILTERS.getCssOrXPathSelector() +"[.//text()" +
                    "[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),\"{0}\")]]");



    public static Target SELECTED_FILTER_VALUE = Target.the("Selected Filter Value")
            .locatedBy(SELECTED_FILTER.getCssOrXPathSelector() + "/" + SELECTED_VALUE);


    public static Target FILTER_VALUES = Target.the("All Filters")
            .locatedBy("(" + BAR.getCssOrXPathSelector()
                    + "/..//*[@role=\"button\"])[position() mod 2 = 0]");
    public static Target LIST_BOX = Target.the("List Box")
            .locatedBy("//*[contains(@class,\"listbox\")]");


    /// Targets for Filter Dropdown
    public static Target FILTER_SELECTION = Target.the("Filter Selection")     ///  INFO: Use Lower Case values when using this Target
            .locatedBy("//ul//li[.//text()" +
                    "[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),\"{0}\")]]");

    public static Target VALUE_SELECTION = Target.the("Filter Value")
            .locatedBy("//ul//li[.//*[text()=\"{0}\"]]");




}
