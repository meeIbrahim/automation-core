package serenity.custom.performable.conditional;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.conditions.Check;

public class PerformConditionalTask implements Performable {
    ConditionalTask[] tasks;
    public PerformConditionalTask(ConditionalTask[] tasks){
        this.tasks = tasks;
    }
    @Override
    public <T extends Actor> void performAs(T t) {
        for(ConditionalTask task:tasks) {
            t.attemptsTo(Check.whether(task.isPerformableBy(t)).andIfSo(task));
        }
    }

    public static PerformConditionalTask performConditionalTask(ConditionalTask... tasks) {
        return Instrumented.instanceOf(PerformConditionalTask.class).withProperties(tasks);
    }
}
