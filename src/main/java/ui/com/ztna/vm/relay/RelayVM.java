package ui.com.ztna.vm.relay;

import com.jcraft.jsch.JSchException;
import files.utils.FileUtils;
import maverick.remoteHost;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import postgresql.databases.ResourceDb;

import java.io.IOException;
import java.sql.ResultSet;



public class RelayVM {

    String pem;
    String name;
    String user;
    int port;
    String ip;
    public RelayVM(String name) throws RelayNotFound {
        this.name = name;
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String environment = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("env");
        this.pem = String.format("src/test/resources/pem/cloud-hosted-relay-%s.pem", environment);
        this.user = "ubuntu";
        this.port = 22;
        this.ip = getRelayIP(name);
    }

    public static String getRelayIP(String name) throws RelayNotFound {
        try {
            String relayId;
            ResultSet result = ResourceDb.rowContainingElement("resources_relaynode", "name", name);
            relayId = result.getString("id");
            ResultSet resultSecond = ResourceDb.rowContainingElementWithoutWorkspaceId("resources_relaynodeinstance", "relay_node_id", relayId);
            return resultSecond.getString("endpoint");

        }
        catch (Exception e){
            throw new RelayNotFound(name);
        }
    }

    public String runCommand(String Command) throws JSchException, IOException, RelayCommandException {
        String PrivateKey = FileUtils.toAbsolutePath(pem);
        remoteHost Relay = remoteHost.With().Host(ip).User(user).PassOrPem(PrivateKey).Port(port).Connect();
        Relay.RunCommand(Command);
        return Relay.LastOutput.get(0);
    }
    public String getConnectorIP(String WG) throws Exception {
        String Command = "sudo wg | grep {WG_IP} -B 2 | grep -oP \"(?<=endpoint: ).*(?=:51820)\"";
        String Placeholder = "\\{WG_IP\\}";
        String PrivateKey = FileUtils.toAbsolutePath(pem);
        remoteHost host = remoteHost.With().Host(ip).User(user).PassOrPem(PrivateKey).Port(port).Connect();
        host.RunCommand(Command.replaceAll(Placeholder, WG));
        return host.LastOutput.get(0);
    }
}
