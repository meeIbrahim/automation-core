package ui.com.ztna.web.common.page;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.filters.FilterPage;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;
import ui.com.ztna.web.common.tables.common.ListingImplementor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;

public abstract class ResourcePage <T extends ZTAParameters> implements FilterPage,Resource<T> {


    /// High Level Resource Actions


    public abstract ListingImplementor<Row> table();
    public abstract ResourceUI ui();
    public abstract ResourceQuestions question();

    @Override
    public Performable purge(ZTAParameters parameters){
        return purge(parameters.name);
    }

    public Performable purge(String name){
        return purge(name,Duration.ofMinutes(3).plusSeconds(15));
    }
    public Performable purge(String name,Duration forceDeleteWait){
        return Task.where("{0} purges Resource Z".replace("Z",name),actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Row row = actor.asksFor(table().row(name));
            if (row!=null){
                if (!row.contains(ui().deletionStatus()).answeredBy(actor)){
                    actor.attemptsTo(
                            removeAssociations(name),
                            Check.whether(Modal.isOpen()).andIfSo(Modal.close()),
                            row.action(ui().removeActionMenu()),
                            Modal.save(),
                            table().refresh(),
                            Check.whether(Wait.forQuestion(row.exists())
                                    .untilEqualTo(false)
                                    .isSuccess()).otherwise(
                                    Ensure.thatTheAnswerTo(
                                            Wait.forQuestion(row.contains(ui().deletionStatus()))
                                                    .untilEqualTo(true)
                                                    .forTime(Duration.ofSeconds(2))
                                                    .isSuccess())
                                            .isTrue()
                            )
                    );
                }
                boolean deleted = Wait.forQuestion(table().contains(name))
                        .untilEqualTo(false)
                        .forTime(forceDeleteWait).isSuccess()
                        .answeredBy(actor);
                if (!deleted){
                    int totalActions = actor.asksFor(row.menu().availableActions());
                    actor.attemptsTo(
                            row.action(Action_Menu.ACTION_BUTTON.of(String.valueOf(totalActions)).called("Force Remove Button")),
                            Modal.waitUntilOpen(),
                            Input.of(Input.BY_ORDER.of("1")).with(name),
                            Modal.save());
                }
                actor.attemptsTo(
                        Ensure.thatTheAnswerTo(
                                        Wait.forQuestion(table().contains(name))
                                                .untilEqualTo(false)
                                                .isSuccess())
                                .isTrue()
                );
            }
        });
    }
    @Override
    public Question<ZTAParameters> getFree(List<ZTAParameters> Cached) {
        return Question.about("Free Resource").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isFree(resource))){
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

    public Question<ZTAParameters> getFree() {
        return Question.about("Free Resource").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isFree(resource))){
                        return actor.asksFor(question().getParameters(resource));
                    }
                actor.attemptsTo(
                        openPage()
                );
            }
            return null;
        });
    }

    public Question<ZTAParameters> getActive() {
        return Question.about("Active Resource").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isActive(resource))){
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
    public Question<ZTAParameters> getActive(List<ZTAParameters> Cached) {
        return Question.about("Active Resource").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isActive(resource))){
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
    public Question<List<ZTAParameters>> getAllFree() {
        return Question.about("All Free Resources").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            ArrayList<ZTAParameters> resources = new ArrayList<>();
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isFree(resource))){
                    resources.add(actor.asksFor(question().getParameters(resource)));
                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return resources;
        });
    }

    @Override
    public Question<List<ZTAParameters>> getAllActive() {
        return Question.about("All Active Resources").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            ArrayList<ZTAParameters> resources = new ArrayList<>();
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (actor.asksFor(isActive(resource))){
                    resources.add(actor.asksFor(question().getParameters(resource)));
                }
                actor.attemptsTo(
                        openPage()
                );
            }
            return resources;
        });
    }

    @Override
    public Question<List<ZTAParameters>> getAll() {
        return Question.about("All Resources").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            ArrayList<ZTAParameters> resources = new ArrayList<>();
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                resources.add(actor.asksFor(question().getParameters(resource)));
            }
            return resources;
        });
    }

    public abstract Question<Boolean> isFree(Row row);
    public abstract Question<Boolean> isActive(Row row);

    @Override
    public Performable purge(){
        return Task.where("{0} Purging all resources on Page: " + url(),actor -> {
            actor.attemptsTo(
                    PageLoad.waitFor(),
                    waitNotPresenceOf(Page.LOADER)
            );
            Iterator<Row> rows = table().rowIterator().answeredBy(actor);
            while (rows.hasNext()){
                Row row = rows.next();
                actor.attemptsTo(
                        purge(row.name())
                );
            }
        });
    }
    @Override
    public Performable removeAssociations(ZTAParameters parameters){
        return removeAssociations(parameters.name);
    }
    public Performable removeAssociations(String name){
        return null;
    }

    @Override
    public Question<Boolean> hasAssociations(ZTAParameters parameters){
        return Question.about("Does resource have child associations").answeredBy(actor -> {
            Row row = actor.asksFor(table().row(parameters.name));
            return hasAssociations(row).answeredBy(actor);
        });
    }

    public Question<Boolean> hasAssociations(Row resource){
        return Question.about("Does resource have child associations").answeredBy(
                actor -> {
                    boolean isOpen = false;
                    if (Modal.isOpen().answeredBy(actor)) {
                        String title = actor.asksFor(Modal.title()).toLowerCase();
                        if (!title.contains("remove")){
                            actor.attemptsTo(
                                    Modal.close()
                            );
                        }
                        else {
                            isOpen = true;
                        }
                    }
                    if (!isOpen) {
                        actor.attemptsTo(
                                resource.menu().open()
                        );
                        if (!actor.asksFor(resource.menu().isEnabled(ui().removeActionMenu()))) {
                            return true;
                        }
                        actor.attemptsTo(
                                resource.action(ui().removeActionMenu()),
                                Modal.waitUntilOpen()
                        );
                    }

//                    if(Modal.MODAL_FOOTER_BUTTONS.resolveAllFor(actor).size() >= 2
//                    &&
//                    ResourceUI.ASSOCIATIONS.resolveAllFor(actor).isEmpty()){
//                        actor.attemptsTo(Modal.close());
//                        return false;
//                    }
                    if(!ResourceUI.ASSOCIATIONS.resolveAllFor(actor).isEmpty()){
                        return true;
                    }
                    actor.attemptsTo(Modal.close());
                    return false;
                }
        );
    }

    protected Performable waitForNewResource(Performable creationTask,Target... ACTIVE){
         return waitForNewResource(creationTask,200,ACTIVE);
    }
    protected Performable waitForNewResource(Performable creationTask,Integer timeout,Target... ACTIVE){
        return Task.where("{0} Creates new Resource",actor -> {
            LinkedHashSet<Row> oldRows = actor.asksFor(table().rows());
            actor.attemptsTo(creationTask);

            /// Poll for new Row
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            org.openqa.selenium.support.ui.Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(18)) // Set to 12 since page polling for ZTA is 10 seconds
                    .pollingEvery(Duration.ofSeconds(1));
            try {
                wait.until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        LinkedHashSet<Row> newRows = actor.asksFor(table().rows());
                        return (newRows.size() > oldRows.size());
                    }
                });}
            catch (Exception ignored){
            }
            LinkedHashSet<Row> newRows = actor.asksFor(table().rows());
            newRows.removeAll(oldRows);
            actor.attemptsTo(
                    Ensure.that(newRows.size()).isEqualTo(1)
            );
            Row resource = newRows.iterator().next();
            actor.attemptsTo(table().navigateTo(resource));
            for(Target matcher: ACTIVE) {
                actor.attemptsTo(
                        Ensure.thatTheAnswerTo(
                                Wait.forQuestion(resource.contains(matcher))
                                        .forTime(Duration.ofSeconds(timeout))
                                        .untilEqualTo(true)
                                        .isSuccess()
                        ).isTrue()
                );
            }
        });
    }

}
