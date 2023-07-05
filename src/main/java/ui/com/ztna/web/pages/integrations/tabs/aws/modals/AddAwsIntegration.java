package ui.com.ztna.web.pages.integrations.tabs.aws.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;
import ui.com.ztna.web.parameters.AWSIntegrationParameters;

import static constants.Waits.THIRTY;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class AddAwsIntegration {
    public static Target INTEGRATION_NAME = Input.BY_ORDER.of("1").called("Integration Name");
    public static Target ACCOUNT_ID = Input.BY_ORDER.of("2").called("AWS Account ID");
    public static Target REGION = Dropdown.BY_ORDER.of("1").called("Region");
    public static Target ACCESS_KEY_ID = Input.BY_ORDER.of("1").called("AWS Access Key ID");
    public static Target AWS_SECRET = Input.BY_ORDER.of("3").called("AWS Secret");
    public static Target SESSION_TOKEN = Input.BY_ORDER.of("5").called("AWS Secret");


    public static Performable openAwsIntegrationTab() {
        return Task.where("{0} opens AWS Integration tab", actor -> {
            actor.attemptsTo(
                    Check.whether(!Presence.of(IntegrationsUI.INTEGRATION_TAB_BUTTON_SELECTED.of("AWS")).asABoolean().answeredBy(actor))
                            .andIfSo(Click.on(IntegrationsUI.INTEGRATION_TAB_BUTTON.of("1"))),
                    waitPresenceOf(IntegrationsUI.ADD_INTEGRATION_BUTTON, THIRTY)
            );
        });
    }

    public static Performable fillGeneralSettings(AWSIntegrationParameters parameters){
        return Task.where("{0} fills General Settings for AWS Integration",actor -> {
            actor.attemptsTo(
                    Input.of(INTEGRATION_NAME).with(parameters.name),
                    Input.of(ACCOUNT_ID).with(parameters.AwsAccountId),
                    Check.whether(parameters.region.isEmpty()).andIfSo(
                            Dropdown.of(REGION).select(1)
                    ).otherwise(
                            Dropdown.of(REGION).select(parameters.region)
                    )
            );

        });
    }
    public static Performable fillIntegrationCredentials(AWSIntegrationParameters parameters){
        return Task.where("{0} fills Integration credentials for AWS Integration",actor -> {
            actor.attemptsTo(
                    Input.of(ACCESS_KEY_ID).with(parameters.AwsAccessKeyId),
                    Input.of(AWS_SECRET).with(parameters.AwsSecret),
                    Input.of(SESSION_TOKEN).with(parameters.SessionToken)
            );

        });
    }
}
