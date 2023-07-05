package com.ztna.acceptancetests.cleanup;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import static com.ztna.constants.Static.ZTNA_GLUE;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/Regression/Environment_Cleanup/Environment_Cleanup.feature", glue = ZTNA_GLUE)
public class EnvironmentCleanupSuite {
}
