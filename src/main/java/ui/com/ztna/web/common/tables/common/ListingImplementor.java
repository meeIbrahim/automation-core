package ui.com.ztna.web.common.tables.common;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Page;
import ui.com.ztna.web.common.tables.Row;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;

public abstract class ListingImplementor <T extends Row> implements Listings<T>,Pagination {
    protected Target ROWS;
    protected Target ROWS_CONTAINS;
    protected Target COLUMNS;
    Class<T> tClass;

    Page page;
    protected Target COLUMN;
    protected Target ROW;
    protected Target ROW_CONTAINS;
    protected Target COLUMN_ELEMENTS; // Locator to check element under a Column
    protected List<String> Headers;


    protected Target REFRESH = Target.the("Refresh Button")
            .locatedBy("//button[.//*[contains(@id,\"refresh\")]]");
    protected Target LOADER = Target.the("Loading Icon")
            .locatedBy("//*[contains(@data-testid,\"loader\")]");

    protected Target BLANK_SLATE = Target.the("Blanke Slate icon")
            .locatedBy("//*[contains(@data-testid,\"blankSlate\")]");

    public ListingImplementor(String ROWS,
                              String COLUMNS,
                              String ROW,
                              String COLUMN_ELEMENTS,
                              String COLUMN,
                              List<String> Headers, Page page, Class<T> tClass) {
        this.page = page;
        this.tClass = tClass;
        this.Headers  = Headers;
        this.ROW = Target.the("Row Card")
                .locatedBy(ROW);
        this.COLUMNS = Target.the("Column Element")
                .locatedBy(COLUMNS);
        this.ROWS = Target.the("Rows")
                        .locatedBy(ROWS);
        this.COLUMN_ELEMENTS = Target.the("Column Elements")
                .locatedBy(COLUMN_ELEMENTS);
        this.COLUMN = Target.the("Column")
                .locatedBy(COLUMN);
        this.ROW_CONTAINS = Target.the("Row Containing")
                .locatedBy(ROW + "[{0}]");
        this.ROWS_CONTAINS = Target.the("Rows Containing")
                .locatedBy(ROWS + "[{0}]");
    }

