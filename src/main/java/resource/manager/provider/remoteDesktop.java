package resource.manager.provider;

import resource.cached.ZTAParameters;
import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.parameters.remoteDesktopParameters;

public class remoteDesktop extends DependentEntity<remoteDesktopParameters> {
    public remoteDesktop() {
        super(new ui.com.ztna.web.pages.services.tab.remote.desktop.remoteDesktop(),RM.connector());
    }

    @Override
    protected remoteDesktopParameters getParameters(String parent) throws UsedResource, ResourceException {
        return Data.remoteDesktop().connector(parent).any();    }

}
