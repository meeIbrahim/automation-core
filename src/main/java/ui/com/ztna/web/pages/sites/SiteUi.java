package ui.com.ztna.web.pages.sites;

import net.serenitybdd.screenplay.targets.Target;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class SiteUi implements ResourceUI {

    public static final Target UPDATE_SITE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Site");
    public static final Target REMOVE_SITE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Remove Site");


    public static Target HEALTHY_SITE = Target.the("Active Site")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][3][text()>0]");

    public static Target RELAY_ATTACHED = Target.the("Site with Relay")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][3][text()>0]");

    public static Target ADD_SITE_BUTTON = Target.the("Add Site Button")
            .locatedBy("(//button[@id])[1]");

    @NotNull
    @Override
    public Target removeActionMenu() {
        return REMOVE_SITE_ACTION_MENU;
    }

    @NotNull
    @Override
    public Target activeStatus() {
        return HEALTHY_SITE;
    }

    @NotNull
    @Override
    public Target enabledStatus() {
        return RELAY_ATTACHED;
    }

    @NotNull
    @Override
    public Target deletionStatus() {
        return ALWAYS_FALSE.called("Deletion Status of Site");
    }
}
