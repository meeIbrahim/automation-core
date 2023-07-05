package com.ztna.acceptancetests.common;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import static com.ztna.constants.FilePath.ENVIRONMENT_CLEANUP_SUITE_FILE_PATH;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = ENVIRONMENT_CLEANUP_SUITE_FILE_PATH, glue = "com.ztna")
public class EnvironmentCleanUpTestRunner {
}
