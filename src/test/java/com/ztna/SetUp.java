package com.ztna;

import api.zta.Request;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.questions.Presence;
import resource.data.Data;
import resource.manager.RM;
import serenity.custom.tasks.Navigate;
import ui.com.xiq.web.homepage.tasks.ZTA;
import ui.com.xiq.web.login.tasks.Login;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static postgresql.databases.AuthDb.setWorkspaceId;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.common.user_interface.NavBarUI.PROFILE_BUTTON;
import static ui.com.ztna.web.signin.user_interface.UsersGridUI.ONBOARDING_MODAL_MAYBE_LATER_BUTTON;

public class SetUp {

    @Given("{word} called {string} is available")
    public void ResourceAvailable(String Resource, String Reference){

    }

    @Given("User is logged in as tenant")
    public void UserIsLoggedIn(){
        if (PROFILE_BUTTON.resolveAllFor(theActorInTheSpotlight()).isEmpty()){
            theActorInTheSpotlight().attemptsTo(
                    Navigate.to("xiq.login"),
                    Login.using(Data.parameters().tenant.email,Data.parameters().tenant.password),
                    ZTA.GoToZTA(),
                    waitPresenceOf(PROFILE_BUTTON)
            );
            String homeOwnerID = theActorInTheSpotlight().asksFor(Request.homeOwnerID());
            setWorkspaceId(homeOwnerID);
            if(Presence.of(ONBOARDING_MODAL_MAYBE_LATER_BUTTON).asABoolean().answeredBy(theActorInTheSpotlight())) theActorInTheSpotlight().attemptsTo(clickButton(ONBOARDING_MODAL_MAYBE_LATER_BUTTON));

        }

    }

    @Given("User is logged in as tenant {reference}")
    public void UserLogIn(String reference){
        theActorInTheSpotlight().attemptsTo(
                Navigate.to("xiq.login"));

            switch(reference.toLowerCase()){
                case "a":
                    theActorInTheSpotlight().attemptsTo( Login.using(Data.parameters().tenant.email,Data.parameters().tenant.password));
                    break;
                case "b":
                    theActorInTheSpotlight().attemptsTo( Login.using(Data.parameters().tenantTwo.email,Data.parameters().tenantTwo.password));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        theActorInTheSpotlight().attemptsTo(
                ZTA.GoToZTA(),
                waitPresenceOf(PROFILE_BUTTON)
        );
        String homeOwnerID = theActorInTheSpotlight().asksFor(Request.homeOwnerID());
        setWorkspaceId(homeOwnerID);
        if(Presence.of(ONBOARDING_MODAL_MAYBE_LATER_BUTTON).asABoolean().answeredBy(theActorInTheSpotlight())) theActorInTheSpotlight().attemptsTo(clickButton(ONBOARDING_MODAL_MAYBE_LATER_BUTTON));

    }

}
