package resource.manager.provider;

import resource.manager.provider.common.IndependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.pages.applicationGroups.ApplicationGroups;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;

public class ApplicationGroup extends IndependentEntity<ApplicationGroupParameters> {


    public ApplicationGroup() {
        super(new ApplicationGroups());
    }

    @Override
    protected ApplicationGroupParameters getParameters() throws UsedResource {
        return Data.applicationGroup().get();
    }
}
