package ui.com.ztna.web.common.input;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

import java.util.Objects;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class Checkbox implements Performable {
    public static Target BY_ORDER = Target.the("Checkbox")
            .locatedBy("//*[contains(@class,\"MuiCheckbox\")][{0}]/ancestor::*[.//text()][1]");
    public static Target BY_LABEL = Target.the("Checkbox")
            .locatedBy("//*[contains(@class,\"MuiCheckbox\")]/ancestor::*[.//*[contains(text(),\"{0}\")]][1]");

    String CHECKED = ".//*[contains(@class,\"checked\")]";
    String BOX = ".//*[@type=\"checkbox\"]";
    Target target;
    String toDo;

    private Checkbox(Target target){this.target = target;} // Temporary Constructor

    public Checkbox(Target target,String toDo){    // Constructor passed to instrumented
        this.target = target;
        this.toDo = toDo;
    }
    private Checkbox(){}// Serenity suggests having an empty constructor
    public Checkbox enable(){
        return Instrumented
                .instanceOf(Checkbox.class)
                .withProperties(target,"enable");
    }
    public Checkbox disable(){
        return Instrumented
                .instanceOf(Checkbox.class)
                .withProperties(target,"disable");
    }
    public Checkbox toggle(){
        return Instrumented
                .instanceOf(Checkbox.class)
                .withProperties(target,"toggle");
    }
    public static Checkbox of(Target target){return new Checkbox(target);}
    @Override
    @Step("{0} Toggles #target")
    public <T extends Actor> void performAs(T actor) {
        switch (toDo){
            case "enable":
                enableToggle(actor);
                break;
            case "disable":
                disableToggle(actor);
                break;
            default:
                toggleCheckbox(actor);
        }
    }

    private <T extends Actor> void enableToggle(T actor){
        if (!actor.asksFor(isChecked())){
            toggleCheckbox(actor);
        }
    }
    private <T extends Actor> void disableToggle(T actor){
        if (actor.asksFor(isChecked())){
            toggleCheckbox(actor);
        }
    }
    private <T extends Actor> void toggleCheckbox(T actor){
        actor.attemptsTo(
                waitPresenceOf(target)
        );
        Boolean Before = actor.asksFor(isChecked());
        WebElementFacade element = target.resolveFor(actor).findBy(BOX);
        actor.attemptsTo(Click.on(element));
        Boolean After = actor.asksFor(isChecked());
        actor.attemptsTo(
                Ensure.thatTheAnswerTo(didToggle(Before,After)).isTrue()
        );
    }

    public Question<Boolean> isChecked(){
        return Question.about("Is Checkbox checked").answeredBy(actor -> !target.resolveFor(actor).findElements(By.xpath(CHECKED)).isEmpty());
    }
    private Question<Boolean> didToggle(Boolean Before, Boolean After){
        return Question.about("Toggled Successfully? ").answeredBy(actor -> !Objects.equals(Before, After));
    }
}
