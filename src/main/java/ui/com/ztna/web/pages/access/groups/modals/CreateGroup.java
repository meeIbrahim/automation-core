package ui.com.ztna.web.pages.access.groups.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.pages.access.groups.AccessGroupsUI;

public class CreateGroup {
    public static Target NAME = Input.BY_ORDER.of("1").called("Access Group Name");
    public static Target DESCRIPTION = Input.BY_ORDER.of("2").called("Access Group Description");
    public static Target USERS = MultiSelect.BY_ORDER.of("1").called("Access Group Users");

    public static Performable open(){
        return Task.where("{0} opens Create Access Group Modal",actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(AccessGroupsUI.ADD_ACCESS_GROUP),
                    Click.on(AccessGroupsUI.ADD_ACCESS_GROUP),
                    Modal.waitUntilOpen()
            );
        });
    }

    public static Performable fill(String Name, String Description, String... Users){
        return Task.where("{0} fills info",actor -> {

            actor.attemptsTo(
                    Input.of(NAME).with(Name),
                    Input.of(DESCRIPTION).with(Description),
                    MultiSelect.of(USERS).select(Users)
            );
        });
    }

}
