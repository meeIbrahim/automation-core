package desktop;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class AgentConfig {
    private static EnvironmentVariables environmentVariables;
    private static String environment;
    private static final String CONFIG = "desktopAgent";
    private static final String AppPath = "env.application.binary"; /// env is replaced with actual environment at runtime
    private static final String ServerPath = "env.application.go";
    private static final String ProcessPath = "env.application.process";
    private static final String ConfigPath = "env.application.config";
    private static final String DriversPath = "driver";

    /////// CACHED ENVIRONMENT VARIABLES ///////
    private static EnvironmentVariables Variables(){
        if (environmentVariables == null){environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();}
        return environmentVariables;
    }

    /////// GET ENVIRONMENT VARIABLES ///////
    public static String AppPath(){
        EnvironmentVariables envVars = Variables();
        return envVars.getProperty(Path(AppPath));
    }

    public static Boolean isDebug(){
        EnvironmentVariables envVars = Variables();
        try {
            return Boolean.parseBoolean(envVars.getProperty("debug"));
        } catch (Exception ignored){
            return false;
        }
    }
    public static String DriverPath(){
        EnvironmentVariables envVars = Variables();
        return envVars.getProperty(Path(DriversPath));
    }

    public static String GoServerPath(){
        EnvironmentVariables envVars =Variables();
        return envVars.getProperty(Path(ServerPath));
    }

    public static String ConfigPath(){
        EnvironmentVariables enVars = Variables();
        return enVars.getProperty(Path(ConfigPath));
    }

    public static String ProcessName(){
        EnvironmentVariables enVars = Variables();
        return enVars.getProperty(Path(ProcessPath));
    }

    ////// HELPER FUNCTIONS TO OUTPUT PATH BASED ON PLATFORM
    private static String Path(String... variables){
        String PATH = Join(CONFIG,OS.utils.getOsName());
        for (String item : variables){
            PATH = PATH + "." + withEnvironment(item);
        }
        return  PATH;
    }
    private static String Join(String... toJoin){
        String JOINED = "";
        for (String item : toJoin){
            if (JOINED.equals("")){JOINED=item;continue;}
            JOINED = JOINED + "." +item;
        }
        return JOINED;
    }

    /// Replace env with actual environment value
    private static String withEnvironment(String propertyKey){
        if (propertyKey.contains("env.")){
            String env = ENV();
            if (environmentVariables.getPropertiesWithPrefix(env).isEmpty()){
                propertyKey = propertyKey.replace("env.","default.");
            }
            else {
                propertyKey = propertyKey.replace("env.", (env + "."));
            }
        }
        return propertyKey;
    }
    private static String ENV(){
        if (environment==null){environment = Variables().getProperty("environment");}
        return environment;
    }
}
