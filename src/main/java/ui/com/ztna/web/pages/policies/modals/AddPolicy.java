package ui.com.ztna.web.pages.policies.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.policies.PoliciesUI;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddPolicy {
    public static Target NAME = Input.BY_ORDER.of("1").called("Policy Name");
    public static Target DESCRIPTION = Input.BY_ORDER.of("2").called("Policy Description");
    public static Target ATTACHMENT_TYPE = Dropdown.BY_ORDER.of("1").called("Attachment Type");
    public static Target ATTACHMENT = Dropdown.BY_ORDER.of("2").called("Attachment");
    public static Performable open(){
        return Task.where("{0} opens Add Policy Modal",actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(PoliciesUI.ADD_POLICY_BUTTON),
                    Click.on(PoliciesUI.ADD_POLICY_BUTTON),
                    Modal.waitUntilOpen()
            );
        });
    }

    public static Performable fill(String Name, String Description, String Attachment, Boolean forProject){
        return Task.where("{0} fills policy info",actor ->
        {
            actor.attemptsTo(
                    Input.of(NAME).with(Name),
                    Input.of(DESCRIPTION).with(Description),
                    Check.whether(forProject).andIfSo(
                            Dropdown.of(ATTACHMENT_TYPE).select(2)
                    ).otherwise(
                            Dropdown.of(ATTACHMENT_TYPE).select(1)
                    ),
                    Dropdown.of(ATTACHMENT).select(Attachment)
            );
        });
    }
}
