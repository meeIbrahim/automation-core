package serenity.custom.interactions;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

public class MultiSearchSelect implements Interaction {
    List<String> options;
    Task clearChosenOptions;
    Target inputField;

    public MultiSearchSelect(List<String> options) {
        this.options = options;

        clearChosenOptions = new Task() {
            @Override
            public <T extends Actor> void performAs(T t) {

            }
        };
    }

    @Override
    @Step("{0} selects #options from #inputField")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(clearChosenOptions);
        options.forEach(option -> actor.attemptsTo(JavaScriptClick.on(inputField),Enter.theValue(option).into(inputField).thenHit(Keys.ENTER)));
    }

    public static MultiSearchSelect usingText(String... options) {
        return Instrumented.instanceOf(MultiSearchSelect.class).withProperties(Arrays.asList(options));
    }
    public static MultiSearchSelect usingText(List<String> options) {
        return Instrumented.instanceOf(MultiSearchSelect.class).withProperties(options);
    }
    public MultiSearchSelect from(Target inputField) {
        this.inputField = inputField;
        return this;
    }

    public MultiSearchSelect andClear(Target chosenOptionsClearBtns) {
        this.clearChosenOptions = Task.where("{0} clear the selected choices",
                Check.whether(the(chosenOptionsClearBtns), isPresent()).andIfSo(ClickAll.by(chosenOptionsClearBtns)));
        return this;
    }

}