    T createRowObject(WebElementFacade rowElement) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = tClass.getDeclaredConstructor(WebElementFacade.class);
        return constructor.newInstance(rowElement);
    }
    public Question<Iterator<T>> rowIterator(){
        return Question.about("Iterator for all Rows").answeredBy(
                actor -> new RowIterator<>(actor,this)
        );
    }
    @Override
    public Question<PageIterator> pageIterator(){
        return Question.about("Iterator for pages").answeredBy(
                actor -> new PageIterator(actor,this,this.waitUntilReady())
        );
    }

    @Override
    public Performable waitForPageChange() {
        return Task.where("{0} wait for page change",actor -> {
            actor.attemptsTo(
                    Wait.forQuestion(isReady()).untilEqualTo(true).forTime(Duration.ofSeconds(10)));
                    ///// Temporary to be removed
                    Row resource = row(1).answeredBy(actor);
                    actor.asksFor(
                            Wait.forInvisibilityOf(resource.webElement(actor))
                                    .forTime(Duration.ofSeconds(5))
                                    .isSuccess()
                    );
                    ///////////////////////////////////////////////////////////
        }).withNoReporting();
    }

    public Performable navigateTo(T row){
        return Task.where("{0} navigates to page with row Z".replace("Z",row.name()),actor -> {
            Iterator<Integer> pageIterator = pageIterator().answeredBy(actor);
            boolean notFound = true;
            while(pageIterator.hasNext()){
                pageIterator.next();
                if (actor.asksFor(row.exists())){
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                throw new IllegalArgumentException("Row:" + row.name() + " Not Found in Table");
            }
        });
    }
    @Override
    public Performable refresh(){
        return Task.where("{0} Refreshes listing",actor -> {
            if (REFRESH.resolveFor(actor).isPresent()){
                REFRESH.resolveFor(actor).click();
                actor.attemptsTo(
                            Wait.forInvisibilityOf(LOADER)
                );
            }
            else {
                int currentPage = currentPage().answeredBy(actor);
                actor.attemptsTo(
                        page.refresh(),
                        waitUntilReady(),
                        jumpToPage(currentPage)
                );
            }
            actor.attemptsTo(
                    waitUntilReady()
            );
        });
    }
    @Override
    public Question<T> row(String matcher) {
        return Question.about("Return Row for MATCH".replace("MATCH", matcher)).answeredBy(actor -> {
            Iterator<Integer> iterator = pageIterator().answeredBy(actor);
            while (iterator.hasNext()) {
                iterator.next();
                List<WebElementFacade> elements = ROW.of(matcher).resolveAllFor(actor);
                if (!elements.isEmpty()) {
                    try {
                        return createRowObject(elements.get(0));
                    } catch (Exception e) {
                        throw new RowCreateException(tClass);
                    }

                }
            }
            return null;
        });
    }

    // Searches for Row relative to ROWS Target
    @Override
    public Question<T> row(Target matcher) {
        return Question.about("Return Row for MATCH".replace("MATCH", matcher.getName())).answeredBy(
                actor ->{
                    Iterator<T> rowIterator = rowIterator().answeredBy(actor);
                    while (rowIterator.hasNext()) {
                        T row = rowIterator.next();
                        if (row.contains(matcher).answeredBy(actor)){
                            return row;
                        }
                    }
                    return null;
                });
    }

    @Override
    public Question<Boolean> isReady(){
        return Question.about("Is listing completely loaded").answeredBy(actor -> {
            List<WebElementFacade> rows = ROWS.resolveAllFor(actor);
            List<WebElementFacade> blankSlate = BLANK_SLATE.resolveAllFor(actor);
            return (!rows.isEmpty() || !blankSlate.isEmpty());
        });
    }
    @Override
    public Performable waitUntilReady(){
        return Task.where("{0} waits for listing to be ready",actor -> {
            actor.attemptsTo(
                    page.openPage(),
                    Wait.forQuestion(isReady()).untilEqualTo(true).forTime(Duration.ofSeconds(10))
            );
        }).withNoReporting();
    }
    @Override
    public Question<T> row(Integer count){
        return Question.about("Return Row numbered COUNT on current Page".replace("COUNT",String.valueOf(count)))
                .answeredBy(actor -> {
                    if (totalResults().answeredBy(actor) >= count){
                        refresh();
                        List<WebElementFacade> elementFacades = ROWS.resolveAllFor(actor);
                        try {
                            return createRowObject(elementFacades.get(count - 1));
                        } catch (Exception e){
                            throw new RowCreateException(tClass);
                        }
                    }
                    return null;
                });
    }
    @Override
    public Question<LinkedHashSet<T>> rows(){
        return Question.about("Return all rows").answeredBy(actor -> {
            Iterator<Integer> pageIterator = pageIterator().answeredBy(actor);
            LinkedHashSet<T> rowSet = new LinkedHashSet<>();
            while (pageIterator.hasNext()){
                pageIterator.next();
                List<WebElementFacade> rows = ROWS.resolveAllFor(actor);
                for(WebElementFacade row: rows){
                    try {
                        rowSet.add(createRowObject(row));
                    } catch (Exception e){
                        throw new RowCreateException(tClass);
                    }
                }
            }
            return rowSet;
        });
    }
    @Override
    public Question<LinkedHashSet<T>> rows(String matcher){
        return Question.about("Return all rows matching MATCH".replace("MATCH", matcher)).answeredBy(
                actor -> {
                    LinkedHashSet<T> rowSet = new LinkedHashSet<>();
                    Iterator<Integer> iterator = pageIterator().answeredBy(actor);
                    while(iterator.hasNext()){
                        iterator.next();
                        List<WebElementFacade> elements = ROW.of(matcher).resolveAllFor(actor);
                        for(WebElementFacade row: elements){
                            try {
                                rowSet.add(createRowObject(row));
                            } catch (Exception e){
                                throw new RowCreateException(tClass);
                            }
                        }
                    }
                    return rowSet;
                }
        );
    }
    @Override
    public Question<LinkedHashSet<T>> rows(Target matcher){
        return Question.about("Return all rows matching MATCH".replace("MATCH", matcher.getName())).answeredBy(
                actor -> {
                    Target finalMatcher = normalizeSubElement(matcher);
                    LinkedHashSet<T> rowSet = new LinkedHashSet<>();
                    Iterator<Integer> iterator = pageIterator().answeredBy(actor);
                    while (iterator.hasNext()){
                        iterator.next();
                        List<WebElementFacade> elements = ROWS_CONTAINS.of(finalMatcher.getCssOrXPathSelector()).called(finalMatcher.getName()).resolveAllFor(actor);
                        for(WebElementFacade row: elements){
                            try {
                                rowSet.add(createRowObject(row));
                            } catch (Exception e){
                                throw new RowCreateException(tClass);
                            }
                        }
                    }
                    return rowSet;
                }
        );
    }

    @Override
    public Question<LinkedHashSet<T>> rows(String header, String matcher){
        return Question.about("Return all rows matching MATCH under Column HEAD".replace("MATCH", matcher).replace("HEAD", header)).answeredBy(
                actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    LinkedHashSet<T> rowSet = new LinkedHashSet<>();
                    int headerIndex = this.Headers.indexOf(header);
                    Target cellTarget = normalizeSubElement(COLUMN_ELEMENTS.of(String.valueOf(headerIndex), matcher));
                    List<WebElementFacade> elements = ROWS_CONTAINS.of(cellTarget.getCssOrXPathSelector()).resolveAllFor(actor);
                    for(WebElementFacade row: elements){
                        try {
                            rowSet.add(createRowObject(row));
                        } catch (Exception e){
                            throw new RowCreateException(tClass);
                        }
                    }
                    return rowSet;
                }
        );
    }

    protected Target normalizeSubElement(Target input){
        if(input.getCssOrXPathSelector().startsWith("."))
        {
            return input;
        } else if (input.getCssOrXPathSelector().startsWith("/")) {
            return Target.the(input.getName()).locatedBy("." + input.getCssOrXPathSelector());
        }
        else {throw new IllegalArgumentException();}
    }

    @Override
    public Question<Integer> currentPageResults(){
        return Question.about("Number of results on current page").answeredBy(
                actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    if (actor.asksFor(isPaginated())) {
                        Matcher matcher = getResultsCount(actor);
                        if (matcher == null) {
                            return null;
                        }
                        return (Integer.parseInt(matcher.group(2)) - Integer.parseInt(matcher.group(1)) + 1);
                    }
                    else {
                        return actor.asksFor(countRows());
                    }
                }
        );
    }

    @Override
    public Question<Boolean> contains(String matcher){
        return Question.about("Table Contains MATCH".replace("MATCH", matcher)).answeredBy(actor -> {
            Iterator<Integer> iterator = pageIterator().answeredBy(actor);
            while (iterator.hasNext()){
                iterator.next();
                if (COLUMN_ELEMENTS.of("0", matcher).resolveFor(actor).isPresent())
                {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public Question<Boolean> contains(String header, String matcher){
        return Question.about("Table Contains HEAD MATCH ".replace("MATCH", matcher).replace("HEAD", header))
                .answeredBy(actor -> {
                    Iterator<Integer> iterator = pageIterator().answeredBy(actor);
                    int headerIndex = this.Headers.indexOf(header);
                    while (iterator.hasNext()){
                        iterator.next();
                        if (COLUMN_ELEMENTS.of(String.valueOf(headerIndex), matcher).resolveFor(actor).isPresent()){
                            return true;
                        }
                    }
                    return false;
                });
    }

    @Override
    public Question<Boolean> contains(Integer header, String matcher){
        return Question.about("Table Contains MATCH in Column:HEAD ".replace("MATCH", matcher).replace("HEAD", header.toString()))
                .answeredBy(actor -> {
                    Iterator<Integer> iterator = pageIterator().answeredBy(actor);
                    while (iterator.hasNext()){
                        iterator.next();
                        if (COLUMN_ELEMENTS.of(String.valueOf(header - 1), matcher).resolveFor(actor).isPresent()){
                            return true;
                        }
                    }
                    return false;
                });
    }

    @Override
    public Question<List<WebElementFacade>> columnElements(String column) {
        return Question.about("Elements under Column HEAD on Current Page".replace("HEAD", column))
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    int headerIndex = this.Headers.indexOf(column.toLowerCase());
                    return COLUMN.of(String.valueOf(headerIndex)).resolveAllFor(actor);
                });

    }
    @Override
    public Question<List<WebElementFacade>> columnElements(Integer column) {
        return Question.about("Elements under Column HEAD on current Page".replace("HEAD", column.toString()))
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    return COLUMN.of(String.valueOf(column - 1)).resolveAllFor(actor);
                });

    }

    @Override
    public Question<Integer> countRows() {
        return Question.about("Count of Rows on current Page")
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    return ROWS.resolveAllFor(actor).size();
                });
    }

    @Override
    public Question<Integer> countColumns() {
        return Question.about("Count of Columns")
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            waitUntilReady()
                    );
                    return COLUMNS.resolveAllFor(actor).size();
                });
    }


}
