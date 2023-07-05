package com.ztna.acceptancetests.debug;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import ui.com.ztna.web.common.tables.common.Pagination;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;


public class debugStep {

    Pagination pagination;
    private static final String CHROMEDRIVER = "src/test/resources/webdriver/linux/chromedriver";
    private static final String FIREFOX_DRIVER = "src/test/resources/webdriver/linux/geckodriver";
    public static Actor AgentUser;
    @SneakyThrows
    @Given("test bench is attached to {word}")
    public void setUp(String browser) {
        WebDriver driver = attachToChrome("8555");
        OnStage.setTheStage(Cast.whereEveryoneCan(BrowseTheWeb.with(driver)));
        AgentUser = OnStage.theActorCalled("User");
        ArrayList<String> Handles = new ArrayList<>(driver.getWindowHandles());
        int handle = 0;
        while(!Objects.equals(driver.getTitle(), "ExtremeCloud ZTA")){
            driver.switchTo().window(Handles.get(handle));
            handle = handle + 1;
        }
    }

    public static WebDriver attachToChrome(String Port) throws IOException {
        ChromeDriverService.Builder builder = new ChromeDriverService.Builder().usingDriverExecutable(new File(Paths.get(CHROMEDRIVER).toRealPath().toString()));
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:" + Port);
        return new ChromeDriver(builder.build(),options);
    }

    @Then("test step is run")
    @SneakyThrows
    public void testStep() {

        

    }
}
