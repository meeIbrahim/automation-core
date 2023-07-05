package ui.com.ztna.web.pages.cloud.relays;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.filters.FilterPage;
import ui.com.ztna.web.common.input.Toggle;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.cloud.relays.modals.AddRelay;
import ui.com.ztna.web.pages.cloud.relays.modals.ManageHA;
import ui.com.ztna.web.pages.connectors.ServiceConnectors;
import ui.com.ztna.web.parameters.RelayParameters;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CloudHostedRelays extends ResourcePage<RelayParameters> implements FilterPage {

    Table table = new Table(this,
            "Cloud Relays",
            "Name",
            "Status",
            "Region", "HA");

    ResourceUI ui = new RelayUI();
    Questions questions = new Questions();
    public Table table(){
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


    @Override
    public Performable create(RelayParameters parameters) {
        return Task.where("{0} Creates Relay",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddRelay.open(),
                                    AddRelay.fill(parameters.region,parameters.mtu,parameters.ha),
                                    Modal.save()
                            );
                        }
                    },900,RelayUI.RELAY_STATUS_ENABLED
                            ,RelayUI.ACTIVE_RELAY
                            ));
            Row relay = table().row(1).answeredBy(actor);
            parameters.name = relay.name();
        });
    }
    public Performable enableHA(ZTAParameters relay){
        return toggleHA(relay,true);
    }
    public Performable disableHA(ZTAParameters relay){
        return toggleHA(relay,false);
    }
    public Performable toggleHA(ZTAParameters relay,Boolean state){
        return Task.where("{0} toggle HA for Relay Z".replace("Z",relay.name),actor -> {
            actor.attemptsTo(
                    openPage(),
                    Filters.NAME.with(relay.name).apply()
            );
            Row relayRow = actor.asksFor(table().row(relay.name));
            actor.attemptsTo(
                    relayRow.action(RelayUI.MANAGE_HA_ACTION_MENU),
                    Modal.waitUntilOpen(),
                    Check.whether(state).andIfSo(
                            Toggle.of(ManageHA.HA).disable() /// Toggle is inverted for HA
                    ).otherwise(
                            Toggle.of(ManageHA.HA).enable()  /// Toggle is inverted for HA
                    ),
                    Modal.save(),
                    Ensure.thatTheAnswerTo(Wait.forQuestion(
                                    Questions.isHA(relayRow)).untilEqualTo(state)
                            .forTime(Duration.ofSeconds(10)).isSuccess()).isTrue(),
                    Wait.forQuestion(relayRow.contains(RelayUI.RELAY_STATUS_IN_PROGRESS))
                            .untilEqualTo(true)
                            .forTime(Duration.ofSeconds(5)),
                    Wait.forQuestion(relayRow.contains(ui().enabledStatus()))
                            .untilEqualTo(true)
                            .forTime(Duration.ofMinutes(3)));
        });
    }

    public Question<ZTAParameters> getFree(Boolean isHA,List<ZTAParameters> Cached){
        return Question.about("Free Relay").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            HashSet<Row> activeRows = actor.asksFor(table().rows(RelayUI.ACTIVE_RELAY));
            HashSet<Row> enabledRows = actor.asksFor(table().rows("Status","Enabled"));
            activeRows.retainAll(enabledRows);
            for(Row relay: activeRows){
                if (!actor.asksFor(hasAssociations(relay))) {
                    if (isHA == null || isHA == Questions.isHA(relay).answeredBy(actor)) {
                        if (!Cached.stream()
                                .map(ZTAParameters::getName)
                                .collect(Collectors.toList())
                                .contains(relay.name()))
                        {return actor.asksFor(question().getParameters(relay));}
                    }
                }
            }
            return null;
        });
    }


    @Override
    public Question<ZTAParameters> getFree(List<ZTAParameters> Cached) {
        return getFree(false,Cached);
    }



    public Question<ZTAParameters> getActive(Boolean isHA,List<ZTAParameters> Cached) {
        return Question.about("Active Relay").answeredBy(
                actor -> {
                    actor.attemptsTo(
                            openPage());
                    HashSet<Row> rows = actor.asksFor(table().rows(RelayUI.ACTIVE_RELAY));
                    for(Row relay:rows){
                        if (actor.asksFor(relay.contains(RelayUI.RELAY_STATUS_ENABLED))){
                            if (isHA == null || isHA == Questions.isHA(relay).answeredBy(actor)) {
                                if (!Cached.stream()
                                        .map(ZTAParameters::getName)
                                        .collect(Collectors.toList())
                                        .contains(relay.name()))
                                {return actor.asksFor(question().getParameters(relay));}
                            }
                        }
                    }
                    return null;
                }
        );

    }
    @Override
    public Question<ZTAParameters> getActive(List<ZTAParameters> Cached) {
        return getActive(null,Cached);
    }

    @Override
    public Question<Boolean> isFree(Row row) {
        return null;
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return null;
    }

    @Override
    public Performable removeAssociations(String name){
        return Task.where("{0} removes Association for Relay Z".replace("Z",name),actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Row row = actor.asksFor(table().row(name));
            if (row != null){
                ServiceConnectors connector = new ServiceConnectors();
                actor.attemptsTo(
                        row.action(ui().removeActionMenu()),
                        Modal.waitUntilOpen(),
                        Check.whether(Wait.forPresenceOf(ResourceUI.ASSOCIATIONS).isSuccess()).andIfSo(
                                Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Connectors")).isSuccess()).andIfSo(
                                        withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Connectors")))
                                                .perform(connector.purge())
                                ),
                                Modal.close()
                        ),
                        Ensure.thatTheAnswerTo(hasAssociations(row)).isFalse()
                );
            }
        });
    }
    @Override
    public Performable purge(String name){
        return purge(name, Duration.ofMinutes(6).plusSeconds(15));
    }
    @Override
    public String url() {
        return "/app/relay-nodes";
    }



}
