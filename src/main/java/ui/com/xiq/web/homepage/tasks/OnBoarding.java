package ui.com.xiq.web.homepage.tasks;

import constants.Waits;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.Wait;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.xiq.web.homepage.user_interfaces.homepageUI;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class OnBoarding {

    public static Performable SelectLicense(){
        return Task.where("{0} Navigates Past License Selection",actor -> {
            actor.attemptsTo(
                    Check.whether(isOnLicensePage()).andIfSo(
                            WaitUntil.the(homepageUI.NETWORK_TYPE_BOX, WebElementStateMatchers.isNotVisible()).forNoMoreThan(Waits.FIFTEEN).seconds(),
                                    Click.on(homepageUI.LICENSE_BUTTON)

                    )
            );
        });
    }

    public static  Performable SelectNetworkType(){
        return Task.where("{0} Selects Network Type on XIQ",actor -> {
            actor.attemptsTo(
                    Check.whether(isNetworkSelectionRequired()).andIfSo(
                            WaitUntil.the(homepageUI.NETWORK_TYPE_RADIO_BUTTON,WebElementStateMatchers.isVisible()).forNoMoreThan(Waits.FIFTEEN).seconds(),
                            Click.on(homepageUI.NETWORK_TYPE_RADIO_BUTTON)
                    )
            );
        });
    }


    //// Questions

    public static Question<Boolean> isOnLicensePage(){
        return Question.about("Is User on License Page").answeredBy(actor -> homepageUI.LICENSE_BOX.resolveFor(actor).isPresent());
    }

    public static Question<Boolean> isNetworkSelectionRequired(){
        return Question.about("Is Network Selection Required").answeredBy(actor -> homepageUI.NETWORK_TYPE_BOX.resolveFor(actor).isPresent());
    }
}
