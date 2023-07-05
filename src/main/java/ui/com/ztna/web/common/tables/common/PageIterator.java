package ui.com.ztna.web.common.tables.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.conditions.Check;
import serenity.custom.interactions.Logger;
import serenity.custom.questions.URL;

import java.util.Iterator;
import java.util.List;

public class PageIterator  implements Iterator<Integer> {

    Actor actor;
    Pagination pagination;
    String currentUrl;
    Boolean toStart = true;
    Performable openPage;
    /***
     *
     * @param actor Actor that will navigate through pages
     * @param pagination Pagination Object that will contain Performables for navigations
     * @param openPage Performable that is performed before first iteration
     *
     *  Iterates through Pages if Paginated
     *  Always Starts with the first page
     */

    public PageIterator(Actor actor, Pagination pagination, Performable openPage){
        this.actor = actor;
        this.currentUrl = actor.asksFor(URL.current());
        this.pagination = pagination;
        this.openPage = openPage;
    }

    public PageIterator(Actor actor, Pagination pagination){
        this.actor = actor;
        this.currentUrl = actor.asksFor(URL.current());
        this.pagination = pagination;
        this.openPage = new Performable() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                    /// Do not Open any page
            }
        };
    }

    @Override
    public boolean hasNext() {
        if (actor.asksFor(pagination.isLastPage())){
            return toStart;
        }
        else {
            return true;
        }
    }  // if last page and not starting return false


    @Override
    public Integer next() {
        if (!hasNext() && !toStart){
            return null;
        }
        if (!currentUrl.contains(actor.asksFor(URL.current()))){
            try {
                throw new ListingIteratorException("Url Changed during Listing Iteration!");
            } catch (ListingIteratorException e) {
                throw new RuntimeException(e);
            }
        }
        /// Starts with the first page
        if (toStart){
                actor.attemptsTo(
                        openPage,
                        pagination.firstPage()
                );
            this.currentUrl = actor.asksFor(URL.current());
            toStart = false;
        }
        else {
            actor.attemptsTo(
                    pagination.nextPage()
            );
        }
        return actor.asksFor(pagination.currentPage());
    }
}
