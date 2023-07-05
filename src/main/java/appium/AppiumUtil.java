package appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;
import java.io.IOException;

public class AppiumUtil {

    private static AppiumDriverLocalService appiumDriverLocalService;

    public static void startAppium() {
        initializeDriver();
        appiumDriverLocalService.start();
    }

    public static void stopAppium() {
        appiumDriverLocalService.stop();
    }

    private static void initializeDriver() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String nodePath = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("node.path");
        String appiumPath = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("appium.path");

        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingDriverExecutable(new File(nodePath));
        serviceBuilder.withAppiumJS(new File(appiumPath));
        serviceBuilder.usingPort(4724);
//        serviceBuilder.usingAnyFreePort();
//        serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        appiumDriverLocalService = AppiumDriverLocalService.buildService(serviceBuilder);
    }
}
