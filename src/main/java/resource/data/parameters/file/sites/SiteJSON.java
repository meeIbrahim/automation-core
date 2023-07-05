package resource.data.parameters.file.sites;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;

public class SiteJSON extends IndexedPojo {

    public static final String NAME_KEY = "name";
    @SerializedName(NAME_KEY)
    public String name = "";

    public static final String HOSTING_PROVIDING_KEY = "hosting provider";
    @SerializedName(HOSTING_PROVIDING_KEY)
    public String hostingProvider = "";

    public static final String RELAYS_KEY = "relays";
    @SerializedName(RELAYS_KEY)
    public ArrayList<String> relays = new ArrayList<>();

    public static final String REGION_KEY = "region";
    @SerializedName(REGION_KEY)
    public String region = "";






}
