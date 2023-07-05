package json.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import postgresql.parameters.PostgreSQLConnectionParameters;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Deserialize {

    public static PostgreSQLConnectionParameters authDbConnectionParameters() throws FileNotFoundException {
        Gson gson = new Gson();
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("env");
        JsonReader json;
        if (environment.equals("zta-qa")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_qa.json"));
        } else if (environment.equals("beta01")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_beta01.json"));
        } else if (environment.equals("demo")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_demo.json"));
        } else if (environment.equals("chaos")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_chaos.json"));
        } else if (environment.equals("dev")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_dev.json"));
        } else if (environment.equals("staging")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_staging.json"));
        }else if (environment.equals("feature-3")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_feature_3.json"));
        }else if (environment.equals("zta-tme")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_tme.json"));
        } else if (environment.equals("feature-2")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/auth_db/auth_db_credentials_feature_2.json"));
        } else {
            json = null;
        }
        PostgreSQLConnectionParameters parameters = gson.fromJson(json, PostgreSQLConnectionParameters.class);
        return parameters;
    }

    public static PostgreSQLConnectionParameters resourceDbConnectionParameters() throws FileNotFoundException {
        Gson gson = new Gson();
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("env");
        JsonReader json;
        if (environment.equals("zta-qa")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_qa.json"));
        } else if (environment.equals("beta01")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_beta01.json"));
        } else if (environment.equals("demo")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_demo.json"));
        } else if (environment.equals("chaos")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_chaos.json"));
        } else if (environment.equals("dev")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_dev.json"));
        } else if (environment.equals("staging")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_staging.json"));
        } else if (environment.equals("feature-3")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_feature_3.json"));
        } else if (environment.equals("zta-tme")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_tme.json"));
        } else if (environment.equals("feature-2")) {
            json = new JsonReader(new FileReader("src/test/resources/database_access_credentials/resource_db/resource_db_credentials_feature_2.json"));
        } else {
            json = null;
        }
        PostgreSQLConnectionParameters parameters = gson.fromJson(json, PostgreSQLConnectionParameters.class);
        return parameters;
    }


}
