package ui.com.xiq.web.login.tasks;

import constants.Waits;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.Wait;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.xiq.web.login.user_interface.LoginUI;

public class Login {
    public static Performable using(String Email, String Password){
        return Task.where("User tries to Sign in Using XIQ Portal", actor -> {

            actor.attemptsTo(
                    WaitUntil.the(LoginUI.LOGIN_COLUMN, WebElementStateMatchers.isVisible()).forNoMoreThan(Waits.ONE_HUNDRED_TWENTY).seconds(),
                    interactions.FillPassword(Password),
                    interactions.FillEmail(Email),
                    interactions.ClicksLoginButton()
            );
        });
    }
}
