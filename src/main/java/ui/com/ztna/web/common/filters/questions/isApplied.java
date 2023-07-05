package ui.com.ztna.web.common.filters.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.thucydides.core.annotations.Step;
import ui.com.ztna.web.common.filters.FilterUI;

import java.util.List;

public class isApplied implements Question<Boolean> {
    String Filter;
    private isApplied(String Filter){
        this.Filter = Filter;
    }
    public static isApplied forFilter(String Filter){
        return new isApplied(Filter);
    }

    @Override
    @Subject("is Filter Applied")
    public Boolean answeredBy(Actor actor) {
        List<WebElementFacade> filters = FilterUI.SELECTED_FILTER.of(Filter).resolveAllFor(actor);
        return !filters.isEmpty();
    }
}
