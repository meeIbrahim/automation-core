package resource.data.parameters.file.sites;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AzureSiteJSON extends SiteJSON{

    public static final String AZURE_SUBSCRIPTION_ID_KEY = "id";
    @SerializedName(AZURE_SUBSCRIPTION_ID_KEY)
    public String azureSubscriptionId = "";

    public static final String RESOURCE_GROUP_KEY = "resource group";
    @SerializedName(RESOURCE_GROUP_KEY)
    public String resourceGroup = "";

    public static final String VNETS_KEY = "vnets";
    @SerializedName(VNETS_KEY)
    public ArrayList<String> vnets = new ArrayList<>();



}
