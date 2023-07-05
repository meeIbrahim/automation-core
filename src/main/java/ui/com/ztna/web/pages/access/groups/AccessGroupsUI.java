package ui.com.ztna.web.pages.access.groups;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class AccessGroupsUI implements ResourceUI {

    public static final Target ADD_ACCESS_GROUP = Target.the("Add Access Group")
            .locatedBy("(//button[@id])[1]");
    public static Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Access Group");
    public static Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Remove Access Group");

    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return ResourceUI.ALWAYS_TRUE.called("Active Status of Access Group");
    }

    @Override
    public Target enabledStatus() {
        return ResourceUI.ALWAYS_TRUE.called("Enabled Status of Access Group");
    }

    @Override
    public Target deletionStatus() {
        return ResourceUI.ALWAYS_FALSE.called("Deletion Status of Access Group");
    }
}
