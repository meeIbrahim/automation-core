package ui.com.ztna.web.pages.sites.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.parameters.AwsSiteParameters;

import static ui.com.ztna.web.pages.sites.modals.AddSite.*;

public class AddAwsSite {

    public static Target AWS_ACCOUNT_ID = Dropdown.BY_ORDER.of("3").called("AWS Account ID");
    public static Target AWS_REGION_DROPDOWN = Dropdown.BY_ORDER.of("4").called("AWS Region");
    public static Target AWS_VPC_DROPDOWN = Dropdown.BY_ORDER.of("5").of("AWS VPCs");

    public static Performable fill(AwsSiteParameters parameters){
        return Task.where("{0} fills Add AWS site Modal with data", actor -> {
            actor.attemptsTo(
                    Input.of(NAME_INPUT).with(parameters.name),
                    Dropdown.of(HOSTED_PROVIDER_DROPDOWN).select(parameters.hostingProvider),
                    Check.whether(parameters.relays.isEmpty()).andIfSo(
                            Dropdown.of(CLOUD_RELAY_DROPDOWN).select(1)
                    ).otherwise(
                            MultiSelect.of(CLOUD_RELAY_DROPDOWN).select(parameters.relays.toArray(String[]::new))
                    ),
                    Dropdown.of(AWS_ACCOUNT_ID).select(parameters.awsAccountId));
            if(parameters.region == null || parameters.region.isEmpty()){
                actor.attemptsTo(Dropdown.of(AWS_REGION_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(AWS_REGION_DROPDOWN).select(parameters.region));
            }
            if(parameters.vpc == null || parameters.vpc.isEmpty() | parameters.vpc.size()==0){
                actor.attemptsTo(Dropdown.of(AWS_VPC_DROPDOWN).select(1));
            }
            else{
                actor.attemptsTo(MultiSelect.of(AWS_VPC_DROPDOWN).select(parameters.vpc.toArray(String[]::new)));
            }
            });

    }
}
