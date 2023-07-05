package ui.com.ztna.web.common.page;

import net.serenitybdd.screenplay.targets.Target;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public interface ResourceUI {

    Target ASSOCIATIONS = Target.the("Association Links")
            .locatedBy("//div[@role='dialog']//p/*[@href]");
    Target ASSOCIATION = Target.the("Association Link")
            .locatedBy("//div[@role='dialog']//p/*[@href][../text()[contains(.,\"{0}\")]]");

    Target ALWAYS_FALSE = Target.the("Always False").locatedBy("//text()[.=\"this.locator.always.return.false\"]");
    Target ALWAYS_TRUE = Target.the("Always True").locatedBy("//*");
    Target FORCE_REMOVE_BUTTON = Action_Menu.ACTION_BUTTON_LAST.called("Force Remove Action Button");
    Target FORCE_REMOVE_ENABLED = Target.the("Force Remove Action Button Enabled")
            .locatedBy(Action_Menu.ACTION_BUTTON_LAST.getCssOrXPathSelector() + "[not(contains(@class, 'disabled'))]");
    Target FORCE_REMOVE_DISABLED = Target.the("Force Remove Action Button Enabled")
            .locatedBy(Action_Menu.ACTION_BUTTON_LAST.getCssOrXPathSelector() + "[(contains(@class, 'disabled'))]");
    @NotNull
    Target removeActionMenu();
    @NotNull
    Target activeStatus();
    @NotNull
    Target enabledStatus();
    @NotNull
    Target deletionStatus();

}
