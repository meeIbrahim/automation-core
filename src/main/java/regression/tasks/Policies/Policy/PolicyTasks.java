package regression.tasks.Policies.Policy;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import ui.com.ztna.web.policy.update_policy.model.Update_Policy_Parameters;


import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.common.user_interface.CommonUI.CANCEL_BUTTON;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.AddPolicyFormUI.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;

public class PolicyTasks {

    public static Performable updatePolicyUsing(Update_Policy_Parameters parameters){

        return Task.where("Update Policy",actor -> {

            actor.attemptsTo(
                    choosePopOverMenu(POLICY_ROW_ACTION_ICON_BUTTON.of(parameters.nameOfPolicy),"Update Policy", "Update Policy")
            );

            if(!parameters.editedPolicyName.isEmpty()){
                actor.attemptsTo(
                        Clear.field(NAME_OF_POLICY_FIELD),
                        fillInput(NAME_OF_POLICY_FIELD,parameters.editedPolicyName)
                );
            }

            if(!parameters.newDescription.isEmpty()){
                actor.attemptsTo(
                        Clear.field(DESCRIPTION_FIELD),
                        fillInput(DESCRIPTION_FIELD,parameters.newDescription)
                );
            }

            actor.attemptsTo(
                    clickButton(UPDATE_POLICY_MODAL_BUTTON),
                    waitNotPresenceOf(UPDATE_POLICY_MODAL_BUTTON)
            );

        });

    }

    public static Performable triesToUpdatePolicyUsing(Update_Policy_Parameters parameters){

        return Task.where("Update Policy",actor -> {

            actor.attemptsTo(
                    choosePopOverMenu(POLICY_ROW_ACTION_ICON_BUTTON.of(parameters.nameOfPolicy),"Update Policy", "Update Policy")
            );

            if(!parameters.editedPolicyName.isEmpty()){
                actor.attemptsTo(
                        Clear.field(NAME_OF_POLICY_FIELD),
                        fillInput(NAME_OF_POLICY_FIELD,parameters.editedPolicyName)
                );
            }

            if(!parameters.newDescription.isEmpty()){
                actor.attemptsTo(
                        Clear.field(DESCRIPTION_FIELD),
                        fillInput(DESCRIPTION_FIELD,parameters.newDescription)
                );
            }

            actor.attemptsTo(
                    clickButton(UPDATE_POLICY_MODAL_BUTTON)
            );

        });

    }

    public static Performable verifyPolicyIsUpdated(Update_Policy_Parameters parameters){

        return Task.where("Verify policy is updated", actor -> {

            if(!parameters.editedPolicyName.isEmpty()){
                actor.attemptsTo(
                        waitPresenceOf(POLICY_ROW.of(parameters.editedPolicyName)),
                        choosePopOverMenu(POLICY_ROW_ACTION_ICON_BUTTON.of(parameters.editedPolicyName),"Update Policy", "Update Policy"),
                        waitPresenceOf(NAME_OF_POLICY_FIELD_VALUE.of(parameters.editedPolicyName))
                );
                if (!parameters.newDescription.isEmpty()){
                    actor.attemptsTo(waitPresenceOf(DESCRIPTION_FIELD_VALUE.of(parameters.newDescription)));
                }actor.attemptsTo(
                        clickButton(CANCEL_BUTTON),
                        waitNotPresenceOf(CANCEL_BUTTON),
                        waitPresenceOf(POLICY_ROW.of(parameters.editedPolicyName))

                );
            }

            if (parameters.editedPolicyName.isEmpty()) {
                if (!parameters.newDescription.isEmpty()) {
                    actor.attemptsTo(
                            waitPresenceOf(POLICY_ROW.of(parameters.nameOfPolicy)),
                            choosePopOverMenu(POLICY_ROW_ACTION_ICON_BUTTON.of(parameters.nameOfPolicy), "Update Policy", "Update Policy"),
                            waitPresenceOf(DESCRIPTION_FIELD_VALUE.of(parameters.newDescription)),
                            clickButton(CANCEL_BUTTON),
                            waitNotPresenceOf(CANCEL_BUTTON),
                            waitPresenceOf(POLICY_ROW.of(parameters.nameOfPolicy))

                    );
                }
            }

        });
    }



}
