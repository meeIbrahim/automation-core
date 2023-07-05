package OS;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;


public class utils {

    private static String OS = null;
    ///////  DETERMINE OS /////////////////
    public static String getOsName(){
        if (OS==null) {
            String os_name = System.getProperty("os.name");
            if (os_name.toLowerCase().contains("win")){OS = "windows";}
            if (os_name.toLowerCase().contains("linux")){OS = "linux";}
            if (os_name.toLowerCase().contains("mac")){OS = "mac";}
        }
        return OS;
    }

    public static boolean isWindows(){
        return getOsName().equals("windows");
    }
    public static boolean isUnix(){
        return OS.equals("linux") || OS.equals("mac");
    }
    public static boolean isMac() {return OS.equals("mac");}

    /// Platform based Commands
    public static void killProcessNamed(String ProcessName){
        String Command="";
        if (isWindows()){
            Command = "taskkill /IM <ProcessName> /F";
        }
        else {
            Command = "pkill -9 <ProcessName>";
        }
        localTerminal.Execute(Command.replace("<ProcessName>",ProcessName)).RunCommand();

    }

    public static String deleteDirectory(String Directory){
        List<String> NotSafe = Arrays.asList("/","~","~/","./",".","C:\\");
        if (NotSafe.contains(Directory)){return "Not Safe";}
        String Command = "";
        if (isWindows()){
            Command = "rd /s /q <Directory>";
        }
        else {
            Command = "rm -rfv <Directory>";
        }
        return  localTerminal.Execute(Command.replace("<Directory>",Directory)).RunCommand().get(0);
    }
    public static String getAbsolutePath(String Path){
        if (isWindows()){
            return Path;
        }
        else {
            return localTerminal.Execute("echo PATH".replace("PATH",Path)).RunCommand().get(0);
        }
    }
    @SneakyThrows
    public static void copyFolder(String Source, String Destination) {
        Path source = Paths.get(Source);
        Path target = Paths.get(Destination);
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.copy(file, target.resolve(source.relativize(file).toString()),StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static boolean createDirectory(String Directory){
        return new File(Directory).mkdirs();
    }
}
