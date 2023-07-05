package ui.com.ztna.web.common.input;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Objects;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class Toggle implements Performable {
    public static final Target BY_ORDER = Target.the("Generic Toggle")
            .locatedBy("(//div/*[contains(@class,'MuiSwitch')])[{0}]");
//    public static final Target BY_LABEL = Target.the("Generic Toggle")
//            .locatedBy("(//div/*[contains(@class,'MuiSwitch')])[{0}]");

    String CHECKED = "./*[contains(@class,\"checked\")]";

    Target target;
    String toDo;
    private Toggle(Target target){
        this.target = target;
    } // Temporary Constructor
    public Toggle(Target target, String toDo){  // Constructor passed to instrumented
        this.target = target;
        this.toDo = toDo;
    }
    private Toggle(){} // Serenity suggests having an empty Constructor

    public Toggle enable(){return Instrumented.instanceOf(Toggle.class).withProperties(target,"enable");}
    public Toggle disable(){return Instrumented.instanceOf(Toggle.class).withProperties(target,"disable");}
    public Toggle toggle(){return Instrumented.instanceOf(Toggle.class).withProperties(target,"toggle");}

    public static Toggle of(Target target){
        return new Toggle(target);
    }

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
                toggleSwitch(actor);
        }
    }
    private <T extends Actor> void enableToggle(T actor){
        if (!actor.asksFor(isEnabled())){
            toggleSwitch(actor);
        }
    }
    private <T extends Actor> void disableToggle(T actor){
            if (actor.asksFor(isEnabled())){
                toggleSwitch(actor);
            }
    }
    private <T extends Actor> void toggleSwitch(T actor){
        actor.attemptsTo(
                waitPresenceOf(target)
        );
        boolean elementBefore = actor.asksFor(isEnabled());
        boolean elementAfter = elementBefore;
        int tries = 3;
        while (tries != 0){   /// Bug where Toggle doesn't switch on first Click.
            actor.attemptsTo( /// No clear resolution other than to retry a few time
                    Click.on(target)
            );
            elementAfter = actor.asksFor(isEnabled());
            if (didToggle(elementBefore,elementAfter).answeredBy(actor)){
                break;
            }
            tries--;
        }
        actor.attemptsTo(
                Ensure.thatTheAnswerTo(didToggle(elementBefore,elementAfter)).isTrue()
        );
    }

    public Question<Boolean> isEnabled(){
        return Question.about("is Toggle Enabled").answeredBy(actor -> {
           WebElementFacade toggle = target.resolveFor(actor);
           return !toggle.findElements(By.xpath(CHECKED)).isEmpty();
        });
    }

    private Question<String> childElement(){
        return Question.about("Toggled Element").answeredBy(actor -> target.resolveFor(actor).findBy("./*[1]").getAttribute("class"));
    }
    private Question<Boolean> didToggle(boolean Before, boolean After){
        return Question.about("Toggled Successfully? ").answeredBy(actor -> Before != After);
    }

}
