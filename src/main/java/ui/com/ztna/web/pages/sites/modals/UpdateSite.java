package ui.com.ztna.web.pages.sites.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.sites.SiteUi;

import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;

public class UpdateSite {
    public static Target NAME_INPUT = Input.BY_ORDER.of("1").called("Name of Site");
    public static final Target ASSOCIATE_CLOUD_HOSTED_RELAY_REMOVE_BUTTON = Target.the("Associate Cloud Hosted Relay remove button")
            .locatedBy("//*[contains(@id, 'addSite-relayNode')]//span[.//*[contains(text(),'{0}')]]/following-sibling::*[@role='button']");

    public static Performable open(Row row){
        return Task.where("{0} opens Update Site Modal for ROW".replace("ROW",row.name()), actor -> {
            actor.attemptsTo(
                    row.action(SiteUi.UPDATE_SITE_ACTION_MENU),
                    Wait.forPresenceOf(Modal.MODAL_BOX),
                    Wait.forInvisibilityOf(Modal.MODAL_LOAD_ICON)
            );
        });
    }
    public static Performable removeRelay(String RelayName){
        return Task.where("{0} remove associated Relay from Site",actor -> {
            actor.attemptsTo(
                    clickButton(ASSOCIATE_CLOUD_HOSTED_RELAY_REMOVE_BUTTON.of(RelayName))
            );
        });
    }
    public  static Performable updateName(String Name){
        return Task.where("{0} fills update Modal for Site",actor -> {
            actor.attemptsTo(
                    Check.whether(Name.isEmpty()).otherwise(
                            Input.of(NAME_INPUT).with(Name)
                    )
            );
        });
    }
}
