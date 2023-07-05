package ui.com.xiq.web.login.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import ui.com.xiq.web.login.user_interface.LoginUI;
import ui.com.ztna.web.common.input.Input;

public class interactions {
    public static Performable FillEmail(String Email){
        return Task.where("{0} Enters EMAIL in Email Field".replace("EMAIL",Email),actor ->
                actor.attemptsTo(
                        Input.of(LoginUI.EMAIL_FORM).with(Email)));
    }
    public static Performable FillPassword(String Password){
        return Task.where("{0} Enters PASSWORD in Email Field".replace("PASSWORD",Password),actor ->
                actor.attemptsTo(
                        Input.of(LoginUI.PASSWORD_FORM).with(Password)
                     ));
    }

    public static Performable ClicksLoginButton(){
        return Task.where("{0} Clicks Login",actor ->
                actor.attemptsTo(Click.on(LoginUI.LOGIN_BUTTON)));
    }

    /// Questions

    public static Question<Boolean> isOnLoginPage(){
        return  Question.about("Is on Login Page").answeredBy(actor -> LoginUI.LOGIN_COLUMN.resolveFor(actor).isPresent());
    }
}
