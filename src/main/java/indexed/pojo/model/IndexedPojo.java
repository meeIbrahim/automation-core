package indexed.pojo.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class IndexedPojo {
    List<String> keySet;

    public List<String> keySet() {
        return keySet;
    }

    public void setKeySet(List<String> keySet) {
        this.keySet = keySet;
    }
    public void setKeySet(JsonObject parameters) {
        this.keySet = new ArrayList<>(parameters.keySet());
    }
    public void setKeySet(Map<String,String> parameters) {
        this.keySet = new ArrayList<>(parameters.keySet());
    }

}
