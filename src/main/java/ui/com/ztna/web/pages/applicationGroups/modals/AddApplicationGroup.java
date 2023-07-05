package ui.com.ztna.web.pages.applicationGroups.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;

import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.pages.sites.SiteUi.ADD_SITE_BUTTON;

public class AddApplicationGroup {


    public static Performable open(){
        return Task.where("{0} opens Add Application Group modal", actor -> {
            actor.attemptsTo(
                    clickButton(ADD_SITE_BUTTON),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fill(ApplicationGroupParameters parameters){
        return Task.where("{0} fills #MODAL with data",actor -> {
            actor.attemptsTo(
                    Input.of(NAME_INPUT).with(parameters.name));
            if (!parameters.description.isEmpty()) {
                actor.attemptsTo(
                        Input.of(DESCRIPTION_INPUT).with(parameters.description));
            }
            if(!parameters.services.isEmpty()){
            actor.attemptsTo(
                    MultiSelect.of(SERVICES_MULTISELECT).select(parameters.services.toArray(String[]::new))
            );
            }
        });
    }

    public static Target NAME_INPUT = Input.BY_ORDER.of("1").called("Application group name");

    public static Target DESCRIPTION_INPUT = Input.BY_ORDER.of("2").called("Application group description");

    public static Target SERVICES_MULTISELECT = Dropdown.BY_ORDER.of("1").called("Services multiselect");

}
