package ui.com.ztna.web.common.tables.common;

import junit.framework.AssertionFailedError;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;

import org.asciidoctor.internal.AsciidoctorCoreException;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;


import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public interface Pagination {


    /// Hook like method to insert wait if required for page change
    default Performable waitForPageChange(){
        return Task.where("{0} waits for page change",actor -> {

        }).withNoReporting();
    }
    default Performable nextPage(){
        return Task.where("{0} attempts to navigate to next Page in Listing",actor -> {
                Integer currentPage = actor.asksFor(currentPage());
            if (actor.asksFor(isPaginated())){
                actor.attemptsTo(
                        Check.whether(isLastPage()).otherwise(
                                Click.on(PaginationUI.NEXT_PAGE),
                                waitForPageChange(),
                                Wait.forInvisibilityOf(PaginationUI.CURRENT_PAGED_NUMBERED.of(String.valueOf(currentPage))).forTime(Duration.ofSeconds(10)),
                                Ensure.thatTheAnswerTo(currentPage()).isGreaterThan(currentPage)
                        )
            );

        }});
    }
    default Question<PageIterator> pageIterator(){
        return Question.about("Iterator for pages").answeredBy(
                actor -> new PageIterator(actor,this)
        );
    }

    default Performable previousPage(){
        return Task.where("{0} attempts to navigate to previous Page in Listing",actor -> {
            Integer currentPage = actor.asksFor(currentPage());
            if (actor.asksFor(isPaginated())){
            actor.attemptsTo(
                    Check.whether(isFirstPage()).otherwise(
                            Click.on(PaginationUI.PREVIOUS_PAGE),
                            waitForPageChange(),
                            Wait.forInvisibilityOf(PaginationUI.CURRENT_PAGED_NUMBERED.of(String.valueOf(currentPage))),
                            Ensure.thatTheAnswerTo(currentPage()).isLessThan(currentPage)
                            )
                    );
        }});
    }
    default Performable lastPage(){
        return Task.where("{0} attempts to navigate to last Page in Listing",actor -> {
            if (actor.asksFor(isPaginated())){
            actor.attemptsTo(
                    Check.whether(isLastPage()).otherwise(
                            Click.on(PaginationUI.LAST_PAGE_BUTTON),
                            waitForPageChange(),
                            Ensure.thatTheAnswerTo(isLastPage()).isTrue()
                            ));
        }});
    }
    default Performable firstPage(){
        return Task.where("{0} attempts to navigate to first Page in Listing",actor -> {
            if (actor.asksFor(isPaginated())){
            actor.attemptsTo(
                    Check.whether(isFirstPage()).otherwise(
                            Click.on(PaginationUI.FIRST_PAGE_BUTTON),
                            waitForPageChange(),
                            Ensure.thatTheAnswerTo(isFirstPage()).isTrue()
                    ));
        }});
    }
    default Performable jumpToPage(Integer pageNumber){
        return Task.where("{0} jumps to page Z".replace("Z",String.valueOf(pageNumber)),actor -> {
            if (actor.asksFor(lastPageNumber()) < pageNumber){
                throw new AssertionError("Page not Available");
            }
            else {
                actor.attemptsTo(
                        Wait.forPresenceOf(PaginationUI.SELECT_PAGE),
                        Input.of(PaginationUI.SELECT_PAGE).with(String.valueOf(pageNumber)),
                        waitForPageChange(),
                        Ensure.thatTheAnswerTo(currentPage()).isEqualTo(pageNumber)
                );
            }
        });
    }

    default Question<Integer> currentPage(){
        return Question.about("Current Page Number").answeredBy(
                actor -> {
                    if (actor.asksFor(isPaginated())) {
                        return Integer.parseInt(PaginationUI.CURRENT_PAGE.resolveFor(actor).getText());
                    }
                    else {
                        return 1;
                    }
                }
        );
    }
    default Question<Integer> lastPageNumber(){
        return Question.about("Current Page Number").answeredBy(
                actor -> {
                    if (actor.asksFor(isPaginated())) {
                        return Integer.parseInt(PaginationUI.LAST_PAGE.resolveFor(actor).getText());
                    }
                    else {
                        return 1;
                    }
                }
        );
    }
    default Question<Boolean> isPaginated(){
        return Question.about("Are there multiple pages").answeredBy(
                actor -> {
                    List<WebElementFacade> elements = PaginationUI.FOOTER.resolveAllFor(actor);
                    return !elements.isEmpty();
                }
        );
    }

    default Question<Integer> totalResults(){
        return Question.about("Total number of results").answeredBy(
          actor -> {
              if (actor.asksFor(isPaginated())) {
                  Matcher matcher = getResultsCount(actor);
                  if (matcher == null) {
                      return null;
                  }
                  return Integer.parseInt(matcher.group(3));
              }
              else {
                  return actor.asksFor(currentPageResults());
              }
          }
        );
    }
    default Question<Boolean> isLastPage(){
        return Question.about("Is On Last page").answeredBy(
                actor -> {
                    if (actor.asksFor(isPaginated())) {
                        Integer last = Integer.parseInt(PaginationUI.LAST_PAGE.resolveFor(actor).getText());
                        return (last.equals(actor.asksFor(currentPage())));
                    }
                    else {return true;} /// If only one page -> Its both the first and the last page
                }
        );
    }
    default Question<Boolean> isFirstPage(){
        return Question.about("Is On First page").answeredBy(
                actor -> {
                    if (actor.asksFor(isPaginated())) {
                        Integer first = Integer.parseInt(PaginationUI.FIRST_PAGE.resolveFor(actor).getText());
                        return (first.equals(actor.asksFor(currentPage())));
                    }
                    else {return true;} /// If only one page -> Its both the first and the last page
                }
        );
    }

    Question<Integer> currentPageResults();

     default  <T extends Actor> Matcher getResultsCount(T actor){
         /// Start Count to End Count of Total count
        String regex = "(\\d*) to (\\d*) of (\\d*)";
        Pattern pattern = Pattern.compile(regex,Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(PaginationUI.RESULTS.resolveFor(actor).getText());
        if (matcher.find()){
            return matcher;
        }
        return null;
    }
}
