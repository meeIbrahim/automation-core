package serenity.custom.tasks;

import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runtime implements Interaction {
    final String command;

    public Runtime(String command) {
        System.out.println("bbb");
        this.command = command;
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T t) {
        System.out.println("ccc");
        java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
        Process process = runtime.exec(command, null, new File("./"));
        printResults(process);

//        ProcessBuilder processBuilder = new ProcessBuilder(command);
//        Process process1 = processBuilder.start();
//        printResults(process1);
    }

    public static Runtime execute(String command) {
        System.out.println("aaa");
        return new Runtime(command);
    }

    private static void printResults(Process process) throws IOException {
        System.out.println("eeeee");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        System.out.println("Output:");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("fffff");
    }
}
