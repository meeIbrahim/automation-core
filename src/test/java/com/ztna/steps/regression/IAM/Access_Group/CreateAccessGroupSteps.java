package com.ztna.steps.regression.IAM.Access_Group;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.data.Data;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.access.groups.modals.CreateGroup;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CreateAccessGroupSteps {

    String ACCESS_GROUP_NAME = "access_group_name";


    @When("User opens Create Access Group Modal")
    public void userOpensCreateAccessGroupModal() {
        theActorInTheSpotlight().attemptsTo(
                CreateGroup.open()
        );
        String title = theActorInTheSpotlight().asksFor(Modal.title());
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(title.toLowerCase().contains("create access group"))
                        .isTrue()
        );
    }

    @And("User fills Access Group details")
    public void userFillsAGDetails(){
        String name = Data.generateName();
        theActorInTheSpotlight().attemptsTo(
                Input.of(CreateGroup.NAME).with(name),
                Input.of(CreateGroup.DESCRIPTION).with(Data.generateDescription()),
                Modal.save()
        );
        theActorInTheSpotlight().remember(ACCESS_GROUP_NAME,name);
    }
    @Given("User is on Access Group Page")
    public void userOpensAGPage(){
        theActorInTheSpotlight().attemptsTo(
                ZTA.accessGroups().openPage()
        );
    }


    @Then("Access Group is Created")
    public void accessGroupIsCreated() {
        String name = theActorInTheSpotlight().recall(ACCESS_GROUP_NAME);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        ZTA.accessGroups().table().contains(name)
                ).isTrue()
        );
    }
}
