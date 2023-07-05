package ui.com.ztna.web.common.page;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.conditions.Check;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;
import serenity.custom.questions.URL;
import ui.com.ztna.web.common.interactions.PageLoad;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.page.Page.LOADER;

interface PageStrategy {

    default Performable openPage(){
        return Task.where("{0} opens page "+ url(),actor -> actor.attemptsTo(
                Check.whether(isOnPage()).andIfSo(
                        resetPage()
                ).otherwise(
                        goToPageURL()
                )
        ));
    }
    Performable goToPageURL();
    Performable resetPage();
    default Question<Boolean> isOnPage(){
        return Question.about("Is on page").answeredBy(actor -> {
            EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
            String baseURL = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
            String url = baseURL + url();
            return actor.asksFor(URL.current()).equals(url);
        });
    }
    String url();
    default Performable refresh(){
        return Task.where("{0} refreshes the page", actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            driver.navigate().refresh();
            actor.attemptsTo(
                    PageLoad.waitFor(),
                    waitNotPresenceOf(LOADER)
            );
        }).withNoReporting();
    }
}
