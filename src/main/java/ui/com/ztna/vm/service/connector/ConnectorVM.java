package ui.com.ztna.vm.service.connector;

import files.utils.FileUtils;
import maverick.remoteHost;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;

public class ConnectorVM {
    public static Performable install(String command, PrivateConnectorParameters parameters){
        return Task.where("{0} installs Connector on VM",actor -> {
            String removeAll = "sudo bash hostagent_remove.sh";
            runCommand(parameters,removeAll);
            runCommand(parameters, command);
        });
    }
    public static Performable regenerate(String command, PrivateConnectorParameters parameters){
        return Task.where("{0} re-install Connector on VM",actor -> {
            runCommand(parameters, command);
        });
    }

    public static void runCommand(PrivateConnectorParameters parameters, String command) {
        try {
            System.out.println("Running Command on Machine: " + command);
            String privateKey = FileUtils.toAbsolutePath(parameters.pem);
            remoteHost SC = remoteHost.With()
                    .Host(parameters.host)
                    .Port(parameters.port)
                    .User(parameters.user)
                    .PassOrPem(privateKey)
                    .Connect();
            SC.RunCommand(command);
            System.out.println(SC.LastOutput.get(0));

        } catch (Exception e) {
            throw new ConnectorVMException(parameters.host, e);
        }
    }

    public static Performable stopAgent(PrivateConnectorParameters parameters){
        return Task.where("{0} stops agent on Connector",actor -> {
            String privateKey = FileUtils.toAbsolutePath(parameters.pem);
            try {
                remoteHost SC = maverick.remoteHost.With()
                        .Host(parameters.host)
                        .Port(parameters.port)
                        .User(parameters.user)
                        .PassOrPem(privateKey)
                        .Connect();
                SC.RunCommand("sudo systemctl stop hostagent.service");

            } catch (Exception e) {
                throw new ConnectorVMException(parameters.host, e);
            }
        });
    }
    public static Performable deleteAgent(PrivateConnectorParameters parameters){
        return Task.where("{0} stops agent on Connector",actor -> {
            try {
                String privateKey = FileUtils.toAbsolutePath(parameters.pem);
                remoteHost SC = maverick.remoteHost.With()
                        .Host(parameters.host)
                        .Port(parameters.port)
                        .User(parameters.user)
                        .PassOrPem(privateKey)
                        .Connect();
                SC.RunCommand("sudo bash hostagent_remove.sh");

            } catch (Exception e) {
                throw new ConnectorVMException(parameters.host, e);
            }
        });
    }
}


