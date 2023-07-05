package ui.com.ztna.web.xiq_integration.tasks;

import net.serenitybdd.screenplay.AnonymousPerformableFunction;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.xiq_integration.user_interface.ExtremeCloudXIQUI.*;

public class SeeThat {
    public static AnonymousPerformableFunction ExtremeCloudXIQIntegrationVerification(){
        return Task.where("{0} sees that network service is added", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(INTEGRATION_SUCCESS_MESSAGE,
                            isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                    WaitUntil.the(SYNC_BUTTON,
                            isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                    WaitUntil.the(PROFILE_ID_COLUMN,
                            isPresent()).forNoMoreThan(FIFTEEN).seconds(),
                    WaitUntil.the(PROFILE_NAME_COLUMN,
                            isPresent()).forNoMoreThan(FIFTEEN).seconds()
            );
        });
    }
}
