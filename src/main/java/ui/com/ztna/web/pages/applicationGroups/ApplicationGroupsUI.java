package ui.com.ztna.web.pages.applicationGroups;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class ApplicationGroupsUI implements ResourceUI {

    public static final Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Application Group");
    public static final Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Remove Application Group");
    public static Target ADD_APPLICATION_GROUP_BUTTON = Target.the("Add Application Group Button")
            .locatedBy("(//button[@id])[1]");


    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return ResourceUI.ALWAYS_TRUE.called("Active Status of Project");
    }

    @Override
    public Target enabledStatus() {
        return ResourceUI.ALWAYS_TRUE.called("Enabled Status of Project");
    }

    @Override
    public Target deletionStatus() {
        return ResourceUI.ALWAYS_FALSE.called("Deletion Status of Project");
    }
}
