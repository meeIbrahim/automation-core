package com.ztna.hooks;


import io.cucumber.java.Before;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;



public class BrowserHooks {
    ////////  Cucumber Hook to create User Profile
    //////// These Profiles are used to open Desktop Agent App

    private static final String BrowserDirectory = "src/test/external_resources/browser_profiles/";
    private static final String DataDir = "target/browser_profile";

    private static final String WebDriver = "webdriver.driver";
    private static final String ChromeSwitch = "chrome.switches";
    private static final String FireFoxSwitch = "firefox.switches";



    @Before(order = 1)
    public void BrowserHooks(){
        createBrowserProfile();
    }

    public static void createBrowserProfile(){
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String browser = environmentVariables.getProperty(WebDriver);
        OS.utils.createDirectory(DataDir);
        if (browser.equals("chrome")){
            OS.utils.copyFolder(BrowserDirectory + "chrome" ,DataDir);
        }
        else if(browser.equals("firefox")){
            OS.utils.copyFolder(BrowserDirectory + "firefox" ,DataDir);
        }
    }


}
