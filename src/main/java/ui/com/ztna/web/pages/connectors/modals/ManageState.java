package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.connectors.ConnectorUI;
import ui.com.ztna.web.pages.connectors.Questions;

import java.time.Duration;

public class ManageState {
    public static Performable open(Row row){
        return Task.where("{0} opens Manage State Modal for ROW".replace("ROW",row.name()), actor -> {
            actor.attemptsTo(
                    row.action(ConnectorUI.MANAGE_STATE_ACTION_MENU),
                    Modal.waitUntilOpen()
            );
        });
    }

    public static Performable toggleState(Row row){
        return Task.where("{0} toggle State for Service Connector  ROW".replace("ROW",row.name()),actor -> {
            Boolean isDisabled = actor.asksFor(Questions.isDisabled(row));
            actor.attemptsTo(
                    open(row),
                    Modal.save(),
                    Wait.forInvisibilityOf(Modal.MODAL_BOX),
                    Ensure.thatTheAnswerTo(
                            Wait.forQuestion(Questions.isDisabled(row))
                                    .forTime(Duration.ofSeconds(60))
                                    .untilEqualTo(!isDisabled)
                                    .isSuccess()).isTrue()
            );
        });
    }
    public static Performable enableConnector(Row row){
        return Task.where("{0} Enable Service Connector  ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    Check.whether(Questions.isDisabled(row)).andIfSo(
                            toggleState(row)
                    )
            );
        });
    }
    public static Performable disableConnector(Row row){
        return Task.where("{0} Disable Service Connector  ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                Check.whether(Questions.isDisabled(row)).otherwise(
                        toggleState(row)
                )
            );
        });
    }
}
