package ui.com.ztna.web.pages.identityProviders.tab.gsuite;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.identityProviders.IdentityProviders;
import ui.com.ztna.web.pages.identityProviders.tab.gsuite.modals.AddGSuiteIDP;
import ui.com.ztna.web.parameters.GSuiteIDPParameters;


public class GSuiteIDP extends IdentityProviders<GSuiteIDPParameters> {
    @Override
    public Performable create(GSuiteIDPParameters parameters) {
        return Task.where("{0} adds Azure IDP Settings", actor -> {
            actor.attemptsTo(
                    openPage(),
                    AddGSuiteIDP.openGSuiteIDPTab(),
                    AddGSuiteIDP.fill(parameters),
                    AddGSuiteIDP.validate(),
                    Modal.save()
            );
        });

    }

}
