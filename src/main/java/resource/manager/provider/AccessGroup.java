package resource.manager.provider;


import resource.manager.provider.common.IndependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.pages.access.groups.AccessGroups;
import ui.com.ztna.web.parameters.AGParameters;

public class AccessGroup extends IndependentEntity<AGParameters> {

    public AccessGroup() {
        super(new AccessGroups());
    }

    @Override
    protected AGParameters getParameters() throws UsedResource {
        return Data.accessGroup().get();
    }
}
