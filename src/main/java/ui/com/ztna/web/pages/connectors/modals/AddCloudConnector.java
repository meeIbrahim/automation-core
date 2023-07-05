package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;

import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;

import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.pages.connectors.ConnectorUI;


import static ui.com.ztna.web.common.page.Modal.MODAL_FOOTER;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.pages.connectors.ConnectorUI.ADD_CONNECTOR_BUTTON;

public class AddCloudConnector {

    protected static String MODAL = "Add Multi-Cloud Service Connector";
    public static Target CLOUD_HOSTING_PROVIDER = Dropdown.BY_ORDER.of("1").called("Cloud Hosting Provider");
    public static Target SITE = Dropdown.BY_ORDER.of("2").called("Site");
    public static Target RELAY = Dropdown.BY_ORDER.of("3").called("Cloud Relay Node");
    public static Target ADD_CONNECTOR_MODAL_BUTTON = Target.the("Modal Add Connector button")
            .locatedBy(MODAL_FOOTER.getCssOrXPathSelector() + "//button[2]");
    public static Target CONNECTIVITY_BUTTON = Target.the("Connectivity Button")
            .locatedBy("//*[contains(@id,\"testConnectivity\")]");
    public static Target CONNECTIVITY_STATUS = Target.the("Connectivity Status")
            .locatedBy("//*[contains(text(),\"Connected\")]");

    public static Performable testConnectivity(){
        return Task.where("{0} Tests Connectivity",actor -> {
            int count = 3;
            while (count > 0){
                actor.attemptsTo(
                        Click.on(CONNECTIVITY_BUTTON)
                );
                if(actor.asksFor(connectivityStatus())){break;}
                count--;
            }
            Ensure.thatTheAnswerTo(connectivityStatus()).isTrue();
        });
    }
    public static Question<Boolean> connectivityStatus(){
        return Question.about("Service Connectivity Status").answeredBy(
                actor -> {
                    actor.attemptsTo(
                            Wait.forPresenceOf(CONNECTIVITY_STATUS)
                    );
                    return CONNECTIVITY_STATUS.resolveFor(actor).getText().equalsIgnoreCase("site connected");
                }
        );
    }
    public static Performable open(){
        return Task.where("{0} opens "+MODAL, actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ADD_CONNECTOR_BUTTON),
                    Click.on(ADD_CONNECTOR_BUTTON),
                    waitPresenceOf(Action_Menu.POPOVER),
                    Click.on(ConnectorUI.ADD_CLOUD_HOSTED),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }
    public static Performable validateSite(String CloudHost, String Site, String Relay){
        return Task.where("{0} validates site Connectivity",actor -> {
            actor.attemptsTo(
            Dropdown.of(CLOUD_HOSTING_PROVIDER).select(CloudHost),
                    Dropdown.of(SITE).select(Site));
            if(Relay == null || Relay.isEmpty()){
                actor.attemptsTo(Dropdown.of(RELAY).select(1));
            }
            else{
                actor.attemptsTo(Dropdown.of(RELAY).select(Relay));
            }
            actor.attemptsTo(
                    testConnectivity(),
                    Modal.next()
            );
        });
    }


}
