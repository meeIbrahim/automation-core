package indexed.pojo.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class IndexedPojoDeserializer<T extends IndexedPojo> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    public static <T extends IndexedPojo> T deserializeJsonTo(JsonObject parameters, Class<T> tClass) {
        IndexedPojoTypeAdapterFactory indexedPojoTypeAdapterFactory = new IndexedPojoTypeAdapterFactory();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(indexedPojoTypeAdapterFactory)
                .create();

        T instance = gson.fromJson(parameters, tClass);
        instance.setKeySet(parameters);
        return instance;
    }

    public static <T extends IndexedPojo> T deserializeMapTo(Map parameters, Class<T> tClass) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(parameters).getAsJsonObject();
        return deserializeJsonTo(jsonObject,tClass);
    }
}
