package ui.com.ztna.web.pages.cloud.relays;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class RelayUI implements ResourceUI {
    public static final Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Relay");
    public static final Target MANAGE_HA_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Manage HA");
    public static final Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("3").called("Delete Relay");
    public static Target ACTIVE_RELAY = Target.the("Active Relay")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][1]//*[contains(text(),\"ago\")]");
    public static Target RELAY_STATUS = Target.the("Relay Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][2]//*[text()=\"{0}\"]");
    public static Target RELAY_STATUS_IN_PROGRESS = RELAY_STATUS.of("In Progress").called("In Progress Relay Status");
    public static Target RELAY_STATUS_DELETION = RELAY_STATUS.of("Remove In Progress").called("Delete Status of Relay");
    public static Target RELAY_STATUS_DEPLOYMENT_FAILED = RELAY_STATUS.of("Deployment Failure").called("Deployment Failed Status of Relay");
    public static Target RELAY_STATUS_ENABLED = RELAY_STATUS.of("Enabled").called("Enabled Relay Status");
    public static Target ADD_RELAY_BUTTON = Target.the("Add Relay Button")
            .locatedBy("(//button[@id])[1]");
    public static Target HA_ENABLED = Target.the("HA Enabled label")
                .locatedBy("//*[@*[contains(., \"Cell\")]][@id][4]//*[text()=\"Enabled\"]");
    public static final Target FORCE_REMOVE_RELAY_NODE_OPTION = Target.the("Force Remove Relay Node option")
            .locatedBy("//div[@role='button'][not (contains(@class, 'disabled'))]//p[text()='Force Remove Relay Node']");


    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return ACTIVE_RELAY;
    }

    @Override
    public Target enabledStatus() {
        return RELAY_STATUS_ENABLED;
    }

    @Override
    public Target deletionStatus() {
        return RELAY_STATUS_DELETION;
    }
}
