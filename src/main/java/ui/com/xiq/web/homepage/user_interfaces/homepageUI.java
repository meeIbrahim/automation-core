package ui.com.xiq.web.homepage.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class homepageUI {
    public static final Target SIDE_BAR = Target.the("Side Bar")
            .locatedBy("//div[contains(@class,\"extreme-pilot-header\")]");

    public static final Target DOT_MENU = Target.the("Nine Dot Menu")
            .locatedBy("//div[@class=\"discovery-container\"]");


    public static final Target LICENSE_BOX = Target.the("License Selection Box")
            .locatedBy("//div[contains(@id,\"WelcomeEdition\")]");

    public static final Target LICENSE_RADIO_BUTTON = Target.the("Trial Radio Selection")
            .locatedBy("//*[./*[contains(text(),\"trial\")]]");

    public static final Target LICENSE_BUTTON = Target.the("License Get Started Button")
            .locatedBy("//body/div/div[contains(@class,\"Footer\")]/div/button[contains(text(),\"Started\")]");

    public static final Target NETWORK_TYPE_BOX = Target.the("Select Network Type Pop UP")
            .locatedBy("//div[contains(@class,\"is-open\")]");

    public static final Target NETWORK_TYPE_RADIO_BUTTON = Target.the("Network Radio Selection")
            .locatedBy("//span[contains(text(),\"locally\")]");
}
