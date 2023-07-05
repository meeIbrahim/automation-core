package regression.tasks.Policies.Rule;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.common.model.UpdateDevicePostureParameters;
import ui.com.ztna.web.common.model.UpdateLocationParameters;
import ui.com.ztna.web.common.model.UpdateNameParameters;
import ui.com.ztna.web.common.model.UpdateTimeParameters;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.pages.rules.modals.UpdateRule.LOCATION_BASED_ACCESS_COUNTRIES;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.handleTimeAccess;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.MODAL_LABEL;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.RULE_NAME_ENTER;

public class RuleTasks {

    public static Performable updateRuleLocation(UpdateLocationParameters updateLocationParameters){

        return Task.where("Update rule location",actor -> {

            if(updateLocationParameters.anyLocation.equals("yes")){
                if (Presence.of(LOCATION_BASED_ACCESS_CHECKBOX_UNCHECKED).asABoolean().answeredBy(actor)){
                    actor.attemptsTo(clickButton(LOCATION_BASED_ACCESS_CHECKBOX));
                }
            }else {

                if (Presence.of(LOCATION_BASED_ACCESS_CHECKBOX_CHECKED).asABoolean().answeredBy(actor)) {
                    actor.attemptsTo(clickButton(LOCATION_BASED_ACCESS_CHECKBOX));
                } else if (Presence.of(LOCATION_BASED_ACCESS_CHECKBOX_UNCHECKED).asABoolean().answeredBy(actor)) {
                    actor.attemptsTo(
                            removeMultipleValuesFromInput(updateLocationParameters.countriesToRemove)
                    );
                }
                actor.attemptsTo(addMultipleValuesIntoInput(COUNTRIES_INPUT, updateLocationParameters.countries));
            }

        });

    }

    public static Performable updateRuleLocation(List<String> countries){

        return Task.where("Update rule location",actor -> {


                if (Presence.of(LOCATION_BASED_ACCESS_CHECKBOX_CHECKED).asABoolean().answeredBy(actor)) {
                    actor.attemptsTo(Click.on(LOCATION_BASED_ACCESS_CHECKBOX));
                } else if (Presence.of(LOCATION_BASED_ACCESS_CHECKBOX_UNCHECKED).asABoolean().answeredBy(actor)) {
                    List<String> selectedCountries = MultiSelect.of(LOCATION_BASED_ACCESS_COUNTRIES).selectedValues().answeredBy(actor);
                    actor.attemptsTo(
                            removeMultipleValuesFromInput(selectedCountries)
                    );
                }
                actor.attemptsTo(
                        addMultipleValuesIntoInput(COUNTRIES_INPUT, countries),
                        Click.on(MODAL_LABEL));

        });

    }

    public static Performable updateRuleName(UpdateNameParameters updateNameParameters){

        return Task.where("Update name",actor -> {
            actor.attemptsTo(
                    Clear.field(RULE_NAME_ENTER),
                    fillInputField(RULE_NAME_ENTER, updateNameParameters.newName)
            );
        });

    }

    public static Performable updateRuleTime(UpdateTimeParameters updateTimeParameters){

        return Task.where("Update Time", actor -> {

            if(updateTimeParameters.anyTime.equals("yes")){
                if (Presence.of(TIME_BASED_ACCESS_CHECKBOX_UNCHECKED).asABoolean().answeredBy(actor)){
                    actor.attemptsTo(clickButton(TIME_BASED_ACCESS_CHECKBOX));
                }
            }else {
                actor.attemptsTo(handleTimeAccess(updateTimeParameters));
            }



        });
    }

    public static Performable updateRuleTime(String sth, String stm, String eth, String etm){

        return Task.where("Update Time", actor -> {


                if (Presence.of(TIME_BASED_ACCESS_CHECKBOX_CHECKED).asABoolean().answeredBy(actor)){
                    actor.attemptsTo(Click.on(TIME_BASED_ACCESS_CHECKBOX));
                }
                actor.attemptsTo(handleTimeAccess(sth,stm,eth,etm));


        });
    }


    public static Performable updateDevicePosture(UpdateDevicePostureParameters updateDevicePostureParameters){

        return Task.where("Update Time", actor -> {

            if (!updateDevicePostureParameters.devicePosture.isEmpty()){

                actor.attemptsTo(selectDropDownField(DEVICE_POSTURE_DROPDOWN, updateDevicePostureParameters.devicePosture));

            }



        });
    }



}
