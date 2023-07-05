package com.ztna.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class RemembranceHooks {
    public final static int ORDER = 2;
    public static final String SCENARIO_MEMORY_KEY = "Scenario";


    @Before(order = 3)
    public void rememberTheScenario(Scenario scenario) {
        theActorInTheSpotlight().remember(SCENARIO_MEMORY_KEY,scenario);
        System.out.println(scenario.getName()+" haroon");
    }
}
