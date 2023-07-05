package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.connectors.modals.AddCloudConnector;
import ui.com.ztna.web.parameters.AwsConnectorParameters;

public class AddAwsConnector extends AddCloudConnector {
    public static Target NAME = Input.BY_ORDER.of("1").called("Instance Name");
    public static Target INSTANCE_TYPE = Dropdown.BY_ORDER.of("1").called("Instance Type");

    public static Target VPC = Dropdown.BY_ORDER.of("2").called("VPC ID | Name");
    public static Target SUBNET = Dropdown.BY_ORDER.of("3").called("Subnet ID | Name");
    public static Target KEY_PAIR = Dropdown.BY_ORDER.of("4").called("Key Pair (login)");


    public static Performable fill(AwsConnectorParameters parameters){
        return Task.where("{0} configures "+MODAL+" with Data",actor -> {
            actor.attemptsTo(
                    validateSite("AWS", parameters.site, parameters.relay),
                    Input.of(NAME).with(parameters.name));
            if(parameters.instanceType == null || parameters.instanceType.isEmpty()){
                actor.attemptsTo(Dropdown.of(INSTANCE_TYPE).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(INSTANCE_TYPE).select(parameters.instanceType));
            }

            if(parameters.vpcId == null || parameters.vpcId.isEmpty()){
                actor.attemptsTo( Dropdown.of(VPC).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(VPC).select(parameters.vpcId));
            }

            if(parameters.subnetId == null || parameters.subnetId.isEmpty()){
                actor.attemptsTo( Dropdown.of(SUBNET).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(SUBNET).select(parameters.subnetId));
            }

            if(parameters.keyPair == null || parameters.keyPair.isEmpty()){
                actor.attemptsTo( Dropdown.of(KEY_PAIR).select(1));
            }
            else{
                actor.attemptsTo( Dropdown.of(KEY_PAIR).select(parameters.keyPair));
            }

            actor.attemptsTo(
                    Click.on(ADD_CONNECTOR_MODAL_BUTTON)
            );
        });
    }

}
