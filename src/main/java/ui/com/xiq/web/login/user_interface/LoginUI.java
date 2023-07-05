package ui.com.xiq.web.login.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class LoginUI {

    public static final Target LOGIN_BUTTON = Target.the("XIQ Login Button")
            .locatedBy("//div[@class=\"login-form\"]//*[contains(@class,\"button\")]");
    public static final Target EMAIL_FORM = Target.the("XIQ Login Button")
            .locatedBy("//div[@class=\"login-form\"]//*[contains(@class,\"email-text\")]");
    public static final Target PASSWORD_FORM = Target.the("XIQ Login Button")
            .locatedBy("//div[@class=\"login-form\"]//*[contains(@class,\"password-text\")]");

    public static final Target LOGIN_COLUMN = Target.the("Login Form")
            .locatedBy("//div[@class=\"login-form\"]");
}
