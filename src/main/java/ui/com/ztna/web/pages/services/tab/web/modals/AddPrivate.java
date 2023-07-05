package ui.com.ztna.web.pages.services.tab.web.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Checkbox;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddPrivate {
    public static Target PROTOCOL = Dropdown.BY_ORDER.of("1").called("Protocol");
    public static Target URL = Input.BY_ORDER.of("1").called("Service URL");
    public static Target HIDE_URL = Checkbox.BY_ORDER.of("1").called("Hide My Url");

    public static Performable open(){
        return Task.where("{0} Opens Add Private Web App Modal",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ServicesUI.ADD_APPLICATION),
                    Click.on(ServicesUI.ADD_APPLICATION),
                    waitPresenceOf(Action_Menu.POPOVER),
                    Click.on(ServicesUI.PRIVATE_APP),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fillApplicationInfo(String Protocol,String url,Boolean HideUrl){
        return Task.where("{0} fills Application Info",actor -> {
            if(Protocol.equalsIgnoreCase("HTTPS") | Protocol.equalsIgnoreCase("HTTP")){
                actor.attemptsTo(Dropdown.of(PROTOCOL).select(Protocol.toUpperCase()));
            }
            else {throw new IllegalArgumentException("Protocol for Web App Invalid");}
            actor.attemptsTo(
                    Input.of(URL).with(AddService.normalizeURL(url,Protocol)),
                    Check.whether(HideUrl).andIfSo(
                            Checkbox.of(HIDE_URL).enable()
                    ).otherwise(
                            Checkbox.of(HIDE_URL).disable()
                    )
            );

        });
    }

}