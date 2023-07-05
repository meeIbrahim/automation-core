package ui.com.ztna.web.pages.integrations;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import resource.cached.ZTAParameters;

import serenity.custom.questions.URL;
import ui.com.ztna.web.common.page.*;
import ui.com.ztna.web.common.filters.BulkFilters;
import ui.com.ztna.web.common.filters.Filter;
import ui.com.ztna.web.common.filters.Query;
import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.tables.Cards;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.common.tables.action.menu.ActionMenu;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.parameters.CloudIntegrationParameters;

import java.util.List;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;


public abstract class Integrations<T extends CloudIntegrationParameters> extends ResourcePage<T> {
    Target tab = Target.the("Tab").locatedBy("//*[@role=\"tablist\"]//*[@role=\"tab\"][{0}]");
    ResourceUI ui = new IntegrationsUI();
    ResourceQuestions questions = new Questions();

    protected abstract Integer tab();


    protected Question<Boolean> isSelected(){
        return Question.about("Is Tab Selected").answeredBy(actor -> {
            WebElementFacade elementFacade = tab.of(String.valueOf(tab())).resolveFor(actor);
            return elementFacade.getAttribute("aria-selected").contains("true");
        });
    }

    @Override
    public Performable openPage() {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseURL = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
        String url = baseURL + url();
        return Task.where("Open page "+url, actor -> {
            Boolean isOnPage = actor.asksFor(URL.current()).equals(url);
            actor.attemptsTo(
                    Check.whether(isOnPage).otherwise(
                                    Open.url(url),
                                    PageLoad.waitFor(),
                                    waitNotPresenceOf(LOADER)
                            )
                            .andIfSo(
                                    Check.whether(Modal.isOpen()).andIfSo(
                                            Modal.close()
                                    ),
                                    Check.whether(ActionMenu.isOpen()).andIfSo(
                                            Click.on(Action_Menu.POPOVER_OVERLAY)
                                    )
                            ),
                    Check.whether(isSelected()).otherwise(
                            Click.on(tab.of(String.valueOf(tab()))),
                            Ensure.thatTheAnswerTo(isSelected()).isTrue()
                    )
            );

        });
    }

    @Override
    public ResourceUI ui() {
        return ui;
    }

    @Override
    public ResourceQuestions question() {
        return questions;
    }

    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Integration Free").answeredBy(
                actor -> {
                    Boolean isActive = actor.asksFor(isActive(row));
                    Boolean hasNoAssociations = !actor.asksFor(hasAssociations(row));
                    return (isActive && hasNoAssociations);
                }
        );
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Integration Active").answeredBy(actor -> {
            return (ui().activeStatus().resolveFor(actor).isPresent() &&
                    ui().enabledStatus().resolveFor(actor).isPresent());
        });
    }

    @Override
    public String url() {
        return "/app/public-cloud";
    }


}
