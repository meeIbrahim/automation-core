package ui.com.ztna.web.xiq_integration.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.xiq_integration.user_interface.ExtremeCloudXIQUI;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public class ClickOnIntegrationButton {
    public static Task integrateTheProfiles(){
        return Task.where("{0} clicks on integration button",
                WaitUntil.the(ExtremeCloudXIQUI.INTEGRATE_EXTREME_CLOUD_IQ_Button, isClickable()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(ExtremeCloudXIQUI.INTEGRATE_EXTREME_CLOUD_IQ_Button),
                WaitUntil.the(ExtremeCloudXIQUI.XIQ_CONFIRM_BUTTON, isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                Click.on(ExtremeCloudXIQUI.XIQ_CONFIRM_BUTTON)

                );
    }
}
