package postgresql.databases;

import json.utils.Deserialize;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import postgresql.parameters.PostgreSQLConnectionParameters;
import postgresql.tasks.DatabaseConnection;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static postgresql.tasks.DatabaseConnection.closeDatabaseConnection;
import static postgresql.tasks.DatabaseConnection.establishDatabaseConnection;

public class ResourceDb {
    static PostgreSQLConnectionParameters parameters;

    static {
        try {
            parameters = Deserialize.resourceDbConnectionParameters();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    public static String getElement(String table, String column1, String element1, String column2){
        establishDatabaseConnection(parameters);
        String element = DatabaseConnection.database.getElementFor(table, column1, element1, column2);
        closeDatabaseConnection();
        return element;
    }


    @SneakyThrows
    public static void deleteElement(String table, String column1, String element1) {
        establishDatabaseConnection(parameters);
        DatabaseConnection.database.deleteElement(table, column1, element1);
        closeDatabaseConnection();
    }

    @SneakyThrows 
    public static String getElement(String table, String GetElementColumn, HashMap<String,String> conditions){
        establishDatabaseConnection(parameters);
        String element = DatabaseConnection.database.getElementFor(table,GetElementColumn,conditions);
        closeDatabaseConnection();
        return element;

    }

    @SneakyThrows
    public static void updateDataBaseTime(String table, String column1, String element1, String column2, String element2){
        establishDatabaseConnection(parameters);
        DatabaseConnection.database.updateDbLastStatus(table,column1,element1,column2,element2);
        closeDatabaseConnection();
    }

    @SneakyThrows
    public static Boolean elementPresent(String table, String column, String element) {
        establishDatabaseConnection(parameters);
        Boolean authToken = DatabaseConnection.database.elementPresentQuery(table, column, element);
        closeDatabaseConnection();
        return authToken;
    }

    @SneakyThrows
    public static ResultSet rowContainingElement(String table, String column, String element) {

        String workspaceId = AuthDb.workspaceId;
        theActorInTheSpotlight().remember("WorkSpaceID",workspaceId);
        System.out.println("WorkSpaceID : "+workspaceId);

        ResultSet result;
        if (workspaceId!=null) {
           result = ResourceDb.rowContainingElementWithWorkspaceId(table, column, element, workspaceId);
        }
        else {
            establishDatabaseConnection(parameters);
            result = DatabaseConnection.database.rowContainingElementWithoutWorkspaceQueryResourceDb(table, column, element);
            closeDatabaseConnection();
        }
        return result;
    }

    @SneakyThrows
    public static ResultSet rowContainingElementWithoutWorkspaceId(String table, String column, String element) {
        establishDatabaseConnection(parameters);
        ResultSet result = DatabaseConnection.database.rowContainingElementWithoutWorkspaceQueryResourceDb(table, column, element);
        closeDatabaseConnection();
        return result;
    }

    @SneakyThrows
    private static ResultSet rowContainingElementWithWorkspaceId(String table, String column, String element, String workspaceId) {
        establishDatabaseConnection(parameters);
        ResultSet result = DatabaseConnection.database.rowContainingElementQueryResourceDb(table, column, element, workspaceId);
        closeDatabaseConnection();
        return result;
    }

    @SneakyThrows
    public static Boolean elementPresent(String table, String column1, String element1, String column2, String element2){
        establishDatabaseConnection(parameters);
        Boolean elementPresent = DatabaseConnection.database.isElementPresent(table,column1,element1,column2,element2);
        closeDatabaseConnection();
        return elementPresent;
    }
    @SneakyThrows
    public static void updateDataBase(String table, String column1, String element1, String column2, String element2){
        establishDatabaseConnection(parameters);
        DatabaseConnection.database.updateDb(table,column1,element1,column2,element2);
        closeDatabaseConnection();
    }
}
