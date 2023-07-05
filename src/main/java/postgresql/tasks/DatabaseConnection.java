package postgresql.tasks;

import com.jcraft.jsch.JSchException;
import jdbc.Database;
import jdbc.DatabaseBuilder;
import jsch.ForwardPort;
import jsch.PortForwardBuilder;
import jsch.RemoteHost;
import postgresql.parameters.DatabaseParameters;
import postgresql.parameters.PortForwardingParameters;
import postgresql.parameters.PostgreSQLConnectionParameters;
import postgresql.parameters.RemoteHostParameters;

import java.sql.SQLException;

public class DatabaseConnection {

    static private RemoteHost remoteHost;
    static public Database database;

    public static void establishDatabaseConnection(PostgreSQLConnectionParameters parameters) throws JSchException, SQLException {

        RemoteHostParameters remoteHostParameters = parameters.remoteHostParameters;
        remoteHost = new RemoteHost().onUrl(remoteHostParameters.host).forUser(remoteHostParameters.user).havingIdentityFileAt(remoteHostParameters.pemFilePath).onPort(remoteHostParameters.port);
        remoteHost.createSession();
        remoteHost.connectSession();

        PortForwardingParameters portForwardingParameters = parameters.portForwardingParameters;
        ForwardPort forwardPort = new PortForwardBuilder()
                .localPort(portForwardingParameters.localPort)
                .remotePort(portForwardingParameters.remotePort)
                .host(portForwardingParameters.dbHost)
                .build();
        Integer forwardedPort = forwardPort.forSession(remoteHost.getSession());

        DatabaseParameters databaseParameters = parameters.databaseParameters;
        database = new DatabaseBuilder()
                .rdbms(databaseParameters.rdbms)
                .host(databaseParameters.host)
                .port(forwardedPort)
                .db(databaseParameters.db)
                .userName(databaseParameters.dbUser)
                .password(databaseParameters.dbPassword)
                .build();

        database.connect();
    }

    public static void closeDatabaseConnection() throws SQLException {
        database.disconnect();
        remoteHost.disconnectSession();
    }
}
