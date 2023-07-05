package ui.com.ztna.web.multicloud_integrations.edit_integration.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class EditIntegrationParameters extends IndexedPojo {

    public static final String INTEGRATION_TYPE_KEY = "Integration Type";
    @SerializedName(INTEGRATION_TYPE_KEY)
    public String integrationType = "";

    public static final String INTEGRATION_NAME_TO_BE_EDITED_KEY = "Integration Name to be Edited";
    @SerializedName(INTEGRATION_NAME_TO_BE_EDITED_KEY)
    public String integrationNameToBeEdited = "";

    public static final String EDITED_INTEGRATION_NAME_KEY = "Edited Integration Name";
    @SerializedName(EDITED_INTEGRATION_NAME_KEY)
    public String editedIntegrationName = "";

    public static final String INTEGRATION_NAME_KEY = "Integration Name";
    @SerializedName(INTEGRATION_NAME_KEY)
    public String integrationName = "";
}
