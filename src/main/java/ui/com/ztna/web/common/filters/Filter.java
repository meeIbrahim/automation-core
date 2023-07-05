package ui.com.ztna.web.common.filters;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Hit;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.Keys;
import ui.com.ztna.web.common.Wait.Wait;



import java.util.ArrayList;
import java.util.List;


import static ui.com.ztna.web.common.filters.FilterUI.*;

public class Filter {

    public static Query by(String Filter){
        return new Query(Filter);
    }

    public static BulkFilters using(Query... Filters){
        return new BulkFilters(Filters);
    }

    public static Performable open(){
        return Task.where("User opens Filter Dropdown", actor -> {
            actor.attemptsTo(
                    Check.whether(isOpen()).otherwise(
                            Click.on(BAR)
                    ),
                    Ensure.thatTheAnswerTo(isOpen()).isTrue()
            );
        }).withNoReporting();
    }
    public static Performable close(){
        return Task.where("User closes Filter Dropdown",actor -> {
            actor.attemptsTo(
                    Check.whether(isOpen()).andIfSo(
                            Click.on(BAR)
                    ),
                    Ensure.thatTheAnswerTo(isOpen()).isFalse()
            );
        }).withNoReporting();
    }

    public static Question<List<Query>> applied(){
        return Question.about("applied filters").answeredBy(actor -> {
            List<WebElementFacade> filters = FILTERS.resolveAllFor(actor);
            ArrayList<Query> queries = new ArrayList<>();
            for (WebElementFacade filter : filters){
                Query query = Filter.by(filter.getText());
                query.syncAppliedValue(actor);
                queries.add(query);
            }
            return queries;
        });
    }

    /// To Add: Clear All Button
    public static Performable clear(){
        return Task.where("{0} clears Filter",actor -> {
            List<Query> queries = actor.asksFor(applied());
            for (Query query : queries){
                actor.attemptsTo(
                        query.clear()
                );
            }
        });
    }

    public static Question<Boolean> isOpen(){
        return Question.about("Is Filter Dropdown Open").answeredBy(actor -> {
            List<WebElementFacade> elements = LIST_BOX.resolveAllFor(actor);
            return !elements.isEmpty();
        });
    }
    public static Question<Query> isApplied(String filter){
        return Question.about("get Query if applied").answeredBy(actor -> {
            Query query = Filter.by(filter);
            if (query.isApplied().answeredBy(actor)){
                query.with(query.getAppliedValue().answeredBy(actor));
            }
            return null;
        });
    }

    public static Question<Boolean> isReady(){
        return Question.about("is Filter Bar Ready").answeredBy(actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(FilterUI.BAR)
            );
            Integer filters = FilterUI.FILTERS.resolveAllFor(actor).size();
            Integer values = FilterUI.FILTER_VALUES.resolveAllFor(actor).size();
            return filters.equals(values);
        });
    }

    public static Performable makeReady(){
        return Task.where("{0} clears empty filter",actor -> {
            actor.attemptsTo(
                    Check.whether(isReady()).otherwise(
                            Hit.the(Keys.BACK_SPACE).into(FilterUI.BAR)
                    ),
                    Ensure.thatTheAnswerTo(isReady()).isTrue()
            );
        }).withNoReporting();
    }

}
