package serenity.custom.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Navigate implements Task {
    String urlShort;

    public Navigate(String urlShort) {
        this.urlShort = urlShort;
    }

    @Override
    @Step("{0} navigates to #urlShort")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn().thePageNamed(urlShort));
    }

    public static Navigate to(String urlShort) {
        return instrumented(Navigate.class, urlShort);
    }
}
