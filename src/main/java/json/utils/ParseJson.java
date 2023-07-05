package json.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ParseJson {

    public static JsonObject objectFromString(String jsonData) {
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(jsonData, JsonObject.class);
        return data;
    }
    public static JsonArray arrayFromString(String jsonArrayString) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
        return jsonArray;
    }
}
