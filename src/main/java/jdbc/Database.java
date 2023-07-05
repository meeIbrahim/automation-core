package jdbc;

import postgresql.databases.AuthDb;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Database {
    private String userName;
    private String password;
    private String url;

    private Connection connection;
//    private Database database;


    public Database(DatabaseBuilder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
        this.url = "jdbc:"+builder.rdbms+"://"+builder.host+":"+builder.port+"/"+ builder.db;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
//        database = DatabaseConnection.database;
        System.out.println ("Database connection established");
    }

    public void disconnect() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
            System.out.println("Closing Database Connection");
        }
    }

    public String getAuthTokenFor(String email) throws SQLException {
//        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE email='%s'", email));
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE deleted_at IS NULL AND email = '%s' order by id desc limit 1", email));
        if(resultSet.next()) {
            if (!resultSet.getBoolean("is_deleted")) {
                System.out.println("Authentication token returned");
                return resultSet.getString("auth_token");
            }
            else {
                System.out.println("Authentication token already used");
                return "authentication_token_already_used";
            }
        } else {
            System.out.println("Email not found");
            return "email_not_found";
        }
    }

    public String getTokenFor(String email) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE email='%s'", email));
        resultSet.next();
        return resultSet.getString("auth_token");
    }


    public String getPinFor(String email) throws SQLException {
//        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE email='%s'", email));
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE deleted_at IS NULL AND is_deleted='f' AND email = '%s'", email.toLowerCase()));
        if(resultSet.next()) {
            if (!resultSet.getBoolean("is_deleted")) {
                System.out.println("Pin returned :"+resultSet.getString("pin"));
                return resultSet.getString("pin");
            }
            else {
                System.out.println("Pin already used");
                return "pin_already_used";
            }
        } else {
            System.out.println("Email not found");
            return "email_not_found";
        }
    }

    public String getElementFor(String table, String column1, String element1, String column2 ) throws SQLException {
//        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE email='%s'", email));
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%1$s WHERE %2$s = '%3$s'", table,column1,element1));
        resultSet.next();
        return resultSet.getString(column2);

    }

    public String getElementFor(String table, String GetColumn, HashMap<String,String> conditions) throws SQLException {
        String Conditions = null;
        for (Map.Entry<String,String> condition : conditions.entrySet()){
            if (Conditions == null) {Conditions = String.format("%s = '%s'",condition.getKey(),condition.getValue());}
            else {Conditions = Conditions + String.format(" AND %s = '%s'",condition.getKey(),condition.getValue());}
        }
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%1$s WHERE %2$s", table,Conditions));
        resultSet.next();
        return resultSet.getString(GetColumn);

    }


    public Boolean elementPresentQuery(String table, String column, String element) throws SQLException {
//        theActorInTheSpotlight().remember("workspaceId", "25");
//        "SELECT * FROM public.%s WHERE %s = '%s' AND workspace_id = '25'"
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%s WHERE %s = '%s'", table, column, element));
        if(resultSet.next()) {
            return Objects.equals(resultSet.getString(column), element);
        }
        return false;
    }

    public Boolean deleteElement(String table, String column, String element) throws SQLException {
        connection.createStatement().execute(String.format("DELETE FROM public.%s WHERE %s = '%s'", table, column, element));
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%s WHERE %s = '%s'", table, column, element));
        if(!resultSet.next()) {
            return true;
        }else {return false;}
    }


    public ResultSet rowContainingElementQueryResourceDb(String table, String column, String element, String workspaceId) throws SQLException {
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE).executeQuery(String.format("SELECT * FROM public.%s WHERE %s = '%s' AND workspace_id = '%s'", table, column, element, workspaceId));
        if(resultSet.next()) {
            return resultSet;
        }else{return null;}
    }

    public ResultSet rowContainingElementWithoutWorkspaceQueryResourceDb(String table, String column, String element) throws SQLException {
        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE).executeQuery(String.format("SELECT * FROM public.%s WHERE %s = '%s'", table, column, element));
        if(resultSet.next()) {
            return resultSet;
        }else {return null;}
    }

    public void updateDbLastStatus(String table, String column1, String element1, String column2, String replacingtime) throws SQLException {

//        String workspaceId = AuthDb.returnWorkspaceId();
        String workspaceId = AuthDb.workspaceId;
        theActorInTheSpotlight().remember("WorkSpaceID",workspaceId);

        try(Statement stmt = connection.createStatement()) {
            stmt.executeQuery(String.format("UPDATE public.%1$s SET %2$s = %3$s WHERE %4$s = '%5$s' AND workspace_id = '%6$s'", table, column2, replacingtime, column1, element1,workspaceId));

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void updateDb(String table, String column1, String element1, String column2, String replacingElement) throws SQLException {

//        ResultSet resultSet = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE).executeQuery(String.format("SELECT * FROM public.%1$s WHERE  %2$s = '%3$s'", table,column1,element1));
//        resultSet.next();
//        resultSet.updateString(column2,replacingElement);
//        resultSet.updateRow();
//        System.out.println(resultSet.getString(column2));
//        resultSet.updateRow();

        PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE public.%1$s SET %4$s = '%5$s' WHERE %2$s = '%3$s'", table,column1,element1,column2,replacingElement));
        preparedStatement.executeUpdate();

    }
    public ResultSet rowContainingElementQueryAuthDb(String table, String column, String element) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%s WHERE %s = '%s'", table, column, element));
        if(resultSet.next()) {
            return resultSet;
        }
        return null;
    }

    public Boolean isUserActiveFor(String email) throws SQLException {

        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_user WHERE users_user.status = 'ACTIVATED' AND email = '%s'", email));
        resultSet.next();
        System.out.println(resultSet.getString("status"));

        return resultSet.getString("status").equals("ACTIVATED");

    }

    public Boolean isElementPresent(String table, String column1, String element1, String column2, String element2) {

        try {
            System.out.println("table: "+table + "/ Column1: " + column1 + "/Element1: " + element1+ "/ Column2: " + column2 + "/Element2: " + element2);
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%1$s WHERE  %2$s = '%3$s'", table,column1,element1));
            resultSet.next();
            System.out.println(resultSet.getString(column2)+"From DB 2");

            return resultSet.getString(column2).equals(element2);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public Boolean isElementPresent(String table, String column1, String element1, String column2, String element2, String column3, String element3) {

        try {
            System.out.println("table:"+table + " / Column1:" + column1 + " / Element1:" + element1+ " / Column2:" + column2 + " /Element2:" + element2+ " / Column3:" + column3 + " /Element3:" + element3);
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%1$s WHERE  %2$s = '%3$s' AND %4$s = '%5$s'", table,column1,element1,column2,element2));
            resultSet.next();
            System.out.println("From DB : Column1:"+resultSet.getString(column1)+" / Column2:"+resultSet.getString(column2)+" / Column3:"+resultSet.getString(column3));
            return resultSet.getString(column3).equals(element3);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean nullIsElementPresent(String table, String column1, String element1, String column2) {

        try {
            System.out.println(table + " " + column1 + " " + element1);
            ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.%1$s WHERE  %2$s = '%3$s'", table,column1,element1));
            resultSet.next();
            System.out.println(resultSet.getString("used_by")+"From DB 2");

            return resultSet.getString(column2) == null;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public void updatePinIsDeletedToTrue(String email) throws SQLException {
        connection.createStatement().execute(String.format("UPDATE public.users_emailpin SET is_deleted = true WHERE is_deleted = false and email = '%s'",email));
    }

    public Boolean emailPinIsDeleted(String userName) throws SQLException{

        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM public.users_emailpin WHERE is_deleted = 'false' AND email = '%s'",userName));
        return !resultSet.next();

    }
}
