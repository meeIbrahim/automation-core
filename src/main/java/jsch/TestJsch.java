package jsch;

import com.jcraft.jsch.JSchException;
import jdbc.Database;
import jdbc.DatabaseBuilder;

import java.sql.SQLException;

public class TestJsch {

    public static void main(String[] args) throws JSchException, SQLException {
        String host="ec2-18-216-217-82.us-east-2.compute.amazonaws.com";
        String user="ec2-user";
        Integer port = 22;
        String pemFilePath = "en-ztna.pem";

        RemoteHost remoteHost = new RemoteHost().onUrl(host).forUser(user).havingIdentityFileAt(pemFilePath).onPort(port);
        remoteHost.createSession();
        remoteHost.connectSession();


        Integer localPort = 15000;
        Integer remotePort = 5432;
        String dbHost = "rds-qa.c1l6ph6diymr.us-east-2.rds.amazonaws.com";

        ForwardPort forwardPort = new PortForwardBuilder()
                .localPort(localPort)
                .remotePort(remotePort)
                .host(dbHost)
                .build();
        Integer forwardedPort = forwardPort.forSession(remoteHost.getSession());

        String rdbms = "postgresql";
        String localHost = "localhost";
        String db = "auth_db";
        String dbUser = "auth_user";
        String dbPassword = "auth@password";

        Database database = new DatabaseBuilder()
                .rdbms(rdbms)
                .host(localHost)
                .port(forwardedPort)
                .db(db)
                .userName(dbUser)
                .password(dbPassword)
                .build();

        database.connect();

        String authToken = database.getAuthTokenFor("ztna.automation@gmail.com");
        System.out.println("Auth Token: "+authToken);

        database.disconnect();
        remoteHost.disconnectSession();
    }
}
