package ui.com.ztna.web.pages.sites.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.pages.sites.modals.AddSite;
import ui.com.ztna.web.parameters.AzureSiteParameters;

import static ui.com.ztna.web.pages.sites.modals.AddSite.CLOUD_RELAY_DROPDOWN;

public class AddAzureSite {

    public static Target AZURE_REGION_DROPDOWN = Dropdown.BY_ORDER.of("5").called("Azure Region");
    public static Target AZURE_SUBSCRIPTION_ID_DROPDOWN = Dropdown.BY_ORDER.of("3").of("Azure Subscription ID");
    public static Target RESOURCE_GROUP_DROPDOWN = Dropdown.BY_ORDER.of("4").of("Resource Group");
    public static Target VNETS_DROPDOWN = Dropdown.BY_ORDER.of("6").of("Azure VNETs");

    public static Performable fill(AzureSiteParameters parameters){
        return Task.where("{0} fills azure Site MODAL with data", actor -> {
            actor.attemptsTo(
                    Input.of(AddSite.NAME_INPUT).with(parameters.name),
                    Dropdown.of(AddSite.HOSTED_PROVIDER_DROPDOWN).select(parameters.hostingProvider),
                    Check.whether(parameters.relays.isEmpty()).andIfSo(
                            Dropdown.of(CLOUD_RELAY_DROPDOWN).select(1)
                    ).otherwise(
                            MultiSelect.of(CLOUD_RELAY_DROPDOWN).select(parameters.relays.toArray(String[]::new))
                    ),
                    Dropdown.of(AZURE_SUBSCRIPTION_ID_DROPDOWN).select(parameters.azureSubscriptionId));
            if(parameters.resourceGroup == null || parameters.resourceGroup.isEmpty()){
                actor.attemptsTo(Dropdown.of(RESOURCE_GROUP_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(RESOURCE_GROUP_DROPDOWN).select(parameters.resourceGroup));
            }
            if(parameters.region == null || parameters.region.isEmpty()){
                actor.attemptsTo(Dropdown.of(AZURE_REGION_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(AZURE_REGION_DROPDOWN).select(parameters.region));
            }
            if(parameters.vnet == null ||parameters.vnet.isEmpty()){
                actor.attemptsTo( Dropdown.of(VNETS_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo(MultiSelect.of(VNETS_DROPDOWN).select(parameters.vnet.toArray(String[]::new)));
            }

        });
    }
}
