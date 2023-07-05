package com.ztna.steps.web.logout;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import ui.com.xiq.web.login.tasks.interactions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static ui.com.ztna.web.logout.interactions.Logout.logout;

public class Logout_Steps {

    @When("user tries to logout")
    public void userTriesToLogout() {
        theActorInTheSpotlight().attemptsTo(logout());
    }

    @Then("user should see that logout is successful")
    public void logoutIsSuccessful() {
        theActorInTheSpotlight().attemptsTo(
                /// Old Tenant Log out
//                waitPresenceOf(SIGN_IN_TEXT)
//                XIQ Logout
                Ensure.thatTheAnswerTo("Is User on Login Page",interactions.isOnLoginPage()).isTrue()
        );
    }
}
