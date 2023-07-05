package ui.com.ztna.web.logout.interactions;

import net.serenitybdd.screenplay.Task;
import ui.com.xiq.web.login.user_interface.LoginUI;
import ui.com.ztna.web.common.Wait.Wait;

import java.time.Duration;

import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;
import static ui.com.ztna.web.common.user_interface.NavBarUI.LOGOUT_BUTTON;
import static ui.com.ztna.web.common.user_interface.NavBarUI.PROFILE_BUTTON;

public class Logout {

    public static Task logout() {
        return Task.where("{0} logouts",
                clickButton(PROFILE_BUTTON),
                clickButton(LOGOUT_BUTTON),
                Wait.forPresenceOf(LoginUI.LOGIN_BUTTON).forTime(Duration.ofSeconds(60))
        );
    }
}
