package ui.com.ztna.web.common.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class PageLoad implements Performable {
    @Override
    public <T extends Actor> void performAs(T actor) {
        ExpectedCondition<Boolean> javascriptDone = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").equals("complete");
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            }
        };
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), 5);
        wait.until(javascriptDone);
    }
    public static PageLoad waitFor(){
        return Instrumented.instanceOf(PageLoad.class).newInstance();
    }
}
