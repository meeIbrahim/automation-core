package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;
import resource.data.parameters.file.sites.AwsSiteJSON;
import resource.data.parameters.file.sites.AzureSiteJSON;
import resource.data.parameters.file.sites.GCPSiteJSON;
import resource.data.parameters.file.sites.OnPremJSON;

import java.util.ArrayList;
import java.util.List;

public class SitesJSON extends IndexedPojo {

    public static final String ON_PREM_KEY = "on-prem";
    @SerializedName(ON_PREM_KEY)
    public List<OnPremJSON> onPremSite = new ArrayList<>();

    public static final String AWS_KEY = "aws";
    @SerializedName(AWS_KEY)
    public List<AwsSiteJSON> awsSite = new ArrayList<>();

    public static final String AZURE_KEY = "azure";
    @SerializedName(AZURE_KEY)
    public List<AzureSiteJSON> azureSite = new ArrayList<>();

    public static final String GCP_KEY = "gcp";
    @SerializedName(GCP_KEY)
    public List<GCPSiteJSON> gcpSite = new ArrayList<>();


}
