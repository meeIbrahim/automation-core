package ui.com.ztna.web.pages.services;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class ServicesUI implements ResourceUI {
    public static Target ACTIVE_SERVICE = Target.the("Active Service")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][1]//*[contains(text(),\"ago\")]");
    public static Target SERVICE_STATUS = Target.the("Service Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][2]//*[text()=\"{0}\"]");
    public static Target DISABLED_STATUS = SERVICE_STATUS.of("Inactive").called("Service Disabled Status");
    public static Target IN_PROGRESS_STATUS = SERVICE_STATUS.of("In Progress").called("In Progress Service");

    public static final Target ADD_APPLICATION = Target.the("Add Application")
            .locatedBy("(//button[@id])[1]");

    public static final Target  PRIVATE_APP = Action_Menu.ACTION_BUTTON.of("1").called("Private App");
    public static final Target  CLOUD_APP = Action_Menu.ACTION_BUTTON.of("2").called("Cloud App");

    public static final Target UPDATE_APP_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Application");
    public static final Target MANAGE_STATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Manage State");
    public static final Target REMOVE_APPLICATION_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("3").called("Remove Application");

    @Override
    public Target removeActionMenu() {
        return REMOVE_APPLICATION_ACTION_MENU;
    }

    @Override
    public Target enabledStatus() {
        return SERVICE_STATUS.of("Active").called("Enabled Service");
    }

    @Override
    public Target activeStatus() {
        return ACTIVE_SERVICE;
    }

    @Override
    public Target deletionStatus() {
        return IN_PROGRESS_STATUS;
    }
}
