package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.connectors.modals.AddCloudConnector;
import ui.com.ztna.web.parameters.AzureConnectorParameters;

public class AddAzureConnector extends AddCloudConnector {
    public static Target NAME = Input.BY_ORDER.of("1").called("VM Name");
    public static Target VM_TYPE = Dropdown.BY_ORDER.of("1").called("VM Type");

    public static Target VNET = Dropdown.BY_ORDER.of("2").called("VNET ID | Name");
    public static Target SUBNET = Dropdown.BY_ORDER.of("3").called("Subnet ID | Name");
    public static Target KEY_PAIR = Dropdown.BY_ORDER.of("4").called("Key Pair (login)");


    public static Performable fill(AzureConnectorParameters parameters){
        return Task.where("{0} configures "+MODAL+" with Data",actor -> {
            actor.attemptsTo(
                    validateSite("Azure", parameters.site, parameters.relay),
                    Input.of(NAME).with(parameters.name));
            if(parameters.vmType == null || parameters.vmType.isEmpty()){
                actor.attemptsTo( Dropdown.of(VM_TYPE).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(VM_TYPE).select(parameters.vmType));
            }

            if(parameters.vnet == null || parameters.vnet.isEmpty()){
                actor.attemptsTo( Dropdown.of(VNET).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(VNET).select(parameters.vnet));
            }

            if(parameters.subnetId == null || parameters.subnetId.isEmpty()){
                actor.attemptsTo( Dropdown.of(SUBNET).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(SUBNET).select(parameters.subnetId));
            }

            if(parameters.keyPair == null || parameters.keyPair.isEmpty()){
                actor.attemptsTo( Dropdown.of(KEY_PAIR).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(KEY_PAIR).select(parameters.keyPair));
            }

        });
    }

}
