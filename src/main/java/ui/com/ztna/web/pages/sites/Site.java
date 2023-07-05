package ui.com.ztna.web.pages.sites;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.filters.FilterPage;
import ui.com.ztna.web.common.page.*;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.interactions.withNewTab;

import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;

import ui.com.ztna.web.pages.connectors.ServiceConnectors;
import ui.com.ztna.web.pages.sites.modals.AddAwsSite;
import ui.com.ztna.web.pages.sites.modals.AddAzureSite;
import ui.com.ztna.web.pages.sites.modals.AddGcpSite;
import ui.com.ztna.web.pages.sites.modals.AddSite;
import ui.com.ztna.web.parameters.*;

import java.util.Objects;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ui.com.ztna.web.pages.sites.SiteUi.HEALTHY_SITE;
import static ui.com.ztna.web.pages.sites.SiteUi.RELAY_ATTACHED;


public class Site extends  ResourcePage <SiteParameters> implements FilterPage {

    @Override
    public String url() {
        return "/app/sites";
    }



    Table table = new Table(this,
            "Site Name",
            "Hosting Provider",
            "Cloud Hosted Relays",
            "Service Connectors",
            "ZT Services");

    ResourceUI ui = new SiteUi();
    ResourceQuestions questions =new Questions();

    public Table table(){
        return table;
    }

    @Override
    public ResourceUI ui() {
        return ui;
    }

    @Override
    public ResourceQuestions question() {
        return questions;
    }

    private Performable createOnPremSite(OnPremSiteParameters parameters){
        return Task.where("{0} Creates on prem site", actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddSite.open(),
                                AddSite.fill(parameters),
                                Modal.save()
                        );
                    }
                }, SiteUi.HEALTHY_SITE)));
    }
    public Performable createAwsSite(AwsSiteParameters parameters){
        return Task.where("{0} Creates aws site", actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddSite.open(),
                                AddAwsSite.fill(parameters),
                                Modal.save()
                        );
                    }
                }, SiteUi.HEALTHY_SITE)));
    }
    public Performable createAzureSite(AzureSiteParameters parameters){
        return Task.where("{0} Creates azure site", actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddSite.open(),
                                AddAzureSite.fill(parameters),
                                Modal.save()
                        );
                    }
                }, SiteUi.HEALTHY_SITE)));
    }
    private Performable createGcpSite(GcpSiteParameters parameters){
        return Task.where("{0} Creates gcp site", actor -> actor.attemptsTo(
                openPage(),
                waitForNewResource(new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        actor.attemptsTo(
                                AddSite.open(),
                                AddGcpSite.fill(parameters),
                                Modal.save()
                        );
                    }
                }, SiteUi.HEALTHY_SITE)));
    }

    @Override
    public Performable create(SiteParameters parameters) {
            String hostingProvider = parameters.hostingProvider;
            switch (hostingProvider){
                case OnPremSiteParameters.HOSTING_PROVIDER:
                    return createOnPremSite((OnPremSiteParameters) parameters);
                case AwsSiteParameters.HOSTING_PROVIDER:
                    return createAwsSite((AwsSiteParameters) parameters);
                case GcpSiteParameters.HOSTING_PROVIDER:
                    return createGcpSite((GcpSiteParameters) parameters);
                case AzureSiteParameters.HOSTING_PROVIDER:
                    return createAzureSite((AzureSiteParameters) parameters);
                default:
                    throw new IllegalArgumentException("Site Hosting Provider not valid!");
            }
    }

    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("is Site ROW free".replace("ROW",row.name())).answeredBy(actor -> {
            if(!actor.asksFor(isActive(row))){return false;}
            return actor.asksFor(hasAssociations(row));
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("is Site ROW active".replace("ROW",row.name())).answeredBy(
                actor -> {
                    Boolean active = actor.asksFor(row.contains(HEALTHY_SITE));
                    Boolean enabled = actor.asksFor(row.contains(RELAY_ATTACHED));
                    return active && enabled;
                });
    }

    public Question<ZTAParameters> getActive(List<ZTAParameters> Cached, String host) {
        return Question.about("Active Sites").answeredBy(actor -> {

            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                String Host=resource.getCell(2,actor).getText();
                if (actor.asksFor(isActive(resource)) && Host.equals(host)){
                    if(!Cached.stream()
                            .map(ZTAParameters::getName)
                            .collect(Collectors.toList()).contains(resource.name())) {
                        return actor.asksFor(question().getParameters(resource));
                    }
                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return null;
        });
    }

    @Override
    public Performable removeAssociations(String name){
        return Task.where("{0} removes Association for Site Z".replace("Z",name),actor -> {
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
                                Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Connector")).isSuccess()).andIfSo(
                                        withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Connector")))
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
    public Question<Boolean> hasAssociations(Row row){
        return Question.about("Does resource have child associations").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage(),
                    table().refresh()
            );
            String Connectors = actor.asksFor(row.getCellText(4));
            String Services = actor.asksFor(row.getCellText(5));
            return !Objects.equals(Connectors, "0") || !Objects.equals(Services, "0");
        });
    }





}
