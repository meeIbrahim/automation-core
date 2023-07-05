package com.ztna.steps.regression.IAM.Users;

import com.ztna.steps.web.accessgroup.Create_Access_Group_Steps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.data.Data;
import ui.com.ztna.web.common.filters.Filter;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.InputForm;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.users.Filters;
import ui.com.ztna.web.pages.users.modals.InviteUsers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class UsersSteps {

    String INVITED_USERS = "invited_users";

    @And("User opens Invite User Modal")
    public void usersOpensInviteUserModal(){
        theActorInTheSpotlight().attemptsTo(
                InviteUsers.open(),
                Ensure.thatTheAnswerTo(Modal.title()).isEqualTo("Invite Users")
        );

    }



    @Then("User verifies that available access groups are shown in dropdown")
    public void userVerifiesAccessGroupInDropdown(){
        List<String> accessGroups = theActorInTheSpotlight().recall(Create_Access_Group_Steps.AVAILABLE_ACCESS_GROUPS);
        List<String> dropdownAccessGroups = Dropdown.of(InviteUsers.ACCESS_GROUP).contains().answeredBy(theActorInTheSpotlight());
        boolean flag = true;
        for (String AG : accessGroups){
            if (!dropdownAccessGroups.contains(AG)) {
                flag = false;
                break;
            }
        }
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(flag).isTrue()
        );
    }

    @When("User saves Invite User Form")
    public void userSavesForm(){
        theActorInTheSpotlight().attemptsTo(
                Modal.save()
        );
    }

    @Then("Invited Users are added with Pending state")
    public void invitedUsersAdded(){
        List<String> emails = theActorInTheSpotlight().recall(INVITED_USERS);
        theActorInTheSpotlight().attemptsTo(
                Filters.STATUS_PENDING.apply()
        );
        LinkedHashSet<Row> users = theActorInTheSpotlight().asksFor(ui.com.ztna.web.pages.ZTA.ZTA.users().table().rows());
        List<String> emailsAdded = users.stream().map(Row::name).collect(Collectors.toList());
        boolean flag = true;
        for (String email : emails){
            if (!emailsAdded.contains(email)) {
                flag = false;
                break;
            }
        }
        theActorInTheSpotlight().attemptsTo(
                Filter.clear()
        );
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(flag).isTrue()
        );
    }




    @And("User fills emails in invite users modal")
    public void userFillsEmailUserForm(){
        ArrayList<String> emails = new ArrayList<>(List.of(
                Data.generateEmail(),
                Data.generateEmail(),
                Data.generateEmail()));
        theActorInTheSpotlight().attemptsTo(
                InputForm.of(InviteUsers.EMAILS).with(emails.toArray(String[]::new))
        );
        theActorInTheSpotlight().remember(INVITED_USERS,emails);
    }

}
