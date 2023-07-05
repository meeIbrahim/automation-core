package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class UserJSON extends IndexedPojo implements OneTimeResource {
    public static final String EMAIL_KEY = "email";
    @SerializedName(EMAIL_KEY)
    public String email = "";

    public static final String PASSWORD_KEY = "password";
    @SerializedName(PASSWORD_KEY)
    public String password = "";

    @Override
    public String primary() {
        return email;
    }
}
