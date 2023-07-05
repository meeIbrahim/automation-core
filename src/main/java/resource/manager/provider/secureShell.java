package resource.manager.provider;

import resource.cached.ZTAParameters;
import resource.data.Data;
import resource.data.UsedResource;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;

import ui.com.ztna.web.parameters.secureShellParameters;

public class secureShell extends DependentEntity<secureShellParameters> {
    public secureShell() {
        super(new ui.com.ztna.web.pages.services.tab.secure.shell.secureShell(), RM.connector());
    }

    @Override
    protected secureShellParameters getParameters(String parent) throws UsedResource, ResourceException {
        return Data.secureShell().connector(parent).any();    }

}
