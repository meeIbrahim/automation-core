package resource.data.parameters.file.sites;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AwsSiteJSON extends SiteJSON {


    public static final String AWS_ACCOUNT_ID_KEY = "id";
    @SerializedName(AWS_ACCOUNT_ID_KEY)
    public String awsAccountId = "";

    public static final String VPCS_KEY = "vpcs";
    @SerializedName(VPCS_KEY)
    public ArrayList<String> vpcs = new ArrayList<>();





}
