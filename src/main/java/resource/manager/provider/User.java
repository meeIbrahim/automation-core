package resource.manager.provider;

import resource.manager.provider.common.IndependentEntity;

import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.pages.users.Users;
import ui.com.ztna.web.parameters.UserParameters;


public class User extends IndependentEntity<UserParameters> {

    public User() {
        super(new Users());
    }

    @Override
    protected UserParameters getParameters() throws UsedResource {
        return Data.localUser().any();
    }
}
