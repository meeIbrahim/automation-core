package com.ztna.acceptancetests.common;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import static com.ztna.constants.FilePath.ENVIRONMENT_CLEANUP_SUITE_FILE_PATH;
import static com.ztna.constants.Static.ZTNA_GLUE;
import static com.ztna.constants.Tags.*;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = ENVIRONMENT_CLEANUP_SUITE_FILE_PATH, glue = ZTNA_GLUE, tags = CLEANUP)
public class EnvironmentCleanUpTestRunnerWithoutSignIn {
}
