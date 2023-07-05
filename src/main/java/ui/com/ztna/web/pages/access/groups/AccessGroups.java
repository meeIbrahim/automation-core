package ui.com.ztna.web.pages.access.groups;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.interactions.withNewTab;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.access.groups.modals.CreateGroup;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.parameters.AGParameters;

import java.util.Iterator;

import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;
import static ui.com.ztna.web.pages.access.groups.AccessGroupsUI.UPDATE_ACTION_MENU;
import static ui.com.ztna.web.pages.access.groups.modals.CreateGroup.USERS;

public class AccessGroups extends ResourcePage<AGParameters> {

    Table table = new Table(this,
            "Access Groups",
            "Name",
            "Users", "Description");
    ResourceUI ui = new AccessGroupsUI();
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

    @Override
    public Performable create(AGParameters parameters) {

        return Task.where("{0} Creates Access Group",actor -> {
            actor.attemptsTo(
            openPage(),
            waitForNewResource(new Performable() {
                @Override
                public <T extends Actor> void performAs(T actor) {
                    actor.attemptsTo(
                            CreateGroup.open(),
                            CreateGroup.fill(parameters.name,parameters.description,parameters.users.toArray(String[]::new)),
                            Modal.save()
                    );
                }
            })
            );
        });
    }


    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is ROW Free".replace("ROW",row.name())).answeredBy(actor -> !actor.asksFor(hasAssociations(row)));
    }

    public Question<Boolean> isEmpty(Row row) {
        return Question.about("Is AccessGroup empty").answeredBy(actor ->{
            row.action(UPDATE_ACTION_MENU).performAs(actor);
            boolean empty= MultiSelect.of(USERS).selectedValues().answeredBy(actor).isEmpty();
            actor.attemptsTo(clickButton(Modal.MODAL_CLOSE_BUTTON));
            return empty;

        } );
    }

    @Override
    public Question<ZTAParameters> getFree() {
        return Question.about("Free Resource").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isFree(resource)) && actor.asksFor(isEmpty(resource))){
                    return actor.asksFor(question().getParameters(resource));
                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return null;
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is ROW Active".replace("ROW",row.name())).answeredBy(actor -> true);
    }

    @Override
    public Performable removeAssociations(String name) {
        return Task.where("{0} removes Association for Access Group Z".replace("Z", name), actor -> {
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
        return "/app/access-groups";
    }
}
