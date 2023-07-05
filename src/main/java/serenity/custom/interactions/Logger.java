package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Logger implements Interaction {
    String message;

    public Logger(String message) {
        this.message = message;
    }

    @Override
    @Step("#message")
    public <T extends Actor> void performAs(T actor) {

    }

    public static Logger log(String message) {
        return instrumented(Logger.class, message);
    }
}
