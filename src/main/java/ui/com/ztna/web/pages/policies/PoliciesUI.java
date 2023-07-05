package ui.com.ztna.web.pages.policies;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class PoliciesUI implements ResourceUI {
    public static Target ADD_POLICY_BUTTON = Target.the("Add Policy Button")
            .locatedBy("//main/*/div[2]//button[@name]");
    public static Target POLICY_STATUS = Target.the("Policy Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][2]//*[text()=\"{0}\"]");

    public static Target POLICY_STATUS_ACTIVE = POLICY_STATUS.of("Active").called("Active Policy Status");

    public static Target ACTIVE_POLICY = Target.the("Active Policy")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][4]/*[not(descendant::*[name()=\"svg\"])]");
    /////////////////////                   TABLE                                           //////////////////////////
    public static Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Policy");
    public static Target MANAGE_ACTIVATION_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Manage Activation");
    public static Target MANAGE_RULES_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("3").called("Manage Rules");
    public static Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("4").called("Remove Policy");

    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return POLICY_STATUS_ACTIVE;
    }

    @Override
    public Target enabledStatus() {
        return POLICY_STATUS_ACTIVE;
    }

    @Override
    public Target deletionStatus() {
        return POLICY_STATUS.of("In Progress");
    }
}
