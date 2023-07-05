package ui.com.ztna.web.pages.cloud.relays.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.Toggle;


import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.pages.cloud.relays.RelayUI.ADD_RELAY_BUTTON;

public class AddRelay {
    static String MODAL = "Add Cloud Hosted Relay";
    public static Target REGION = Dropdown.BY_ORDER.of("1").called("Region");
    public static Target MTU = Input.BY_ORDER.of("1").called("Set MTU");
    public static Target HA_TOGGLE = Toggle.BY_ORDER.of("1").called("HA Toggle");

    public static Performable open(){
        return Task.where("{0} opens #MODAL",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ADD_RELAY_BUTTON),
                    Click.on(ADD_RELAY_BUTTON),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }
    public static Performable fill(String Region,String mtu, Boolean HA){
        return Task.where("{0} fills #MODAL with data",actor -> {
            actor.attemptsTo(
                    Dropdown.of(REGION).select(Region),
                    Input.of(MTU).with(mtu),
                    Check.whether(HA).andIfSo(
                            Toggle.of(HA_TOGGLE).enable()
                    )
            );
        });
    }
    public static Performable fill(String Region,Boolean HA){
        return Task.where("{0} fills #Modal with data",actor -> {
            actor.attemptsTo(
                    Dropdown.of(REGION).select(Region),
                    Check.whether(HA).andIfSo(
                            Toggle.of(HA_TOGGLE).enable()
                    )
            );
        });
    }
    public static Performable fill(String Region){
        return Task.where("{0} fills #Modal with data",actor -> {
            actor.attemptsTo(
                    Dropdown.of(REGION).select(Region)
            );
        });
    }
}
