package com.ztna.steps.web.signup;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.parameters.UserParameters;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SignUp_Steps {
    public static String workspaceId = null;

    @Given("User {reference} is invited")
    public void userAIsInvited(String reference) {
        UserParameters userParameters = Data.localUser().any();
        theActorInTheSpotlight().attemptsTo(
                ZTA.users().invite(userParameters)
        );
        RM.user().cache().cache(reference,userParameters);
        theActorInTheSpotlight().remember(reference,userParameters);
    }

    @When("User {reference} registers")
    public void userARegisters(String reference) {
        UserParameters userParameters = theActorInTheSpotlight().recall(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.users().registerLocal(userParameters)
        );
    }

    @Then("User {reference} is added with Active status")
    public void userAIsAddedWithActiveStatus(String reference) throws cachedNotFound {
        ZTAParameters user = RM.user().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.users().table().contains(user.name)).isTrue()
        );
    }
}