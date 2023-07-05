package ui.com.ztna.web.pages.connectors.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.data.FindData;
import resource.data.UnknownResource;
import resource.data.parameters.file.ConnectorJSON;
import ui.com.ztna.vm.service.connector.ConnectorVM;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.connectors.ConnectorUI;
import ui.com.ztna.web.pages.connectors.Questions;
import ui.com.ztna.web.parameters.PrivateConnectorParameters;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

public class ManageAccess {
    public static Performable open(Row row){
        return Task.where("{0} open manage access modal of Connector ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    row.action(ConnectorUI.MANAGE_ACCESS_ACTION_MENU),
                    Modal.waitUntilOpen()
            );
        });
    }

    public static Performable revokeAccess(Row row){
        return Task.where("{0} revokes access of Connector ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    Check.whether(Questions.isRevoked(row)).otherwise(
                            open(row),
                            Modal.cancel()  /// Single Button
                    ),
                    Ensure.thatTheAnswerTo(Wait.forQuestion(Questions.isRevoked(row))
                                    .forTime(Duration.ofSeconds(15))
                                    .untilEqualTo(true)
                            .isSuccess()).isTrue()
            );
        });
    }

    public static Performable regenerateAccess(Row row){
        return Task.where("{0} regenerates access of Connector ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    Check.whether(Questions.isRevoked(row)).andIfSo(
                            open(row),
                            Modal.previous(), /// Single Button
                            Wait.forInvisibilityOf(Modal.MODAL_LOAD_ICON).forTime(Duration.ofSeconds(10)),
                            Wait.forPresenceOf(AddPrivateConnector.COMMAND_COPY_BUTTON).forTime(Duration.ofSeconds(5)),
                            Click.on(AddPrivateConnector.COMMAND_COPY_BUTTON),
                            Modal.close()
                    ));
            String command = "";
            try {
                command = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                throw new RuntimeException(e);
            }
            ZTAParameters parameters = ZTA.serviceConnectors().question().getParameters(row).answeredBy(actor);
            PrivateConnectorParameters connectorParameters = null;
            try {
                ConnectorJSON json = FindData.lookup(parameters.identifier, PrivateConnectorParameters.class);
                connectorParameters = new PrivateConnectorParameters(parameters.name, parameters.parent, "", json.user, json.host, json.port,json.pem);
            } catch (UnknownResource e) {
                throw new RuntimeException(e);
            }
            System.out.println(command);
            actor.attemptsTo(
                    ConnectorVM.regenerate(command,connectorParameters),
                    Ensure.thatTheAnswerTo(
                            Wait.forQuestion(ZTA.serviceConnectors().isActive(row))
                                    .forTime(Duration.ofMinutes(4))
                                    .untilEqualTo(true)
                                    .isSuccess()
                    ).isTrue()
            );
        });
    }
}
