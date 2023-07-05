package jsch.utils;

import com.jcraft.jsch.JSchException;
import jsch.RemoteHost;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class relayData {

    public static String getSCPublicIP(String WG, String RelayHost, String RelayUser, String RelayPem, Integer RelayPort) throws JSchException, IOException {
        String Command = "sudo wg | grep {WG_IP} -B 2 | grep -oP \"(?<=endpoint: ).*(?=:51820)\"";
        String Placeholder = "\\{WG_IP\\}";
        RemoteHost remoteHost = new RemoteHost().onUrl(RelayHost).forUser(RelayUser).havingIdentityFileAt(RelayPem).onPort(RelayPort);
        remoteHost.createSession();
        remoteHost.connectSession();
        remoteHost.runningCommand(Command.replaceAll(Placeholder,WG));
        remoteHost.createChannel();
        remoteHost.connectChannel();
        remoteHost.runCommand();
        remoteHost.disconnectSession();
        return remoteHost.output.replace("\n","");
    }

    public static String getSCPublicIP(String WG, String RelayHost, String RelayUser, String RelayPem) throws JSchException, IOException {
        return getSCPublicIP(WG,RelayHost,RelayUser,RelayPem,22);
    }

    public static Hashtable<String,String> getSCPublicIP(List<String> WGs, String RelayHost, String RelayUser, String RelayPem, Integer Port) throws JSchException, IOException {
        Hashtable<String,String> IPs = new Hashtable<>();
        for (String WG : WGs){
            IPs.put(WG,getSCPublicIP(WG,RelayHost,RelayUser,RelayPem,Port));
        }
        return IPs;
    }

    public static Hashtable<String,String> getSCPublicIP(List<String> WGs,String RelayHost, String RelayUser, String RelayPem) throws JSchException, IOException {
        return getSCPublicIP(WGs,RelayHost,RelayUser,RelayPem,22);
    }

}
