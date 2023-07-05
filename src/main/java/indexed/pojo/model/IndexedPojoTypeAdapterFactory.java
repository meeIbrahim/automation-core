package indexed.pojo.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IndexedPojoTypeAdapterFactory implements TypeAdapterFactory {
    public IndexedPojoTypeAdapterFactory() { }
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        // Only handle IndexedPojo and subclasses
        if (!IndexedPojo.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        // Get the default adapter as delegate
        // Cast is safe due to `type` check at method start
        @SuppressWarnings("unchecked")
        TypeAdapter<IndexedPojo> delegate = (TypeAdapter<IndexedPojo>) gson.getDelegateAdapter(this, type);
        // Cast is safe because `T` is IndexedPojo or subclass (due to `type` check at method start)
        @SuppressWarnings("unchecked")
        TypeAdapter<T> adapter = (TypeAdapter<T>) new TypeAdapter<IndexedPojo>() {
            @Override
            public void write(JsonWriter out, IndexedPojo value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public IndexedPojo read(JsonReader in) throws IOException {

                JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();

                IndexedPojo value = delegate.fromJsonTree(jsonObject);
                value.setKeySet(jsonObject);
                return value;
            }
        };

        return adapter;
    }
}
