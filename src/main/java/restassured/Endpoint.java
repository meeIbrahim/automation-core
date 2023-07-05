package restassured;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
/*
            CLASS FOR ENDPOINTS
            PROVIDES HELPFUL FUNCTIONALITIES AROUND ENDPOINTS AND THEIR USAGE
*/

public class Endpoint {
    public String URL;
    private  static String auth = "{base}/auth/api/v1";
    private  static String resource = "{base}/resource/api/v1";


    public Endpoint(String endpoint){
        String placeholder = "\\{base\\}";
        String url = "https://dev.extremecloudztna.com";
        try {
            EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
            url = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
        }
        catch (Exception e) {System.out.println("Base URL Not Found! Reverting to dev Env");}
        this.URL = endpoint.replaceAll(placeholder,url);
    }

    /// Method to Duplicate EndPoint
    public Endpoint(Endpoint old){
        this.URL = old.URL;
    }

    /// Method to add parameters to an Endpoint
    public Endpoint param(String... parameters){
        Endpoint toReturn = new Endpoint(this);
        for (String param: parameters) {
            if (!param.isEmpty()) {  // Need to Revisit this - use continue instead
                if (toReturn.URL.contains("?")) {
                    toReturn.URL = toReturn.URL + "&" + param;
                } else {
                    toReturn.URL = toReturn.URL + "?" + param;
                }
            }
        }
        return toReturn;
    }
    /// Builder Method for Endpoint Object
    public static Endpoint url(String URL){
        String auth_placeholder = "\\{auth\\}";
        String resource_placeholder = "\\{resource\\}";
        URL = URL.replaceAll(auth_placeholder,auth);
        URL = URL.replaceAll(resource_placeholder,resource);
        return new Endpoint(URL);
    }

    /// Method to add values to variables to Endpoint
    /// Variables should be inside curly brackets with index number e.g example.com/id={0}/{1}
    public Endpoint with(String... parameters){
        Endpoint toReturn = new Endpoint(this);
        int variableIndex = 0;
        for(String parameter : parameters) {
            String variablePlaceholder = "\\{" + variableIndex++ + "\\}";
            toReturn.URL = toReturn.URL.replaceAll(variablePlaceholder, parameter);
        }
        return toReturn;

    }

}
