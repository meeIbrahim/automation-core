package json.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFile {
    String filepath;
    public JsonFile(String filepath) {
        this.filepath =  filepath;
    }




    public static JsonFile fromPath(String filepath) {
        return new JsonFile(filepath);
    }

    public JsonElement retrieve() {

        Gson gson = new Gson();
        JsonElement jsonElement = new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }
        };

        try {
            Reader reader = Files.newBufferedReader(Paths.get(filepath));
            jsonElement = gson.fromJson(reader, JsonElement.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonElement;
    }

}
