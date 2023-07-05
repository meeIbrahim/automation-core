package ui.com.ztna.web.pages.users.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.InputForm;
import ui.com.ztna.web.pages.users.UsersUI;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class InviteUsers {
    public static Target EMAILS = InputForm.BY_ORDER.of("1").called("Emails");
    public static Target ACCESS_GROUP = Dropdown.BY_ORDER.of("1").called("Access Group");
    public static Performable open(){
        return Task.where("Open Invite Users Modal",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(UsersUI.INVITE_USERS_BUTTON),
                    Click.on(UsersUI.INVITE_USERS_BUTTON),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fill(String Group,String... email){
        return Task.where("Fills user data",actor -> {
            actor.attemptsTo(
                    InputForm.of(EMAILS).with(email),
                    Check.whether(Group.equals("")).otherwise(
                    Dropdown.of(ACCESS_GROUP).select(Group))
            );
        });
    }


}
