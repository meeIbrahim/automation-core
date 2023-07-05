package ui.com.ztna.web.pages.integrations;

import net.serenitybdd.screenplay.targets.Target;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class IntegrationsUI implements ResourceUI {



    public static Target UPDATE_INTEGRATION = Action_Menu.ACTION_BUTTON.of("1").called("Update Integration");
    public static Target REMOVE_INTEGRATION = Action_Menu.ACTION_BUTTON.of("2").called("Remove Integration");
    public static Target INTEGRATION_TAB_BUTTON_SELECTED = Target.the("Integration tab button selected")
            .locatedBy("//button[@role='tab'][@aria-selected='true'][.//span[contains(text(), '{0}')]]");

    public static final Target ADD_INTEGRATION_BUTTON = Target.the("Add Integration")
            .locatedBy("(//button[@id])[1]");
    public static final Target INTEGRATION_TAB_BUTTON = Target.the("Integration tab button")
            .locatedBy("(//div[@role='tablist']/button)[{0}]");
    public static Target INTEGRATION_STATUS = Target.the("Integration Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][3]//*[text()=\"{0}\"]");
    public static Target INTEGRATION_ACTIVE_STATUS = INTEGRATION_STATUS.of("Linked").called("Active Integration Status");
    public static Target INTEGRATION_DELETION_STATUS = INTEGRATION_STATUS.of("Integration Deletion-in-progress").called("Deletion Integration Status");
    public static Target ENABLED_INTEGRATION = Target.the("Active Integration")
            .locatedBy("//*[@id=\"RowCell-name-0\"]/div/div");
    public static final Target INTEGRATION_ALREADY_EXITS = Target.the("Integration already exits failure message")
            .locatedBy("//div[@data-testid='error-container']");


    @NotNull
    @Override
    public Target removeActionMenu() {
        return REMOVE_INTEGRATION;
    }

    @NotNull
    @Override
    public Target activeStatus() {
        return INTEGRATION_ACTIVE_STATUS;
    }

    @NotNull
    @Override
    public Target enabledStatus() {
        return ENABLED_INTEGRATION;
    }

    @NotNull
    @Override
    public Target deletionStatus() {
        return INTEGRATION_DELETION_STATUS;
    }
}
