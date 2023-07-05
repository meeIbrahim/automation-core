package resource.reader.parameters.file.connectors;

import com.google.gson.annotations.SerializedName;
import resource.data.parameters.file.ConnectorJSON;
import resource.data.parameters.file.sites.SiteJSON;

import java.util.ArrayList;

/*Need to check if this is even required*/

public class AwsConnectorJSON extends ConnectorJSON {


    public static final String AWS_ACCOUNT_ID_KEY = "id";
    @SerializedName(AWS_ACCOUNT_ID_KEY)
    public String awsAccountId = "";

    public static final String VPCS_KEY = "vpcs";
    @SerializedName(VPCS_KEY)
    public ArrayList<String> vpcs = new ArrayList<>();





}
