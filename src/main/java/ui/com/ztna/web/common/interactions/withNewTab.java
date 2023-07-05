package ui.com.ztna.web.common.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;


public class withNewTab implements Performable {

    Performable toDo;
    Performable openTab;


    public withNewTab (Performable openTab,Performable toDo){
        this.openTab = openTab;
        this.toDo = toDo;
    }
    private withNewTab(Performable openTab){
        this.openTab = openTab;
        this.toDo = null;
    }

    public static withNewTab open(Performable openTab){
        return new withNewTab(openTab);
    }

    public withNewTab perform(Performable toDo){
        return Instrumented.instanceOf(withNewTab.class).withProperties(this.openTab,toDo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        String originalHandle = driver.getWindowHandle();
        HashSet<String> beforeHandles = (HashSet<String>) driver.getWindowHandles();
        actor.attemptsTo(
                openTab
        );
        HashSet<String> afterHandles = (HashSet<String>) driver.getWindowHandles();
        afterHandles.removeAll(beforeHandles);
        driver.switchTo().window(afterHandles.iterator().next());
        if (toDo != null){
            actor.attemptsTo(
                    toDo
            );
            driver.close();
            driver.switchTo().window(originalHandle);
        }
    }

}
