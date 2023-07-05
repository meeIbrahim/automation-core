package ui.com.ztna.web.pages.rules.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.rules.RulesUI;

import java.util.Objects;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddRule {
    public static Target NAME = Input.BY_ORDER.of("1").called("Rule Name");
    public static Target ATTACHMENT_TYPE = Dropdown.BY_ORDER.of("1").called("Attachment Type");
    public static Target ATTACHMENT = Dropdown.BY_ORDER.of("2").called("Attachment");
    public static Performable open(){
        return Task.where("{0} opens Add Rule Modal",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(RulesUI.ADD_RULE_BUTTON),
                    Click.on(RulesUI.ADD_RULE_BUTTON),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fillGeneralInfo(String Name, String Attachment, Boolean forAccessGroup){
        return Task.where("{0} fills basic Rule info",actor -> {
                actor.attemptsTo(
                        Input.of(NAME).with(Name),
                        Check.whether(forAccessGroup).andIfSo(
                                Dropdown.of(ATTACHMENT_TYPE).select(2)
                        ).otherwise(
                                Dropdown.of(ATTACHMENT_TYPE).select(1)
                        ),
                        Check.whether(Objects.equals(Attachment, ""))
                                .andIfSo(Dropdown.of(ATTACHMENT).select(1))
                                .otherwise(Dropdown.of(ATTACHMENT).select(Attachment))

                );
        });
    }
}
