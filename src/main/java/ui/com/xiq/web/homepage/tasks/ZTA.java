package ui.com.xiq.web.homepage.tasks;

import constants.Waits;
import lombok.SneakyThrows;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;
import ui.com.xiq.web.homepage.user_interfaces.DiscoveryUI;
import ui.com.xiq.web.login.tasks.Login;

import java.net.URL;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class ZTA {
    private static final String Entry_URL = "/zta";

    @SneakyThrows
    public static Task NavigateTo(){
        return Task.where("{0} Navigates to ZTA",
                interactions.OpenNineDotMenu(), //// FE Bugged, have to go to URL Directly
                waitNotPresenceOf(DiscoveryUI.ZTA_ENTERING_PAGE)

                );
    }
    public static Task LoginWithXIQ(String Email, String Password){
        return Task.where("{0} Logs in to ZTA Through XIQ Using EMAIL".replace("EMAIL",Email),
                Login.using(Email,Password),
                GoToZTA());
    }

    public static Performable GoToZTA(){
        return Task.where("{0} Enters ZTA", actor -> {
                    EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
                    String URL = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("webdriver.base.url") + Entry_URL;
                    actor.attemptsTo(Open.url(URL),
                            waitNotPresenceOf(DiscoveryUI.ZTA_ENTERING_PAGE));
                }
                );
    }
}
