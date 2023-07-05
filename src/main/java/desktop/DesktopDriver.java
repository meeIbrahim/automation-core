package desktop;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class DesktopDriver {

    public static WebDriver getDriver(){
        return attachDriverToApp(AgentConfig.DriverPath(),AgentConfig.AppPath());
    }
    /// Create ChromeDriver for Desktop Agent
    /// As of 25/12/2022 :: Chromedriver version 87 is used for Agent
    private static WebDriver getDriverForOS(String DriverPath,String ApplicationPath){
        ChromeDriverService.Builder builder = new ChromeDriverService.Builder().usingDriverExecutable(new File(DriverPath)).usingAnyFreePort();
        ChromeOptions options = new ChromeOptions();
        options.setBinary(ApplicationPath);
        options.addArguments("--user-data-dir=PATH".replace("PATH",OS.utils.getAbsolutePath(AgentConfig.ConfigPath())),  /// Need to Pass Absolute path to Driver
                "--profile-directory=Default");
        return new ChromeDriver(builder.build(),options);
    }

    private static WebDriver attachDriverToApp(String DriverPath,String ApplicationPath){
        ChromeDriverService.Builder builder = new ChromeDriverService.Builder().usingDriverExecutable(new File(DriverPath));
        String Port = String.valueOf(OS.Ports.FreePort());
        /// Create new thread to run Application
        /// To avoid blocking main process
        Thread ZTA = new Thread(){
            public void run(){
                OS.localTerminal.Execute(ApplicationPath + " --remote-debugging-port=" + Port).RunCommand();
            }
        };
        ZTA.start();


        ChromeOptions options = new ChromeOptions();
        options.setBinary(ApplicationPath);
        options.setExperimentalOption("debuggerAddress","127.0.0.1:" + Port);
        return new ChromeDriver(builder.build(),options);
    }


}
