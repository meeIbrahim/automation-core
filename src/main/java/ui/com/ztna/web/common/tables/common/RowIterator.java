package ui.com.ztna.web.common.tables.common;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import serenity.custom.questions.URL;
import ui.com.ztna.web.common.tables.Row;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;


public class RowIterator <T extends Row> implements Iterator<T> {
    Actor actor;
    ListingImplementor<T> table;
    LinkedHashSet<T> rows = null;
    PageIterator pageIterator;
    String currentUrl;
    Boolean start = true;
    public RowIterator(Actor actor, ListingImplementor<T> table){
        this.actor = actor;
        this.table = table;
        this.pageIterator = table.pageIterator().answeredBy(actor);
        rows = getRows();
        if (rows.isEmpty()){
            rows = null;
        }
    }


    protected LinkedHashSet<T> getRows(){
        if (rows == null && (!table.isLastPage().answeredBy(actor) || start)) {
                rows = new LinkedHashSet<>();
                if (start){
                    actor.attemptsTo(
                            table.waitUntilReady(),
                            table.firstPage()
                    );
                    this.currentUrl = actor.asksFor(URL.current());
                    start = false;
                }
                else {
                    actor.attemptsTo(
                            table.nextPage()
                    );
                }
                List<WebElementFacade> rowElements = table.ROWS.resolveAllFor(actor);
                for(WebElementFacade row: rowElements){
                    try {
                        rows.add(table.createRowObject(row));
                    } catch (Exception e){
                        throw new RowCreateException(table.tClass);
                    }

                }

        }
        return rows;
    }
    @Override
    public boolean hasNext() {
        return getRows() != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            return null;
        }
        if (!currentUrl.contains(actor.asksFor(URL.current()))){
            try {
                throw new ListingIteratorException("Url Changed during Listing Iteration!");
            } catch (ListingIteratorException e) {
                throw new RuntimeException(e);
            }
        }
        T row = getRows().iterator().next();
        getRows().remove(row);
        if (getRows().isEmpty()){
            rows = null;
        }
        return row;
    }
}
