package jdbc;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionToDBOverSSH {

    /**
     * Java Program to connect to the remote database through SSH using port forwarding
     * @author Pankaj@JournalDev
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        String host="ec2-18-216-217-82.us-east-2.compute.amazonaws.com";
        String user="ec2-user";

        int lport=15000; //randomly chosen local port
        int rport=5432;

        String dbuserName = "auth_user";
        String dbpassword = "auth@password";
        String dbHost = "rds-qa.c1l6ph6diymr.us-east-2.rds.amazonaws.com";
        String url = "jdbc:postgresql://localhost:15000/auth_db";

        Connection conn = null;
        Session session= null;
        try{
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();
            jsch.addIdentity("en-ztna.pem");
            session=jsch.getSession(user, host, 22);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");

            int forwarded_port = session.setPortForwardingL(lport, dbHost, rport);
            System.out.println("localhost:"+forwarded_port+" -> "+host+":"+rport);
            System.out.println("Port Forwarded");

            conn = DriverManager.getConnection (url, dbuserName, dbpassword);
            System.out.println ("Database connection established");
            System.out.println("DONE");

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM public.users_emailpin");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("email"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if(session !=null && session.isConnected()){
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }
    }

}
