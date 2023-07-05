package ui.com.ztna.web.pages.identityProviders.tab.azure;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.identityProviders.IdentityProviders;
import ui.com.ztna.web.pages.identityProviders.tab.azure.modals.AddAzureIDP;
import ui.com.ztna.web.parameters.AzureIDPParameters;


public class AzureIDP extends IdentityProviders<AzureIDPParameters> {
    @Override
    public Performable create(AzureIDPParameters parameters) {
        return Task.where("{0} adds Azure IDP Settings",actor -> {
            actor.attemptsTo(
                    openPage(),
                    AddAzureIDP.openAzureIDPTab(),
                    AddAzureIDP.fill(parameters),
                    AddAzureIDP.validate(),
                    Modal.save()
                            );
                        });

    }

}
