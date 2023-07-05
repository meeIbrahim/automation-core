package ui.com.ztna.web.common.filters.actions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Step;
import org.hamcrest.Matchers;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.filters.Filter;
import ui.com.ztna.web.common.filters.FilterUI;
import ui.com.ztna.web.common.filters.questions.getValue;
import ui.com.ztna.web.common.filters.questions.isApplied;


import java.util.Objects;

import static ui.com.ztna.web.common.filters.Filter.*;

public class Apply implements Performable {
    String filter;
    String Value;
    public Apply(String Filer,String Value){
        this.filter = Filer;
        this.Value = Value;
    }
    public Apply(String Filter){
        this.filter = Filter;
        this.Value = "";
    }
    public static Apply filter(String Filter){
        return new Apply(Filter);
    }
    public Apply withValue(String Value){
        return Instrumented.instanceOf(Apply.class).withProperties(filter,Value);
    }
    @Override
    @Step("{0} applies Filter #filter with value #Value")
    public <T extends Actor> void performAs(T actor) {
        if (!Objects.equals(Value, "")){
            actor.attemptsTo(
                    Check.whether(getValue.forFilter(filter), Matchers.is(this.Value)).otherwise(
                            Clear.filter(this.filter),
                        Check.whether(isReady()).otherwise(
                                makeReady()
                        ),
                            Filter.open(),
                            Wait.forVisibilityOf(FilterUI.FILTER_SELECTION.of(this.filter)),
                            Click.on(FilterUI.FILTER_SELECTION.of(this.filter)),
                            Check.whether(isOpen()).andIfSo(Filter.open()), /// Requires Check Whether to run smoothly
                            Wait.forVisibilityOf(FilterUI.VALUE_SELECTION.of(this.Value)),
                            Click.on(FilterUI.VALUE_SELECTION.of(this.Value))
                    ),
                    Ensure.thatTheAnswerTo(isApplied.forFilter(filter)).isTrue(),
                    Ensure.thatTheAnswerTo(getValue.forFilter(filter)).isEqualTo(this.Value)
            );
        }
        else {
            actor.attemptsTo(Clear.filter(filter),
                    Ensure.thatTheAnswerTo(isApplied.forFilter(filter)).isFalse());
        }
    }
}
