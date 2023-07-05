package ui.com.ztna.web.common.filters.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.thucydides.core.annotations.Step;
import ui.com.ztna.web.common.filters.FilterUI;

import java.util.List;

public class getValue implements Question<String> {

    String Filter;

    private getValue(String Filter){
        this.Filter = Filter;
    }
    public static getValue forFilter(String Filter){
        return new getValue(Filter);
    }
    @Override
    @Subject("get Value for Applied Filter")
    public String answeredBy(Actor actor) {
        if (!isApplied.forFilter(Filter).answeredBy(actor)){return "";}
        List<WebElementFacade> values = FilterUI.SELECTED_FILTER_VALUE.of(Filter).resolveAllFor(actor);
        if (values.isEmpty()){return  "";}
        return values.get(0).getText();
    }
}
