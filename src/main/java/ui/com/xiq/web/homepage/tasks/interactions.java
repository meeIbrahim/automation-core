package ui.com.xiq.web.homepage.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import ui.com.xiq.web.homepage.user_interfaces.DiscoveryUI;

import static ui.com.xiq.web.homepage.tasks.ZTA.GoToZTA;

public class interactions {
    private static final String Discovery_Apps = "/discovery-apps/";
    public static Performable OpenNineDotMenu(){
        return Task.where("{0} Opens Nine Dot Menu",actor -> {
            actor.attemptsTo(
                    GoToZTA()
            );

        });
    }


    /// Questions
    public static Question<Boolean> isPopUpVisible(){
        return  Question.about("Is Default App popup visible").answeredBy(actor -> DiscoveryUI.APP_POPUP.resolveFor(actor).isPresent());
    }
}
