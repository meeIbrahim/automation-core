package json.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FindJsonObjects {
    String key;
    JsonElement value;
    JsonArray array;

    public FindJsonObjects(String key) {
        this.key = key;
    }

    public static FindJsonObjects havingKey(String key) {
        return new FindJsonObjects(key);
    }

    public FindJsonObjects ofValue(Object value) {
        Gson gson = new Gson();

        this.value = gson.toJsonTree(value);
        return this;
    }

    public FindJsonObjects inJsonArray(JsonArray array) {
        this.array = array;
        return this;
    }
    public JsonArray find() {
        JsonArray matchingJsonObjects = new JsonArray();
        array.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement element = jsonObject.get(key);
            if(element.equals(value)) {
                matchingJsonObjects.add(jsonObject);
            };
        });
        return matchingJsonObjects;
    }
}
