package ui.com.ztna.web.pages.applicationGroups;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.applicationGroups.modals.AddApplicationGroup;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;

import java.util.List;

import static ui.com.ztna.web.pages.applicationGroups.Questions.enabledValues;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class ApplicationGroups extends ResourcePage<ApplicationGroupParameters> {


    Table table = new Table(this,
            "Application Groups",
            "Name",
            "No of Applications",
            "Description");

    ResourceUI ui = new ApplicationGroupsUI();
    ResourceQuestions questions = new Questions();
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

    @Override
    public Performable create(ApplicationGroupParameters parameters) {
        return Task.where("{0} Creates Application Group", actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddApplicationGroup.open(),
                                    AddApplicationGroup.fill(parameters),
                                    Modal.save()
                            );
                        }
                    }));
        });
    }




    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Application Group ROW Free".replace("ROW",row.name())).answeredBy(actor -> {
            if(!actor.asksFor(isActive(row))){return false;}
            return !actor.asksFor(hasAssociations(row));
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Application Group ROW active".replace("ROW",row.name())).answeredBy(actor -> true);
    }

    @Override
    public Performable removeAssociations(String name) {
        return Task.where("{0} removes Association for Project Z".replace("Z", name), actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Policies policies = new Policies();
            Row row = actor.asksFor(table().row(name));
            if (row != null) {
                actor.attemptsTo(
                        row.action(ui().removeActionMenu()),
                        Modal.waitUntilOpen(),
                        Check.whether(Visibility.of(ResourceUI.ASSOCIATIONS).asABoolean()).andIfSo(
                                Check.whether(Visibility.of(ResourceUI.ASSOCIATION.of("Policies")).asABoolean())
                                .andIfSo(
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
        return "/app/projects";
    }
}
