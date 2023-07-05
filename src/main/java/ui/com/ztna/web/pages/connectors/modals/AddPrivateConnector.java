package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.pages.connectors.ConnectorUI;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;
import static ui.com.ztna.web.pages.connectors.ConnectorUI.ADD_CONNECTOR_BUTTON;

public class AddPrivateConnector {
    static String MODAL = "Add Private Cloud Hosted Relay";
    public static Target NAME = Input.BY_ORDER.of("1").called("Connector Name");
    public static Target SITE = Dropdown.BY_ORDER.of("1").called("Associated Site");
    public static Target RELAY = Dropdown.BY_ORDER.of("2").called("Associated Cloud Relay");
    public static final Target COMMAND_COPY_BUTTON = Target.the("Command Copy Button")
            .locatedBy("//span[@class='MuiButton-label'][.='Copy']");

    public static Performable open(){
        return Task.where("{0} opens "+MODAL, actor -> {
            actor.attemptsTo(
                    waitPresenceOf(ADD_CONNECTOR_BUTTON),
                    Click.on(ADD_CONNECTOR_BUTTON),
                    waitPresenceOf(Action_Menu.POPOVER),
                    Click.on(ConnectorUI.ADD_PRIVATE_HOSTED),
                    waitPresenceOf(Modal.MODAL_BOX)
            );
        });
    }

    public static Performable configure(String Name, String Site, String Relay){
        return Task.where("{0} configures Connector with Data",actor -> {
            actor.attemptsTo(
                    Modal.next(),
                    Input.of(NAME).with(Name),
                    Dropdown.of(SITE).select(Site),
                    Check.whether(Objects.equals(Relay, "")).otherwise(
                            Dropdown.of(RELAY).select(Relay)
                    ),
                    Modal.next(), /// Add Data Assertion
                    Modal.next(),
                    Wait.forPresenceOf(COMMAND_COPY_BUTTON).forTime(Duration.ofSeconds(5)),
                    Click.on(COMMAND_COPY_BUTTON)
            );
            String command = "";
            try {
                command = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                throw new RuntimeException(e);
            }
            actor.remember("command",command);
        });
    }
}
