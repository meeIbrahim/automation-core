package resource.manager.provider;


import resource.cached.ZTAParameters;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import resource.data.Data;
import resource.data.UsedResource;

import ui.com.ztna.web.parameters.webAppParameters;


public class webApp extends DependentEntity<webAppParameters> {


    public webApp() {
        super(new ui.com.ztna.web.pages.services.tab.web.webApp(),RM.connector());
    }

    @Override
    protected webAppParameters getParameters(String parent) throws UsedResource {
        return Data.webApp().connector(parent).any();
    }

}
