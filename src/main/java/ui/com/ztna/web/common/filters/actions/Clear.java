package ui.com.ztna.web.common.filters.actions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Hit;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import org.hamcrest.Matchers;
import org.openqa.selenium.Keys;
import ui.com.ztna.web.common.filters.FilterUI;
import ui.com.ztna.web.common.filters.questions.getValue;
import ui.com.ztna.web.common.filters.questions.isApplied;

public class Clear implements Performable {
    String Filter;
    static Target CLEAR_VALUE = Target.the("Clear Value Button")
            .locatedBy(FilterUI.SELECTED_FILTER_VALUE.getCssOrXPathSelector() + "/" + FilterUI.VALUE_CLEAR);
    public Clear(String filter){
        this.Filter = filter;
    }

    public static Clear filter(String filter){
        return Instrumented.instanceOf(Clear.class).withProperties(filter);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Check.whether(isApplied.forFilter(Filter)).andIfSo(
                        Check.whether(getValue.forFilter(Filter), Matchers.is(""))
                                .andIfSo(
                                        Hit.the(Keys.BACK_SPACE).into(FilterUI.BAR)
                        ).otherwise(
                                        Click.on(CLEAR_VALUE.of(Filter))
                        )
                ),
                Ensure.thatTheAnswerTo(isApplied.forFilter(Filter)).isFalse()
        );
    }
}
