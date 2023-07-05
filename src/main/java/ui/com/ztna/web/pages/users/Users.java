package ui.com.ztna.web.pages.users;

import api.zta.Request;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.data.Data;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.access.groups.AccessGroups;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.pages.users.modals.InviteUsers;
import ui.com.ztna.web.parameters.UserParameters;

import java.time.Duration;

public class Users extends ResourcePage<UserParameters> {


    public Table table = new Table(this,
            "Users",
            "Username",
            "Linked Devices",
            "Your Current Location",
            "Status",
            "Authenticators", "Last Logged In At");
    ResourceUI ui  = new UsersUI();
    ResourceQuestions questions = new Questions();


    @Override
    public Table table() {
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

    public Performable invite(UserParameters parameters){
        return Task.where("{0} invite User",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    InviteUsers.open(),
                                    InviteUsers.fill(parameters.access_group,parameters.email),
                                    Modal.save(),
                                    Filters.STATUS_PENDING.apply()
                            );
                        }
                    },UsersUI.USER_STATUS_PENDING),
                    clearFilters()
            );
        });
    }

    public Performable registerLocal(UserParameters parameters){
        return Task.where("{0} Register User",actor -> {
            String FirstName = Data.generateFirstName();
            String LastName = "Automation-User";
            actor.attemptsTo(
                    openPage(),
                    Filters.USERNAME.with(parameters.email).apply(),
                    Request.signUp(parameters.email,FirstName,LastName,parameters.password)
            );
            Row row = actor.asksFor(table().row(parameters.email));
            actor.attemptsTo(
                    Wait.forQuestion(row.contains(UsersUI.USER_STATUS_ACTIVE)).forTime(Duration.ofSeconds(100))
            );
        });
    }
    public Performable registerGSuite(UserParameters parameters){
        return null;
    }
    public Performable registerMicrosoft(UserParameters parameters){
        return null;
    }

    @Override
    public Performable create(UserParameters parameters) {
        return Task.where("{0} adds Users",actor ->{
            actor.attemptsTo(
                    invite(parameters)
            );
            switch (parameters.type.toLowerCase()){
                case "google":
                    actor.attemptsTo(registerGSuite(parameters));
                    break;
                case "microsoft":
                    actor.attemptsTo(registerMicrosoft(parameters));
                default:
                    actor.attemptsTo(registerLocal(parameters));
            }
        });
    }


    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("is User ROW active".replace("ROW", row.name())).answeredBy(
                actor -> {
                    if (actor.asksFor(isActive(row))){
                        return !actor.asksFor(hasAssociations(row));
                    }
                    return false;
                }
        );
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("is User ROW active".replace("ROW", row.name())).answeredBy(
                actor -> actor.asksFor(row.contains(UsersUI.USER_STATUS_ACTIVE)));
    }

    @Override
    public Performable removeAssociations(String name) {
        return Task.where("{0} removes Association for User Z".replace("Z", name), actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Policies policies = new Policies();
            AccessGroups ag = new AccessGroups();
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


    @Override
    public String url() {
        return "/app/users";
    }
}
