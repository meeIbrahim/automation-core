package com.ztna.steps.web.xiq;

import io.cucumber.java.en.Given;
import serenity.custom.tasks.Navigate;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Login_Steps {
    @Given("user is on XIQ Login Page")
    public void UserIsOnXIQLogin()
    {
        theActorInTheSpotlight().attemptsTo(Navigate.to("xiq.login"));
    }


}
