package ui.com.ztna.web.pages.services.tab.remote.desktop.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddRemoteDesktop {
    public static Target PROTOCOL = Dropdown.BY_ORDER.of("1").called("Protocol");
    public static Target URL = Input.BY_ORDER.of("1").called("URL");
    public static Target PORT = Input.BY_ORDER.of("2").called("Port");
    public static Performable open(){
        return Task.where("[0} opens add remote Desktop modal", actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ServicesUI.ADD_APPLICATION),
                    Click.on(ServicesUI.ADD_APPLICATION),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fillApplicationInfo(String protocol, String url, String port){
        return Task.where("{0} adds remote desktop service",actor -> {
            if (protocol.equalsIgnoreCase("RDP") | protocol.equalsIgnoreCase("VNC")){
                actor.attemptsTo(
                        Dropdown.of(PROTOCOL).select(protocol.toUpperCase())
                );
            }
            else {throw new IllegalArgumentException("Unknown remote desktop protocol: " + protocol);}
            actor.attemptsTo(
                    Input.of(PORT).with(port),
                    Input.of(URL).with(AddService.normalizeURL(url))
            );

        });
    }
}
