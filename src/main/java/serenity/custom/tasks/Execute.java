package serenity.custom.tasks;

import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Execute implements Interaction {
    final String[] command;

    public Execute(String[] command) {
        this.command = command;
    }

    @SneakyThrows
    @Override
    public <T extends Actor> void performAs(T t) {
        System.out.println("aaaaa");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        Process process = processBuilder.start();

        if (hasError(process)) {
            System.out.println("vvvvv");
            printError(process);
            System.out.println("wwwww");
            System.exit(0);
            System.out.println("zzzzz");
        }

        printResults(process);
        System.out.println("ggggg");
    }

//    public void hehe() {
//        Execute.command("");
//    }

    public static Execute command(String... command) {
        return new Execute(command);
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

    private static void printError(Process process) throws IOException {
        System.out.println("ccccc");
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorLine = "";
        System.out.println("Error:");
        while ((errorLine = errorReader.readLine()) != null) {
            System.out.println(errorLine);
        }
        System.out.println("ddddd");
    }

    private static boolean hasError(Process process) throws IOException {
        System.out.println("bbbbb");
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        System.out.println(errorReader.readLine());
        System.out.println("sssss");
        if (errorReader.readLine() == null) {
            System.out.println("false");
            return false;
        }
        else {
            System.out.println("true");
            return true;
        }
    }
}
