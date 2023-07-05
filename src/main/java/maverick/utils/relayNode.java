package maverick.utils;

import lombok.SneakyThrows;
import maverick.remoteHost;

import java.util.Hashtable;
import java.util.List;

public class relayNode {
    static String SC_Public_IP = "sudo wg | grep {WG_IP} -B 2 | grep -oP \"(?<=endpoint: ).*(?=:51820)\"";
    static String Relay_wg0_Key = "sudo wg show wg0 public-key";
    static String Relay_wg1_Key = "sudo wg show wg1 public-key";
    static String Relay_wg0_port = "sudo wg show wg0 listen-port";
    static String Relay_wg1_port = "sudo wg show wg1 listen-port";

    @SneakyThrows
    public static Hashtable<String,String> getRelayWG(String RelayHost, String RelayUser, String RelayPem, Integer RelayPort){
        Hashtable<String,String> Commands = new Hashtable<>();
        Commands.put("WG0_Key",Relay_wg0_Key);
        Commands.put("WG1_Key",Relay_wg1_Key);
        Commands.put("WG0_Port",Relay_wg0_port);
        Commands.put("WG1_Port",Relay_wg1_port);
        remoteHost host = remoteHost.With().Host(RelayHost).User(RelayUser).Port(RelayPort).PassOrPem(RelayPem).Connect();
        return host.RunCommand(Commands);

    }

    @SneakyThrows
    public static Hashtable<String,String> getRelayWG(String RelayHost, String RelayUser, String RelayPem){
        return getRelayWG(RelayHost,RelayUser,RelayPem,22);
    }
    @SneakyThrows
    public static String getSCPublicIP(String WG, String RelayHost, String RelayUser, String RelayPem, Integer RelayPort) {
        String Command = SC_Public_IP;
        String Placeholder = "\\{WG_IP\\}";
        remoteHost host = remoteHost.With().Host(RelayHost).User(RelayUser).PassOrPem(RelayPem).Port(RelayPort).Connect();
        host.RunCommand(Command.replaceAll(Placeholder, WG));
        return host.LastOutput.get(0);
    }

    public static String getSCPublicIP(String WG, String RelayHost, String RelayUser, String RelayPem) {
        return getSCPublicIP(WG,RelayHost,RelayUser,RelayPem,22);
    }

    public static Hashtable<String,String> getSCPublicIP(List<String> WGs, String RelayHost, String RelayUser, String RelayPem, Integer Port) {
        Hashtable<String,String> IPs = new Hashtable<>();
        for (String WG : WGs){
            IPs.put(WG,getSCPublicIP(WG,RelayHost,RelayUser,RelayPem,Port));
        }
        return IPs;
    }

    public static Hashtable<String,String> getSCPublicIP(List<String> WGs,String RelayHost, String RelayUser, String RelayPem) {
        return getSCPublicIP(WGs,RelayHost,RelayUser,RelayPem,22);
    }
}
