package com.ztna.steps.web.xiq_integration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.xiq_integration.tasks.ClickOnIntegrationButton;
import ui.com.ztna.web.xiq_integration.tasks.SeeThat;


import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static ui.com.ztna.web.xiq_integration.user_interface.ExtremeCloudXIQUI.EXTREME_CLOUD_IQ_TEXT;
import static ui.com.ztna.web.xiq_integration.user_interface.ExtremeCloudXIQUI.INTEGRATIONS_ICON;

public class AddXIQProfiles {
    @Given("user is on ExtremeCloud IQ page")
    public void onExtremeCloudPage(){
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(INTEGRATIONS_ICON,isClickable()).forNoMoreThan(15).seconds(),
                Click.on(INTEGRATIONS_ICON),
                WaitUntil.the(EXTREME_CLOUD_IQ_TEXT, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(EXTREME_CLOUD_IQ_TEXT)
        );
    }
    @When("user click on Integrate button")
    public void clickOnIntegrateButton(){
        theActorInTheSpotlight().attemptsTo(
                ClickOnIntegrationButton.integrateTheProfiles()
        );

    }
    @Then("the XIQ profiles should be integrated")
    public void verifyIntegratedProfiles(){
        theActorInTheSpotlight().attemptsTo(
                SeeThat.ExtremeCloudXIQIntegrationVerification()
        );

    }

}
