package ui.com.ztna.web.pages.identityProviders.tab.gsuite.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import serenity.custom.interactions.Refresh;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.identityProviders.IdentityProvidersUI;
import ui.com.ztna.web.parameters.GSuiteIDPParameters;

import static ui.com.ztna.web.common.page.Page.LOADER;
import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddGSuiteIDP {
    public static Target CLIENT_ID = Input.BY_ORDER.of("1").called("Client ID");
    public static Target CLIENT_SECRET_ID = Input.BY_ORDER.of("2").called("Client SecretD");
    public static Target APPROVED_DOMAINS = Input.BY_ORDER.of("4").called("Approved Domains");
    public static Performable openGSuiteIDPTab() {
        return Task.where("{0} opens GSuite IDP tab", actor -> {
            actor.attemptsTo(Refresh.theBrowser(), Wait.forInvisibilityOf(LOADER));
            if(Presence.of(IdentityProvidersUI.IDP_TABS.of("GSuite")).asABoolean().answeredBy(actor)) {
                actor.attemptsTo(
                        Click.on(IdentityProvidersUI.IDP_TABS.of("GSuite")),
                        clickButton(IdentityProvidersUI.CONTINUE_BUTTON),
                        waitPresenceOf(IdentityProvidersUI.IDENTITY_PROVIDER_SELECTED.of("GSuite"))
                );
            } else if (Presence.of(IdentityProvidersUI.IDENTITY_PROVIDER_SELECTED.of("Microsoft Azure")).asABoolean().answeredBy(actor)) {
                actor.attemptsTo(
                        clickButton(IdentityProvidersUI.DISCONNECT_IDP),
                        clickButton(IdentityProvidersUI.DISCONNECT_BUTTON),
                        Wait.forInvisibilityOf(LOADER));

                if (Presence.of(IdentityProvidersUI.IDP_TABS.of("GSuite")).asABoolean().answeredBy(actor)){
                    actor.attemptsTo(
                            Click.on(IdentityProvidersUI.IDP_TABS.of("GSuite")),
                            clickButton(IdentityProvidersUI.CONTINUE_BUTTON),
                            waitPresenceOf(IdentityProvidersUI.IDENTITY_PROVIDER_SELECTED.of("GSuite"))

                    );
                }
            } else if (Presence.of(IdentityProvidersUI.IDENTITY_PROVIDER_SELECTED.of("GSuite")).asABoolean().answeredBy(actor)) {
                actor.attemptsTo(Refresh.theBrowser(),Wait.forInvisibilityOf(LOADER));
            }
        });
    }
    public static Performable fill(GSuiteIDPParameters parameters) {
        return Task.where("{0} fills GCP IDP credentials", actor -> {
            actor.attemptsTo(
                    Input.of(CLIENT_ID).with(parameters.clientId),
                    Input.of(CLIENT_SECRET_ID).with(parameters.clientSecret),
                    Check.whether(!parameters.approvedDomains.isEmpty()).andIfSo(
                            Input.of(APPROVED_DOMAINS).with(parameters.approvedDomains)
                    )
            );
        });
    }
    public static Performable validate() {
        return Task.where("{0} validate IDP credentials", actor -> {
            actor.attemptsTo(
                    clickButton(IdentityProvidersUI.VALIDATE_INFORMATION_BUTTON),
                    waitPresenceOf(IdentityProvidersUI.VALIDATION_SUCCESSFUL_MESSAGE),
                    clickButton(IdentityProvidersUI.UPDATE_BUTTON)
            );
        });
    }
}
