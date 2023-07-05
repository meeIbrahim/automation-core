package desktop;

import files.utils.FileUtils;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.apache.maven.shared.utils.Os;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DesktopAgent {
    private static Actor AgentUser = null;
    public static final String ActorName = "Agent User";
    public static final String BrowserActor = "User";
    private static WebDriver Driver = null;

    private static Boolean Installed = null;

    /// Cached variable to hold Desktop Actor
    public static Actor User(){
        if (AgentUser==null){CreateAgent();}
        return AgentUser;
    }
    public static void Close(){
        OS.utils.killProcessNamed(AgentConfig.ProcessName());
        OS.utils.deleteDirectory(AgentConfig.ConfigPath());
    }

    public static boolean IsInstalled(){
        if (Installed==null){Installed = FileUtils.Exists(AgentConfig.AppPath());}
        return Installed;
    }
    public static boolean Installed(Boolean Status){
        Installed = Status;
        return Installed;
    }

    //// Turn Browser actor into Actor in the Spotlight
    public static void returnControl(){
        if (Objects.equals(theActorInTheSpotlight().getName(), ActorName)){theActorCalled(BrowserActor).entersTheScene();}
    }

    /// Create Actor for Desktop and name existing Actor used for Browser
    //// Electron opens with two "Windows". Second window is the GUI of Agent
    private static void CreateAgent(){
        theActorInTheSpotlight().assignName(BrowserActor); /// Assigning Name to Original Actor as a safe measure
        Driver = DesktopDriver.getDriver();
        AgentUser = new Actor(ActorName).whoCan(BrowseTheWeb.with(Driver));
        ArrayList<String> Handles = new ArrayList<>(Driver.getWindowHandles());
        Driver.switchTo().window(Handles.get(AgentConfig.isDebug() ? 2 : 1)); ///// For Debug Build, third window is the GUI
        // Adding Shutdown Hook to close child processes of Agent and clean config folder
        Runtime.getRuntime().addShutdownHook(new Thread((new Runnable() {
            @Override
            public void run() {
                System.out.println("Killing Desktop Agent Child-Processes and Cleaning Data Directory");
                Close();
            }
        }),"Shutdown.thread"));
    }
    /// Return Webdriver
    public static WebDriver Driver(){
        return Driver;
    }

}
