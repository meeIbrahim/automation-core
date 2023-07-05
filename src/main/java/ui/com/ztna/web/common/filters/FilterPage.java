package ui.com.ztna.web.common.filters;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.conditions.Check;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;
import serenity.custom.questions.URL;
import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.page.Page;

import java.util.List;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;

public interface FilterPage extends Page {

    @Override
    default Performable openPage() {
        return Task.where("{0} opens page "+ url(),actor -> {
                    EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
                    String baseURL = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
                    String url = baseURL + url();
                    List<Query> filters;
                    BulkFilters allFilters = new BulkFilters();
                    if (actor.asksFor(URL.current()).contains(url)) {
                        filters = Filter.applied().answeredBy(actor);
                        allFilters = Filter.using(filters.toArray(Query[]::new));   /// If on same page, save filters to apply later
                    }
                    actor.attemptsTo(
                            Check.whether(isOnPage()).andIfSo(
                                    resetPage()
                            ).otherwise(
                                    goToPageURL(),
                                    allFilters.applyAll()
                            )
                    );
                }
        );
    }

    @Override
    default Performable refresh() {
        return Task.where("{0} refreshes the page", actor -> {
            List<Query> filters = Filter.applied().answeredBy(actor);
            BulkFilters allFilters = Filter.using(filters.toArray(Query[]::new));
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            driver.navigate().refresh();
            actor.attemptsTo(
                    PageLoad.waitFor(),
                    waitNotPresenceOf(LOADER),
                    allFilters.applyAll()
            );
        }).withNoReporting();
    }

    default Performable filterPage(BulkFilters bulkFilters) {
        return Task.where("{0} filter page",actor -> actor.attemptsTo(
                openPage(),
                bulkFilters.applyAll()
        ));
    }


    default Performable clearFilters(){
        return Task.where("{0} clear filters on page",actor -> {
            if (!Filter.applied().answeredBy(actor).isEmpty()){
                actor.attemptsTo(
                        Filter.clear()
                );
            }
        });
    }
}
