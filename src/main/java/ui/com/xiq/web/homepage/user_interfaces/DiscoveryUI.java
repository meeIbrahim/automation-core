package ui.com.xiq.web.homepage.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class DiscoveryUI {
    public static final Target ACCOUNT_MANAGEMENT = Target.the("Account Management Button")
            .locatedBy("//div[./div[contains(text(),\"ExtremeCloud Account Management\")]]/div[1]");
    public static final Target APP_POPUP = Target.the("Default App Popup")
            .locatedBy("//div[contains(@class,\"default-app-popup\")][./div]");

    public static final Target APP_POPUP_BUTTON = Target.the("Got it Button on Popup")
            .locatedBy("//div[contains(@class,\"default-app-popup\")]//button");

    public static final Target DOT_MENU_CONTENT = Target.the("Application List")
            .locatedBy("//div[contains(@class,\"catalog-content\")][//div[contains(@class,\"my-apps\")]]");

    public static final Target DOT_MENU = Target.the("Dot Menu on Top Right")
            .locatedBy("//button[contains(@id,\"myAppsId\")]");

    public static final Target ZTA_APP_BUTTON = Target.the("ZTA App in Dot Menu")
            .locatedBy("//div/div[.=\"ExtremeCloud ZTA\"]/div[@id]");

    public static final Target ZTA_ENTERING_PAGE = Target.the("Entering ZTA Page")
            .locatedBy("//div/*[contains(text(),\"Entering\")]");
}
