package OS;

import desktop.DesktopAgent;
import files.utils.FileUtils;
import lombok.SneakyThrows;


import java.io.File;
import java.nio.file.Paths;

public class Agent {
    private static final String PowerShellScript = "scripts\\install-desktop-agent.ps1";
    private static final String InstallationExecutable = "target/release";
    private static final String MacVolumePath = "/Volumes/*/ExtremeCloud-ZTA.app";
    private static final String MacDetachPath = "/Volumes/ExtremeCloud*";
    @SneakyThrows
    public static Boolean Install(String ZipFile){
        if (!FileUtils.Unzip(ZipFile)) {return false;} // Unzip Downloaded Agent Files
        Boolean status;
        if (utils.isWindows()) {
            status = installForWindows();
        }
        else if (utils.isMac()) {
            status =  installForMac();
        }
        else {
            status =  installForLinux();
        }
        DesktopAgent.Installed(status);
        return status;
    }

    private static Boolean installForLinux(){
        // Getting Path to Installation Executable
        String PathToFile = Paths.get(
                System.getProperty("user.dir"),
                InstallationExecutable.replace("/", File.separator)
                )
                .toString();
        localTerminal term = localTerminal.Execute("sudo dpkg -i PATH/*.deb".replace("PATH",PathToFile),"echo $?");
        term.RunCommand();
        String Output = term.getLastLine();
        return Output.contains("0"); // Exit Code 0 is returned if agent installation is successful
    }
    private static Boolean installForWindows() throws Exception {
        /// Windows Installation is done through PowerShell Script stored in 'scripts' folder
        /// ExecutionPolicy must be set to RemoteSigned or Unrestricted to run the PS script
        String PathToScript = Paths.get(
                System.getProperty("user.dir"),
                PowerShellScript)
                .toAbsolutePath().toString();
        String ExecutionPolicy = localTerminal.Execute("powershell Get-ExecutionPolicy").RunCommand().get(0);
        if (ExecutionPolicy.equals("RemoteSigned") || ExecutionPolicy.equals("Unrestricted")) {
            String Output = localTerminal.Execute("powershell SCRIPT".replace("SCRIPT", PathToScript)).RunCommand().get(0);
            DesktopAgent.Close(); /// Agent starts immediately after installation, Closing
            return Output.trim().contains("True"); // True is returned if agent installation is successful
        }
        else {throw new Exception("Execution Policy not Set to 'RemoteSigned'");}
    }

    private static Boolean installForMac(){
        // Getting Path to Installation Executable
        String PathToFile = Paths.get(
                System.getProperty("user.dir"),
                InstallationExecutable.replace("/",File.separator)
                )
                .toString();
        String MountImage = String.format("sudo hdiutil attach %s/*.dmg",PathToFile);
        String InstallApp = String.format("sudo cp -rf %s /Applications",MacVolumePath);
        String UmountImage = String.format("sudo hdiutil detach %s",MacDetachPath);

        localTerminal term = localTerminal.Execute(MountImage,InstallApp,"echo $?");
        term.RunCommand();
        String Output =term.getLastLine();
        localTerminal.Execute(UmountImage).RunCommand(); // Unmount Image -> Might break if More images already mounted
        return Output.contains("0");
    }
}
