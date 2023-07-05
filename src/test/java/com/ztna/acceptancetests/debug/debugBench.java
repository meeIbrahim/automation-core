package com.ztna.acceptancetests.debug;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/com/ztna/acceptancetests/debug/debug.feature", glue = "com.ztna")
public class debugBench {
}
