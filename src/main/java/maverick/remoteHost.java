package maverick;

import com.sshtools.client.*;
import com.sshtools.client.shell.ExpectShell;
import com.sshtools.client.shell.ShellProcess;
import com.sshtools.client.shell.ShellTimeoutException;
import com.sshtools.client.tasks.ShellTask;
import com.sshtools.common.publickey.SshKeyUtils;
import com.sshtools.common.ssh.SshException;
import com.sshtools.common.ssh.components.SshKeyPair;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class remoteHost {

        private String User;
        private String Host;
        private Integer Port;

        private final SshClient client;
        private String Sudo;
        public List<String> LastOutput;

//////////////////////////////////////// CREATION AND AUTHENTICATION ////////////////////////////////////////////////////
        @SneakyThrows
        public remoteHost(String User, String Host, Integer Port,String Password, String PemPath, String Passphrase){
                this.Sudo = "";
                this.User = User;
                this.Host = Host;
                this.Port = Port;
                this.client = new SshClient(this.Host,this.Port,this.User);
                this.client.getForwardingPolicy().allowForwarding(); // Allow Port Forwarding
                System.out.println("Attempting Connection with Machine: " + Host);
                if (PemPath.isEmpty()){
                        if(!authenticateClient(Password)){throw new IllegalArgumentException("Password Authentication Failed!");}
                        else {System.out.println("Password Authentication Succeeded");}
                }
                else {
                        if(!authenticateClient(PemPath,Passphrase)){throw new IllegalArgumentException("Key Pair Authentication Failed!");}
                        else {System.out.println("Key Pair Authentication Succeeded");}
                }
        }
        @SneakyThrows
        private boolean authenticateClient(String Password){
                ClientAuthenticator auth = new PasswordAuthenticator(Password.toCharArray());
                this.client.authenticate(auth,30000);
                if (this.client.isAuthenticated()){
                        return true;
                }
                return false;
        }

        @SneakyThrows
        private boolean authenticateClient(String PemPath,String Passphrase){
                SshKeyPair pair = SshKeyUtils.getPrivateKey(new File(PemPath),Passphrase);
                ClientAuthenticator auth = new PublicKeyAuthenticator(pair);
                this.client.authenticate(auth,30000);
                if (this.client.isAuthenticated()){
                        return true;
                }
                return false;
        }
///////////////////////////////////////// COMMAND EXECUTION ////////////////////////////////////////////////////////////

        @SneakyThrows
        public List<String> RunCommand(String... Commands){
                ArrayList<String> Output = new ArrayList<>();
                String sudo_password = this.Sudo;
                this.client.runTask(new ShellTask(this.client) {
                        @Override
                        protected void onOpenSession(SessionChannelNG sessionChannelNG) throws IOException, SshException, ShellTimeoutException {
                                ExpectShell shell = new ExpectShell(this);
                                ShellProcess process = shell.sudo("echo \"Commencing Command Execution\"",sudo_password);
                                process.drain();
                                for (String Command: Commands){
                                        process = shell.sudo(Command,sudo_password);
                                        process.drain();
                                        System.out.println(process.getCommandOutput());
                                        Output.add(process.getCommandOutput());
                                }
                        }
                });
                this.LastOutput = Output;
                return Output;
        }
        @SneakyThrows
        public Hashtable<String,String> RunCommand(Hashtable<String,String> Commands){
                Hashtable<String,String> Output = new Hashtable<>();
                String sudo_password = this.Sudo;
                this.client.runTask(new ShellTask(this.client) {
                        @Override
                        protected void onOpenSession(SessionChannelNG sessionChannelNG) throws IOException, SshException, ShellTimeoutException {
                                ExpectShell shell = new ExpectShell(this);
                                ShellProcess process = shell.sudo("echo \"Commencing Command Execution\"",sudo_password);
                                process.drain();
                                Enumeration<String> keys = Commands.keys();
                                while (keys.hasMoreElements()){
                                        String Command_Name = keys.nextElement();
                                        String Command = Commands.get(Command_Name);
                                        process = shell.sudo(Command,sudo_password);
                                        process.drain();
                                        System.out.println(Command_Name + " :: " + process.getCommandOutput());
                                        Output.put(Command_Name,process.getCommandOutput());
                                }
                        }
                });
                return Output;
        }


/////////////////////////////////////////  PORT FORWARDING  ////////////////////////////////////////////////////////////

        public void StopLocalPortForwarding(){                  // Stop All Local Port Forwarding
                this.client.stopLocalForwarding();
        }
        public void StopRemotePortForwarding(){                 // Stop All Remote Port Forwarding
                this.client.stopRemoteForwarding();
        }
        public void StopLocalPortForwarding(Integer LocalPort){                 // Stop Local Port Forwarding for Port #
                this.client.stopLocalForwarding("127.0.0.1",LocalPort);
        }

        @SneakyThrows
        public void StopRemotePortForwarding(Integer LocalPort){                // Stop Remote Port Forwarding for Port #
                this.client.stopRemoteForwarding("127.0.0.1",LocalPort);
        }

        @SneakyThrows
        public void LocalPortForward(String RemoteAddress,Integer LocalPort, Integer RemotePort){
                this.client.startLocalForwarding("127.0.0.1",LocalPort,RemoteAddress,RemotePort);}

        @SneakyThrows
        public Integer LocalPortForward(String RemoteAddress, Integer RemotePort){
                return this.client.startLocalForwarding("127.0.0.1",0,RemoteAddress,RemotePort);}

        @SneakyThrows
        public void RemotePortForward(String RemoteAddress,Integer LocalPort, Integer RemotePort){
                this.client.startRemoteForwarding("127.0.0.1",LocalPort,RemoteAddress,RemotePort);}

        @SneakyThrows
        public Integer RemotePortForward(String RemoteAddress, Integer RemotePort){
                return this.client.startRemoteForwarding("127.0.0.1",0,RemoteAddress,RemotePort);}
////////////////////////////////////////    UTILITIES         //////////////////////////////////////////////////////////
        public void setSudoPassword(String Password){
                this.Sudo = Password;
        }

        public static ConnectionParameters With(){
                return new ConnectionParameters();
        }

////////////////////////////////////////    BUILDER CLASS         //////////////////////////////////////////////////////
        public static class ConnectionParameters {
                private String User;
                private String Host;
                private Integer Port;
                private String PemFile;
                private String Passphrase;
                private String Password;

                public ConnectionParameters(){
                        this.Port = 22;
                        this.Passphrase = null;
                        this.PemFile = "";
                        this.Password = "";
                }

                public ConnectionParameters User(String User){
                        this.User = User;
                        return this;
                }

                public ConnectionParameters Host(String Host){
                        this.Host = Host;
                        return this;
                }

                public ConnectionParameters Port(Integer Port){
                        this.Port = Port;
                        return this;
                }

                public ConnectionParameters PassPhrase(String PassPhrase){
                        this.Passphrase = PassPhrase;
                        return this;
                }

                public ConnectionParameters Password(String Password){
                        this.Password = Password;
                        return this;
                }

                public ConnectionParameters PemFilePath(String PemFile){
                        this.PemFile = PemFile;
                        return this;
                }
                //// Sets Pem File if String is a valid Directory else sets password
                public ConnectionParameters PassOrPem(String PassOrPem){
                        File file = new File(PassOrPem);
                        if (file.isFile()){
                                return this.PemFilePath(PassOrPem);
                        }
                        else
                        {
                                return this.Password(PassOrPem);
                        }
                }

                public remoteHost Connect(){
                        return new remoteHost(User,Host,Port,Password,PemFile,Passphrase);
                }



        }
}
