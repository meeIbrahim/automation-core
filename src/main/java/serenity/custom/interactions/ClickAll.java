package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ClickAll implements Interaction {
    Target targets;

    public ClickAll(Target targets) {
        this.targets = targets;
    }

    @Override
    @Step("{0} clicks on all #targets elements")
    public <T extends Actor> void performAs(T actor) {
        targets.resolveAllFor(actor).forEach(element -> actor.attemptsTo(Click.on(element)));

    }

    public static ClickAll by(Target targets) {
        return instrumented(ClickAll.class, targets);
    }
}
