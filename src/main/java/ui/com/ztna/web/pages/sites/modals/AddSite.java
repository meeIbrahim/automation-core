package ui.com.ztna.web.pages.sites.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.parameters.OnPremSiteParameters;
import ui.com.ztna.web.parameters.SiteParameters;



import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.pages.sites.SiteUi.ADD_SITE_BUTTON;


public class AddSite {

    public static Performable open(){
        return Task.where("{0} opens Add Site modal", actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ADD_SITE_BUTTON),
                    Click.on(ADD_SITE_BUTTON),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable fill(OnPremSiteParameters parameters){
        return Task.where("{0} fills #MODAL with data",actor -> {
            actor.attemptsTo(
                    Input.of(NAME_INPUT).with(parameters.name),
                    Dropdown.of(HOSTED_PROVIDER_DROPDOWN).select(parameters.hostingProvider),
                    MultiSelect.of(CLOUD_RELAY_DROPDOWN).select(parameters.relays.toArray(String[]::new)),
                    Input.of(ON_PREM_REGION_INPUT).with(parameters.region)
            );
        });
    }


    public static Target NAME_INPUT = Input.BY_ORDER.of("1").called("Name of Site");

    public static Target HOSTED_PROVIDER_DROPDOWN = Dropdown.BY_ORDER.of("1").called("Hosting Provider");

    public static Target CLOUD_RELAY_DROPDOWN = Dropdown.BY_ORDER.of("2").called("Associate Cloud Hosted Relay");

    public static Target ON_PREM_REGION_INPUT = Input.BY_ORDER.of("2").called("On Prem Region");

}
