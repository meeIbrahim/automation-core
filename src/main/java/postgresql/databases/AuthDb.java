package postgresql.databases;

import com.jcraft.jsch.JSchException;
import json.utils.Deserialize;
import lombok.SneakyThrows;
import postgresql.parameters.PostgreSQLConnectionParameters;
import postgresql.tasks.DatabaseConnection;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static postgresql.tasks.DatabaseConnection.closeDatabaseConnection;
import static postgresql.tasks.DatabaseConnection.establishDatabaseConnection;

public class AuthDb {
    static PostgreSQLConnectionParameters parameters;

    public static String workspaceId;


    static {
        try {
            parameters = Deserialize.authDbConnectionParameters();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getAuthToken(String email) {
        establishDatabaseConnection(parameters);
        String authToken = DatabaseConnection.database.getAuthTokenFor(email);
        closeDatabaseConnection();
        return authToken;
    }

    @SneakyThrows
    public static String getToken(String email) {
        establishDatabaseConnection(parameters);
        String authToken = DatabaseConnection.database.getTokenFor(email);
        closeDatabaseConnection();
        return authToken;
    }

    @SneakyThrows
    public static String getPin(String email) {
        establishDatabaseConnection(parameters);
        String pin = DatabaseConnection.database.getPinFor(email);
        closeDatabaseConnection();
        return pin;
    }

    @SneakyThrows
    public static String getElement(String table, String column1, String element1, String column2){
        establishDatabaseConnection(parameters);
        String element = DatabaseConnection.database.getElementFor(table, column1, element1, column2);
        closeDatabaseConnection();
        return element;
    }

    public static Boolean getActiveUserStatus(String email) throws SQLException, JSchException {
        establishDatabaseConnection(parameters);
        Boolean activeUser = DatabaseConnection.database.isUserActiveFor(email);
        closeDatabaseConnection();
        return activeUser;
    }
    @SneakyThrows
    public static Boolean elementPresent(String table, String column, String element) {
        establishDatabaseConnection(parameters);
        Boolean authToken = DatabaseConnection.database.elementPresentQuery(table, column, element);
        closeDatabaseConnection();
        return authToken;
    }
    @SneakyThrows
    public static Boolean elementPresent(String table, String column1, String element1, String column2, String element2){
        establishDatabaseConnection(parameters);
        Boolean elementPresent = DatabaseConnection.database.isElementPresent(table,column1,element1,column2,element2);
        closeDatabaseConnection();
        return elementPresent;
    }

    @SneakyThrows
    public static Boolean elementPresent(String table, String column1, String element1, String column2, String element2,String column3, String element3){
        establishDatabaseConnection(parameters);
        Boolean elementPresent = DatabaseConnection.database.isElementPresent(table,column1,element1,column2,element2,column3,element3);
        closeDatabaseConnection();
        return elementPresent;
    }

    @SneakyThrows
    public static Boolean nullElementPresent(String table, String column1, String element1, String column2){
        establishDatabaseConnection(parameters);
        Boolean elementPresent = DatabaseConnection.database.nullIsElementPresent(table,column1,element1,column2);
        closeDatabaseConnection();
        return elementPresent;
    }

    public static Boolean getPinDeletedStatus(String email) throws SQLException, JSchException{
        establishDatabaseConnection(parameters);
        Boolean pinDeleted = DatabaseConnection.database.emailPinIsDeleted(email);
        closeDatabaseConnection();
        return pinDeleted;
    }

    @SneakyThrows
    public static void updateDataBase(String table, String column1, String element1, String column2, String element2){
        establishDatabaseConnection(parameters);
        DatabaseConnection.database.updateDb(table,column1,element1,column2,element2);
        closeDatabaseConnection();
    }

    @SneakyThrows
    public static void updateEmailPinStatus(String email){
        establishDatabaseConnection(parameters);
        DatabaseConnection.database.updatePinIsDeletedToTrue(email);
        closeDatabaseConnection();
    }


    @SneakyThrows
    public static ResultSet rowContainingElement(String table, String column, String element) {
        establishDatabaseConnection(parameters);
        ResultSet result = DatabaseConnection.database.rowContainingElementQueryAuthDb(table, column, element);
        closeDatabaseConnection();
        return result;
    }


    @SneakyThrows
    public static void setWorkspaceId(String homeOwnerID) {

//        String userId = null;
        establishDatabaseConnection(parameters);
//        ResultSet userIdRow = DatabaseConnection.database.rowContainingElementQueryAuthDb("users_user", "home_owner_id", homeOwnerID);
//        userId = userIdRow.getString("id");
        ResultSet workSpaceIdRow = DatabaseConnection.database.rowContainingElementQueryAuthDb("workspaces_workspace", "home_owner_id", homeOwnerID);
        workspaceId = workSpaceIdRow.getString("id");
        closeDatabaseConnection();
    }

}