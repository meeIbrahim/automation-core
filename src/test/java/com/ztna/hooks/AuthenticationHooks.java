package com.ztna.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static com.ztna.parameters.Parameters.actor;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;

public class AuthenticationHooks {
    public static Actor actor;


    @Before(order = 2)
    public void set_the_stage() {
        setTheStage(new OnlineCast());
        actor = actor("User");
    }
}
