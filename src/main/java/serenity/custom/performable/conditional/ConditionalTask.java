package serenity.custom.performable.conditional;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public interface ConditionalTask extends Performable {
    <T extends Actor> Boolean isPerformableBy(T actor);
}
