package serenity.custom.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;

import static java.lang.Boolean.TRUE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotSelected;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isSelected;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

public class SetCheckBox implements Interaction {

    Boolean toSelect;
    Target locator;

    public SetCheckBox(Boolean toSelect) {
        this.toSelect = toSelect;
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
                Check.whether(toSelect.equals(TRUE)).andIfSo(
                        Check.whether(the(locator), isNotSelected()).andIfSo(Click.on(locator))
                ).otherwise(
                        Check.whether(the(locator), isSelected()).andIfSo(Click.on(locator))
                )
        );
    }

    public static SetCheckBox to(Boolean toSelect) {
        return Instrumented.instanceOf(SetCheckBox.class).withProperties(toSelect);
    }

    public SetCheckBox forCheckBox(Target locator) {
        this.locator = locator;
        return this;
    }
}
