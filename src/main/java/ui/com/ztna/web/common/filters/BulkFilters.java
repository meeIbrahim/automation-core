package ui.com.ztna.web.common.filters;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ui.com.ztna.web.common.filters.FilterUI.FILTERS;

public class BulkFilters {

    HashMap<String,Query> queries = new HashMap<>();
    public BulkFilters(Query... filters){
        this.queries.putAll(Stream.of(filters)
                .collect(Collectors
                        .toMap(Query::getFilter,
                                Function.identity()
                        )));

        /// (query1,query2) -> query2           ///// Replace existing key with later query
    }

    public Boolean isEmpty(){
        return queries.isEmpty();
    }
    public BulkFilters add(Query... filters){
        this.queries.putAll(Stream.of(filters)
                .collect(Collectors
                        .toMap(Query::getFilter,
                                Function.identity()
                        )));
        return this;
    }

    public Performable applyAll(){
        return Task.where("{0} Applies all filters",actor -> {
            cleanFilters(actor);
            for(Map.Entry<String,Query> queryEntry : queries.entrySet()){
                actor.attemptsTo(
                        queryEntry.getValue().apply()
                );
            }
        });
    }

    private <T extends Actor> void cleanFilters(T actor){
        List<WebElementFacade> appliedFilters = FILTERS.resolveAllFor(actor);
        List<String> toRemove = new ArrayList<>();
        boolean isApplied = false;
        for (WebElementFacade appliedFilter : appliedFilters){
            isApplied = false;
            for (Map.Entry<String,Query> queryEntry : queries.entrySet()){
                if (appliedFilter.getText().toLowerCase().contains(queryEntry.getKey())){
                    isApplied = true;
                    break;
                }
            }
            if (!isApplied){toRemove.add(appliedFilter.getText());}
        }

        for (String remove : toRemove){
            actor.attemptsTo(
                    Filter.by(remove).clear()
            );
        }
    }
}
