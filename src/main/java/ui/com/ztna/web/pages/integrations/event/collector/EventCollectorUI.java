package ui.com.ztna.web.pages.integrations.event.collector;

import net.serenitybdd.screenplay.targets.Target;

public class EventCollectorUI {
    public static Target SPLUNK_CARD = Target.the("Splunk Card")
            .locatedBy("//div[contains(@class,\"MuiCard\")][1]");

    public static Target SPLUNK_ADD_BUTTON = Target.the("Splunk Add Button")
            .locatedBy(SPLUNK_CARD.getCssOrXPathSelector() + "//button");
    public static Target SPLUNK_EDIT_BUTTON = Target.the("Splunk Add Button")
            .locatedBy(SPLUNK_CARD.getCssOrXPathSelector() + "//button[.//*[local-name()=\"svg\"]][1]");
    public static Target SPLUNK_REMOVE_BUTTON = Target.the("Splunk Add Button")
            .locatedBy(SPLUNK_CARD.getCssOrXPathSelector() + "//button[.//*[local-name()=\"svg\"]][2]");

    public static Target SPLUNK_STATUS = Target.the("Splunk Status tag")
            .locatedBy(SPLUNK_CARD.getCssOrXPathSelector() + "//*[contains(@class,\"MuiChip\")]");
}
