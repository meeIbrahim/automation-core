package OS;

import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class localTerminal {
    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    public List<String> LastOutput;
    private List<String> Commands;
    private ProcessBuilder terminal;


    public static localTerminal Execute(String... Commands){
        return new localTerminal(Commands);
    }
    @SneakyThrows
    public localTerminal(ArrayList<String> Commands) {
        Initialize(Commands);
    }

    public localTerminal(String... Commands){
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(Commands));
        Initialize(commands);
    }

    public void Initialize(ArrayList<String> Commands){
        ProcessBuilder terminal = new ProcessBuilder();
        this.Commands = Commands;
        this.terminal = terminal;
        this.LastOutput = new ArrayList<>();
    }
    public localTerminal in(String Dir){
        File location = new File(Dir);
        this.terminal.directory(location);
        return this;
    }

    @SneakyThrows
    public List<String> RunCommand(Integer timeout){
        for (String Command: Commands) {
            if (isWindows) {
                terminal.command("cmd.exe", "/c", Command);
            } else {
                terminal.command("sh", "-c", Command);
            }
            Process process = terminal.start();
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            printStream(inputStream);
            printStream(errorStream);
            process.waitFor();
        }
        return LastOutput;
    }

    public List<String> RunCommand(){
        return this.RunCommand(120);
    }

    public String getLastLine(){
        return LastOutput.get(LastOutput.size()-1);
    }

    @SneakyThrows
    private void printStream(InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.LastOutput.add(line);
                System.out.println(line);
            }

        }
    }

}
