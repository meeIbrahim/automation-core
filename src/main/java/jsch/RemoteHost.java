package jsch;
import com.jcraft.jsch.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RemoteHost {
    private String user;
    private String host;

    private String hostA;
    private Integer port;
    private String identityFilePath;

    private String identityFileB;
    private Session session;

    private Session sessionA;

    private static int forwardedPort;
    private String command;
    private Channel channel;
    private InputStream in;

    public String output;

    public RemoteHost onUrl(String host) {
        this.host = host;
        return this;
    }

    public RemoteHost forUser(String user) {
        this.user = user;
        return this;
    }

    public RemoteHost onPort(Integer port) {
        this.port = port;
        return this;
    }

    public RemoteHost havingIdentityFileAt(String identityFilePath) {
        this.identityFilePath = identityFilePath;
        return this;
    }

    public RemoteHost runningCommand(String command){
        this.command = command;
        return this;
    }

    public void createSession() throws JSchException {
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        jsch.addIdentity(this.identityFilePath);
        session = jsch.getSession(this.user, this.host, this.port);
        session.setConfig(config);
    }
    public String getOutput() throws JSchException {
        return this.output;
    }

    public void createSession(String hostA, String identityFileB){

     try {

         Properties config = new Properties();
         config.put("StrictHostKeyChecking", "no");
         JSch jsch = new JSch();
         jsch.addIdentity(this.identityFilePath);
         sessionA = jsch.getSession(this.user, this.host);
         sessionA.setConfig(config);
         sessionA.connect();
         if (sessionA.isConnected()) {
             System.out.println("Connected host " + this.host + " !");
             forwardedPort = 2222;
             sessionA.setPortForwardingL(forwardedPort, hostA, 22);
         }

         jsch.addIdentity(identityFileB);
         session = jsch.getSession("ubuntu", "localhost", forwardedPort);
         session.setConfig(config);

         if (session.isConnected()) {
             System.out.println("Connected host " + hostA + " !");

         }

     } catch (Exception e){
         e.printStackTrace();
     }


    }



    public void createChannel() throws JSchException, IOException {
        channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
        in=channel.getInputStream();
    }


    public String runCommand() throws IOException {
        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
                output = output + (new String(tmp, 0, i));

            }
            if(channel.isClosed()){
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }

        }
        this.output=this.output.replace("null","");

        return output;
    }



    public Session getSession() {
        return session;
    }

    public Channel getChannel() { return channel; }

    public void connectSession() throws JSchException {
        session.connect();
        System.out.println("SSH session connection established with: " + session.getHost());
    }

    public void disconnectSession() {
        if (sessionA != null && sessionA.isConnected()) {
            System.out.println("Closing SSH session connection");
            sessionA.disconnect();
        }
        if (session != null && session.isConnected()) {
            System.out.println("Closing SSH session connection");
            session.disconnect();
        }
    }

    public void disconnectPortForwardedSession() {
        if (sessionA != null && sessionA.isConnected()) {
            System.out.println("Closing SSH session connection");
            sessionA.disconnect();
        }
        if (session != null && session.isConnected()) {
            System.out.println("Closing SSH session connection");
            session.disconnect();
        }
    }
    public void connectChannel() throws  JSchException{
        channel.connect();
        System.out.println("SSH channel connection established");
    }

    public void disconnectChannel(){
        if (channel != null && channel.isConnected()){
            System.out.println("Closing SSH channel connection");
            channel.disconnect();
        }
    }


//    public static void main(String[] args) throws JSchException, IOException {
//
//        RemoteHost vm = new RemoteHost().onUrl("3.19.212.155").forUser("ubuntu").onPort(22).havingIdentityFileAt("/home/abdullah/web-controller-automation/src/test/resources/pem/qa.pem").runningCommand("ls");
//        vm.createSession("10.0.213.131","/home/abdullah/web-controller-automation/src/test/resources/pem/qa.pem");
//        vm.createChannel();
//        vm.connectChannel();
//        vm.runCommand();
//        System.out.println("\n");
//        System.out.println(vm.output);
//        String o = vm.getOutput();
//        vm.disconnectPortForwardedSession();
//    }

}