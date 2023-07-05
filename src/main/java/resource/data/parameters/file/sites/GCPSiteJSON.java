package resource.data.parameters.file.sites;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GCPSiteJSON extends SiteJSON{

    public static final String PROJECT_ID_KEY = "id";
    @SerializedName(PROJECT_ID_KEY)
    public String projectId = "";

    public static final String VPCS_KEY = "vpcs";
    @SerializedName(VPCS_KEY)
    public ArrayList<String> vpcs = new ArrayList<>();

}
