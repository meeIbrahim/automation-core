package com.ztna.steps.web.signin;

import io.cucumber.java.en.Given;
import serenity.custom.tasks.Navigate;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SignIn_Steps {

    public static String workspaceId = null;

    @Given("user is on sign in page")
    public void userIsOnSignInPage() {
//        theActorInTheSpotlight().attemptsTo(Navigate.to("login.page")); ZTA Sign In Page
        theActorInTheSpotlight().attemptsTo(Navigate.to("xiq.login")); // XIQ Sign in
    }

}
