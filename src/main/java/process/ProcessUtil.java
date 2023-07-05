package process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtil {

    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        System.out.println("Output:");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
