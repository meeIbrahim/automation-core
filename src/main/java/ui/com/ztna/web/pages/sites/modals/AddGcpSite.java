package ui.com.ztna.web.pages.sites.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.pages.sites.modals.AddSite;
import ui.com.ztna.web.parameters.GcpSiteParameters;

import static ui.com.ztna.web.pages.sites.modals.AddSite.CLOUD_RELAY_DROPDOWN;

public class AddGcpSite {

    public static Target PROJECT_ID_DROPDOWN = Dropdown.BY_ORDER.of("3").of("Project ID");

    public static Target GCP_VPCS_DROPDOWN = Dropdown.BY_ORDER.of("4").of("GCP VPCs");

    public static Performable fill(GcpSiteParameters parameters){
        return Task.where("{0} fills #MODAL with data", actor -> {
            actor.attemptsTo(
                    Input.of(AddSite.NAME_INPUT).with(parameters.name),
                    Dropdown.of(AddSite.HOSTED_PROVIDER_DROPDOWN).select(parameters.hostingProvider));
            if(parameters.relays == null || parameters.relays.isEmpty()){
                actor.attemptsTo(Dropdown.of(CLOUD_RELAY_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo( MultiSelect.of(CLOUD_RELAY_DROPDOWN).select(parameters.relays.toArray(String[]::new)));
            }
            if(parameters.projectId == null || parameters.projectId.isEmpty()){
                actor.attemptsTo(Dropdown.of(PROJECT_ID_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(PROJECT_ID_DROPDOWN).select(parameters.projectId));
            }
            if(parameters.vpc == null || parameters.vpc.isEmpty()){
                actor.attemptsTo(Dropdown.of(GCP_VPCS_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo( MultiSelect.of(GCP_VPCS_DROPDOWN).select(parameters.vpc.toArray(String[]::new)));
            }

        });
    }
}
