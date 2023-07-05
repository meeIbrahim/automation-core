package serenity.custom.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

public class SearchSelect implements Interaction {
    String text;
    Target selectInput;
    Target dropdown; //element target clicking on which opens the dropdown may not be select element itself

    public SearchSelect(String text) {
        this.text = text;
    }

    @Override
    @Step("{0} searches and selects #text from #dropdown")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(dropdown),
                Enter.theValue(text).into(selectInput).thenHit(Keys.ENTER)
        );
    }

    public static SearchSelect usingText(String text) {
        return Instrumented.instanceOf(SearchSelect.class).withProperties(text);
    }

    public SearchSelect inDropDown(Target dropdown) {
        this.dropdown = dropdown;
        return this;
    }

    public SearchSelect from(Target selectInput) {
        this.selectInput = selectInput;
        return this;
    }
}
