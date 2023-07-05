package resource.manager.provider;


import resource.cached.ZTAParameters;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.pages.rules.Rules;
import ui.com.ztna.web.parameters.RuleParameters;

public class Rule extends DependentEntity<RuleParameters> {

    public Rule() {
        super(new Rules(null,""),
                RM.policy());
    }

    @Override
    protected RuleParameters getParameters(String parent) throws UsedResource, ResourceException {
        return Data.ruleForAnyUser().withPolicy(parent);
    }

}
