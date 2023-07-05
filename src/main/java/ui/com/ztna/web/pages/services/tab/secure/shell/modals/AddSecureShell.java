package ui.com.ztna.web.pages.services.tab.secure.shell.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.services.ServicesUI;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddSecureShell {
    public static final Target HOSTNAME = Input.BY_ORDER.of("1").called("Hostname");
    public static final Target PORT = Input.BY_ORDER.of("2").called("Port");
    public static Performable open(){
        return Task.where("[0} opens add secure shell modal",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ServicesUI.ADD_APPLICATION),
                    Click.on(ServicesUI.ADD_APPLICATION),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fillApplicationInfo(String url,String port){
        return Task.where("{0} fills application info",actor -> {
            actor.attemptsTo(
                    Input.of(HOSTNAME).with(url),
                    Input.of(PORT).with(port)
            );
        });
    }
}
