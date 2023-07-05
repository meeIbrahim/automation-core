package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class UsersJSON extends IndexedPojo {
    public static final String LOCAL_KEY = "local";
    @SerializedName(LOCAL_KEY)
    public List<UserJSON> local = new ArrayList<>();
    public static final String MICROSOFT_KEY = "microsoft";
    @SerializedName(MICROSOFT_KEY)
    public List<UserJSON> microsoft = new ArrayList<>();
    public static final String GOOGLE_KEY = "google";
    @SerializedName(GOOGLE_KEY)
    public List<UserJSON> google = new ArrayList<>();
}
