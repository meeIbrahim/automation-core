package ui.com.ztna.web.common.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class UpdateNameParameters extends IndexedPojo {

    public static final String OLD_NAME_KEY = "Old Name";
    @SerializedName(OLD_NAME_KEY)
    public String oldName = "";

    public static final String NEW_NAME_KEY = "New Name";
    @SerializedName(NEW_NAME_KEY)
    public String newName = "";

}
