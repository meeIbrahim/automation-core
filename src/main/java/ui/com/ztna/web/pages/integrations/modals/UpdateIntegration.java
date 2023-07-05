package ui.com.ztna.web.pages.integrations.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.integrations.IntegrationsUI;

public class UpdateIntegration {
    public static Target NAME = Input.BY_ORDER.of("1").called("Integration Name");

    public static Performable open(Row row){
        return Task.where("{0} opens Update Integration Modal for ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    row.action(IntegrationsUI.UPDATE_INTEGRATION),
                    Modal.waitUntilOpen()
            );
        });
    }

    public  static Performable updateName(String newName){
        return Task.where("{0} fills update Modal for Integration",actor -> {
            actor.attemptsTo(
                    Check.whether(newName.isEmpty()).otherwise(
                            Input.of(NAME).with(newName)
                    )
            );
        });
    }
}
