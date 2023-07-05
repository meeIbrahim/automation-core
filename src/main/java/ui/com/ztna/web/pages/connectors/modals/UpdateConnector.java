package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.connectors.ConnectorUI;

public class UpdateConnector {
    public static Target NAME = Input.BY_ORDER.of("1").called("Connector Name");
    public static Target RELAY = Dropdown.BY_ORDER.of("1").called("Relay");
    public static Performable open(Row row){
        return Task.where("{0} opens Update Connector Modal for ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    row.action(ConnectorUI.UPDATE_ACTION_MENU),
                    Modal.waitUntilOpen()
            );
        });
    }

    public  static Performable fill(String Name, String relay){
        return Task.where("{0} fills update Modal for Connector",actor -> {
            actor.attemptsTo(
                    Check.whether(Name.isEmpty()).otherwise(
                            Input.of(NAME).with(Name)
                    ),
                    Check.whether(relay.isEmpty()).otherwise(
                            Dropdown.of(RELAY).select(relay)
                    )
            );
        });
    }
}
