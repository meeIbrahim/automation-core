package appium;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.IOException;

public class EmulatorUtil {
    private static Process process;

    public static void startEmulator() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String emulator = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("emulator");
        try {
            process = Runtime.getRuntime().exec(System.getenv("EMULATOR_PATH") + " " + "-avd " + emulator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopEmulator() {
//        process.destroyForcibly();
        process.destroy();
    }
}
