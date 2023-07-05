package ui.com.ztna.web.pages.services;


import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.filters.FilterPage;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.common.tables.common.ListingImplementor;
import ui.com.ztna.web.pages.applicationGroups.ApplicationGroups;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.pages.services.modals.AddService;
import ui.com.ztna.web.parameters.ServiceParameter;


/***
 *   Service Points to Web App by default
 */
public abstract class Services <T extends ServiceParameter> extends ResourcePage<T> implements FilterPage {




    Table table = new Table(this,
            "Web",
            "Application Name",
            "Status",
            "Application Type",
            "Application URL", "Associated Service Connector");

    ResourceUI ui = new ServicesUI();
    ResourceQuestions questions = new Questions();

    public abstract Question<Boolean> testConnectivity(T parameters);

    /// Used on Connectivity page to attempt Connectivity Test multiple times
    public Performable checkConnectivity(){
        final int[] attempts = {3};
        return Task.where("{0} checks connectivity Z times".replace("Z",String.valueOf(attempts[0])), actor -> {
            while (attempts[0] > 0){
                actor.attemptsTo(
                        Click.on(AddService.CONNECTIVITY_BUTTON)
                );
                if(actor.asksFor(AddService.connectivityStatus())){break;}
                attempts[0]--;
            }
        }).withNoReporting();
    }
    @Override
    public ListingImplementor<Row> table() {
        return table;
    }

    @Override
    public ResourceUI ui(){
        return ui;
    }

    @Override
    public ResourceQuestions question(){
        return questions;
    }



    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Service: ROW free".replace("ROW",row.name())).answeredBy(actor -> {
            if(!isActive(row).answeredBy(actor)){return false;}
            return !actor.asksFor(hasAssociations(row));
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Service: ROW active".replace("ROW",row.name())).answeredBy(actor -> {
            Boolean isHealthy = row.contains(ServicesUI.ACTIVE_SERVICE).answeredBy(actor);
            Boolean isActive =  actor.asksFor(row.contains(ServicesUI.SERVICE_STATUS.of("Active")));
            return (isActive && isHealthy);
        });
    }

    @Override
    public Performable removeAssociations(String name) {
        return Task.where("{0} removes Association for Service Z".replace("Z", name), actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Policies policies = new Policies();
            ApplicationGroups ag = new ApplicationGroups();
            Row row = actor.asksFor(table().row(name));
            if (row != null) {
                actor.attemptsTo(
                        row.action(ui().removeActionMenu()),
                        Modal.waitUntilOpen(),
                        Check.whether(Wait.forPresenceOf(ResourceUI.ASSOCIATIONS).isSuccess()).andIfSo(
                                    Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Group")).isSuccess()).andIfSo(
                                            withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Group")))
                                                    .perform(ag.purge())
                                    ),
                                    Check.whether(Wait.forVisibilityOf(ResourceUI.ASSOCIATION.of("Policies")).isSuccess()).andIfSo(
                                            withNewTab.open(Click.on(ResourceUI.ASSOCIATION.of("Policies")))
                                                    .perform(policies.purge())

                                    ),
                                        Modal.close()
                                        ),
                        Ensure.thatTheAnswerTo(hasAssociations(row)).isFalse()
                );
            }
        });
    }

}
