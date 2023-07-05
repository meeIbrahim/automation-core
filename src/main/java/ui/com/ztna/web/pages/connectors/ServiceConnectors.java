package ui.com.ztna.web.pages.connectors;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;

import net.serenitybdd.screenplay.targets.Target;

import resource.cached.ZTAParameters;
import ui.com.ztna.vm.service.connector.ConnectorVM;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.filters.FilterPage;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.pages.connectors.modals.AddCloudConnector;
import ui.com.ztna.web.pages.connectors.modals.AddPrivateConnector;
import ui.com.ztna.web.pages.connectors.modals.AddAwsConnector;
import ui.com.ztna.web.pages.connectors.modals.AddAzureConnector;
import ui.com.ztna.web.pages.connectors.modals.AddGcpConnector;
import ui.com.ztna.web.pages.services.tab.remote.desktop.remoteDesktop;
import ui.com.ztna.web.pages.services.tab.secure.shell.secureShell;
import ui.com.ztna.web.pages.services.tab.web.webApp;
import ui.com.ztna.web.parameters.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ServiceConnectors extends ResourcePage<ConnectorParameters> implements FilterPage {
    public final Target UPDATE_CONNECTOR = Action_Menu.ACTION_BUTTON.of("1").called("Update Connector");
    public final Target MANAGE_ACCESS = Action_Menu.ACTION_BUTTON.of("2").called("Manage Access");
    public final Target MANAGE_STATE = Action_Menu.ACTION_BUTTON.of("3").called("Manage State");
    public final Target REMOVE_CONNECTOR = Action_Menu.ACTION_BUTTON.of("4").called("Remove Connector");
    Table table = new Table(this,
            "Service Connectors",
            "Name",
            "Status",
            "Relay",
            "Site", "Applications");
ResourceUI ui = new ConnectorUI();
Questions questions = new Questions();
    @Override
    public Table table() {
        return table;
    }

    @Override
    public ResourceUI ui() {
        return ui;
    }

    @Override
    public Questions question() {
        return questions;
    }

    private Performable createPrivateConnector(PrivateConnectorParameters parameters){

        return Task.where("{0} Creates Private Connector",actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <S extends Actor> void performAs(S actor) {
                        actor.attemptsTo(
                                AddPrivateConnector.open(),
                                AddPrivateConnector.configure(parameters.name,parameters.site,parameters.relay));
                        String command = actor.recall("command");
                        actor.attemptsTo(
                                ConnectorVM.install(command,parameters),
//                                    Modal.save() //One button only in Modal, save crashes
                                Modal.close()
                        );
                    }
                }, ConnectorUI.CONNECTOR_STATUS_ENABLED,ConnectorUI.ACTIVE_CONNECTOR)
        ));
    }
    private Performable createAwsConnector(AwsConnectorParameters parameters){

        return Task.where("{0} Creates AWS Connector",actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddCloudConnector.open(),
                                AddAwsConnector.fill(parameters)
                        );
                    }
                },1200, ConnectorUI.CONNECTOR_STATUS_ENABLED,ConnectorUI.ACTIVE_CONNECTOR)
        ));
    }
    private Performable createAzureConnector(AzureConnectorParameters parameters){
        return Task.where("{0} Creates Azure Connector",actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddCloudConnector.open(),
                                AddAzureConnector.fill(parameters)

                        );
                    }
                },1200, ConnectorUI.CONNECTOR_STATUS_ENABLED,ConnectorUI.ACTIVE_CONNECTOR)
        ));
    }
    private Performable createGcpConnector(GcpConnectorParameters parameters){
        return Task.where("{0} Creates GCP Connector",actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddCloudConnector.open(),
                                AddGcpConnector.fill(parameters)
                        );
                    }
                },1200, ConnectorUI.CONNECTOR_STATUS_ENABLED,ConnectorUI.ACTIVE_CONNECTOR)
        ));
    }

    public Question<ZTAParameters> getFree(List<ZTAParameters> Cached,String hostingProvider){
        return Question.about("Free Connector").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> rowIterator = table().rowIterator().answeredBy(actor);
            while(rowIterator.hasNext()){
                Row connector = rowIterator.next();
                if (actor.asksFor(isFree(connector)) &&
                        actor.asksFor(question().hostingProvider(connector)).equalsIgnoreCase(hostingProvider)){
                    if(!Cached.stream()
                            .map(ZTAParameters::getName)
                            .collect(Collectors.toList()).contains(connector.name())) {
                        return actor.asksFor(question().getParameters(connector));
                    }                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return null;
        });
    }
    public Question<ZTAParameters> getActive(List<ZTAParameters> Cached,String hostingProvider){
        return Question.about("Free Connector").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> rowIterator = table().rowIterator().answeredBy(actor);
            while(rowIterator.hasNext()){
                Row connector = rowIterator.next();
                if (actor.asksFor(isActive(connector)) &&
                        actor.asksFor(question().hostingProvider(connector)).equalsIgnoreCase(hostingProvider)){
                    if(!Cached.stream()
                            .map(ZTAParameters::getName)
                            .collect(Collectors.toList()).contains(connector.name())) {
                        return actor.asksFor(question().getParameters(connector));
                    }                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return null;
        });
    }
    @Override
    public Performable create(ConnectorParameters parameters) {
        String hostingProvider = parameters.hostingProvider;
        switch (hostingProvider){
            case PrivateConnectorParameters.HOSTING_PROVIDER:
                return createPrivateConnector((PrivateConnectorParameters) parameters);
            case AwsConnectorParameters.HOSTING_PROVIDER:
                return createAwsConnector((AwsConnectorParameters)  parameters);
            case AzureSiteParameters.HOSTING_PROVIDER:
                return createAzureConnector((AzureConnectorParameters) parameters);
            case GcpConnectorParameters.HOSTING_PROVIDER:
                return createGcpConnector((GcpConnectorParameters) parameters);
            default:
                throw new IllegalArgumentException("Connector Provider not Valid!");
        }
    }


    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Connector ROW Free".replace("ROW",row.name())).answeredBy(actor -> {
            if (!actor.asksFor(isActive(row))){return false;}
            return !hasAssociations(row).answeredBy(actor);
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Connector ROW Active".replace("ROW",row.name())).answeredBy(actor -> {
            Boolean isActive = row.contains(ConnectorUI.ACTIVE_CONNECTOR).answeredBy(actor);
            Boolean isEnabled = row.contains(ConnectorUI.CONNECTOR_STATUS_ENABLED).answeredBy(actor);
            return (isActive && isEnabled);
        });
    }

    @Override
    public Performable removeAssociations(String name){
        return Task.where("{0} removes Association for Connector Z".replace("Z",name),actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Row row = actor.asksFor(table().row(name));
            if (row != null){
                secureShell shell = new secureShell();
                remoteDesktop remote = new remoteDesktop();
                webApp web = new webApp();
                actor.attemptsTo(
                            row.action(ui().removeActionMenu()),
                                Modal.waitUntilOpen(),
                                Check.whether(Wait.forPresenceOf(ResourceUI.ASSOCIATIONS).isSuccess()).andIfSo(
                                    Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Shell")).isSuccess()).andIfSo(
                                            withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Shell")))
                                                    .perform(shell.purge())
                                    ),
                                    Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Web")).isSuccess()).andIfSo(
                                            withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Web")))
                                                    .perform(web.purge())
                                    ),
                                    Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Remote")).isSuccess()).andIfSo(
                                            withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Remote")))
                                                    .perform(remote.purge())
                                    ),
                                    Modal.close()
                            ),
                        Ensure.thatTheAnswerTo(hasAssociations(row)).isFalse()
                );
            }
        });
    }

    @Override
    public Question<Boolean> hasAssociations(Row row){
        return Question.about("Does resource have child associations").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            String Applications  = actor.asksFor(row.getCellText(6));
            if (Objects.equals(Applications, "0")){
                return false;
            }
            else {
                return true;
            }
        });
    }
    @Override
    public String url() {
        return "/app/service-connectors";
    }
}
