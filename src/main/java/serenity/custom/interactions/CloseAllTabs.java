package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CloseAllTabs implements Interaction {
    public static CloseAllTabs exceptCurrentTab() {
        return instrumented(CloseAllTabs.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        String currentHandle = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        for (String tab : tabs) {
            if (!tab.equals(currentHandle)) {
                driver.switchTo().window(tab);
                driver.close();
            }
        }
        driver.switchTo().window(currentHandle);
    }
}
