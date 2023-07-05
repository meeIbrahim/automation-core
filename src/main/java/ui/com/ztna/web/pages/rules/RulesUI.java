package ui.com.ztna.web.pages.rules;

import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Checkbox;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class RulesUI implements ResourceUI {
    public static Target TITLE = Target.the("Rule Table")
            .locatedBy("//th//*[contains(text(),\"Rule\")]");

    public static Target ADD_RULE_BUTTON = Target.the("Add Rule Button")
            .locatedBy("//main/*/div[2]//button[@name]");
    public static Target RULE_NAME = Target.the("Rule name")
            .locatedBy("//td[@id='RowCell-name-0'][.='{0}']");
    public static Target REMOVE_RULES_CHECKBOX = Checkbox.BY_LABEL.of("Removed").called("Removed rules checkbox");

    public static Target RULE_STATUS = Target.the("Rule Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][2]//*[text()=\"{0}\"]");
    public static Target RULE_STATUS_ACTIVE = RULE_STATUS.of("Active").called("Active Rule");

    public static Target PARENT_POLICY = Target.the("Policy Name")
            .locatedBy("(//main//div[.//*[contains(text(),\"Pol\")]])[2]//div[3]");
    public static Target UPDATE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("1").called("Update Rule");
    public static Target REMOVE_ACTION_MENU = Action_Menu.ACTION_BUTTON.of("2").called("Remove Rule");

    @Override
    public Target removeActionMenu() {
        return REMOVE_ACTION_MENU;
    }

    @Override
    public Target activeStatus() {
        return RULE_STATUS_ACTIVE;
    }

    @Override
    public Target enabledStatus() {
        return RULE_STATUS_ACTIVE;
    }

    @Override
    public Target deletionStatus() {
        return ALWAYS_FALSE.called("Deletion Status of Rule");
    }
}
