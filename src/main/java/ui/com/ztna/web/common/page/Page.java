package ui.com.ztna.web.common.page;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.tables.action.menu.ActionMenu;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;



import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;

public interface Page extends PageStrategy {

    Target LOADER = Target.the("Loading Icon")
            .locatedBy("//*[contains(@data-testid,\"loader\")]");
    Target PAGE_TITLE = Target.the("Page title")
                    .locatedBy("");

    @Override
    default Performable goToPageURL(){
        return Task.where("{0} Opens Page using URL",actor -> {
            EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
            String baseURL = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
            String url = baseURL + url();
            actor.attemptsTo(
                    Open.url(url),
                    PageLoad.waitFor(),
                    waitNotPresenceOf(LOADER)
            );
        });
    }

    @Override
    default Performable resetPage(){
        return Task.where("{0} resets page",actor -> actor.attemptsTo(
                Check.whether(Modal.isOpen()).andIfSo(
                        Modal.close()
                ),
                Check.whether(ActionMenu.isOpen()).andIfSo(
                        Click.on(Action_Menu.POPOVER_OVERLAY)
                )
        ));
    }

}
