package ui.com.ztna.web.policy.manage_policy_activation.Interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;

import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.common.user_interface.CommonUI.PAGE_LABEL;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;

public class ActivationInteractions {


    public static Performable openManageRulesPage(String policyName){
        return Task.where("Open manage rules",actor -> {

            actor.attemptsTo(
                    openActionIconMenu(POLICY_ROW_OPTION_BUTTON.of(policyName)),
                    chosePopOverButton("Manage Rules", PAGE_LABEL.of("Manage Rules"))
            );

        });
    }

    public static Performable verifyStatus(String status){

        return Task.where("Verify status is {0}",actor -> {

            int count = 0;
            while (Presence.of(TABLE_ROWS.of(String.valueOf(count))).asABoolean().answeredBy(actor)){
                actor.attemptsTo(waitPresenceOf(TABLE_ROWS_STATUS.of(String.valueOf(count),status)));
                count++;
            }


        });
    }







}
