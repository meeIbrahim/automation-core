package ui.com.ztna.web.pages.connectors;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class ConnectorUI implements ResourceUI {

    public static final Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Connector");
    public static final Target MANAGE_ACCESS_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Manage Access");
    public static final Target MANAGE_STATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("3").called("Manage State");
    public static final Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("4").called("Remove Connector");
    public static Target ACTIVE_CONNECTOR = Target.the("Active Connector")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][1]//*[contains(text(),\"ago\")]");
    public static Target CONNECTOR_STATUS = Target.the("Connector Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][2]//*[text()=\"{0}\"]");
    public static Target CONNECTOR_STATUS_ENABLED = CONNECTOR_STATUS.of("Enabled").called("Enabled Connector");
    public static Target CONNECTOR_STATUS_DISABLED = CONNECTOR_STATUS.of("Disabled").called("Disabled Connector");
    public static Target CONNECTOR_STATUS_REVOKED = CONNECTOR_STATUS.of("Access Token Revoked").called("Revoked Connector");
    public static Target CONNECTOR_STATUS_IN_PROGRESS = CONNECTOR_STATUS.of("In-Progress").called("In Progress Connector");
    public static Target CONNECTOR_STATUS_DEPLOYMENT_FAILED = CONNECTOR_STATUS.of("Deployment Failure").called("Deployment failed Connector");
    public static Target CONNECTOR_STATUS_DELETE_IN_PROGRESS = CONNECTOR_STATUS.of("In-Progress").called("Delete In Progress Connector");
    public static Target ADD_CONNECTOR_BUTTON = Target.the("Add Connector Button")
            .locatedBy("(//button[@id])[1]");
    public static Target ADD_PRIVATE_HOSTED = Action_Menu.ACTION_BUTTON.of("1").called("Private Hosted");
    public static Target ADD_CLOUD_HOSTED = Action_Menu.ACTION_BUTTON.of("2").called("Cloud Hosted");

    public static Target SITE_ICON = Target.the("Site Icon").locatedBy("./self::*[count(.//*[name()=\"path\"])={0}][count(.//*[name()=\"defs\"])={1}]");

    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return ACTIVE_CONNECTOR;
    }

    @Override
    public Target enabledStatus() {
        return CONNECTOR_STATUS_ENABLED;
    }

    @Override
    public Target deletionStatus() {
        return CONNECTOR_STATUS.of("In-Progress").called("Deletion In Progress");
    }
}
