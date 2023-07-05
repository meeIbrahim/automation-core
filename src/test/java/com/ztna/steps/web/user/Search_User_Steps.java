package com.ztna.steps.web.user;

import io.cucumber.java.en.Given;
import ui.com.ztna.web.pages.ZTA.ZTA;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Search_User_Steps {


    @Given("User is on users page")
    public void OpenUserPage() {
        theActorInTheSpotlight().attemptsTo(
                ZTA.users().openPage()
        );
    }

}