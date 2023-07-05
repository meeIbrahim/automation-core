package ui.com.ztna.web.common.input;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.CompositePerformable;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Hit;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;
import ui.com.ztna.web.common.Wait.Wait;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class Input implements Performable {
    public static final Target BY_ORDER = Target.the("Generic Input box")
            .locatedBy("(//div[not(contains(@class,\"MuiForm\"))]/div[contains(@class,'MuiInput')])[{0}]//*[name()=\"input\" or name()=\"textarea\"]");
    public static final Target BY_LABEL = Target.the("Generic Input box")
            .locatedBy("//div[not(contains(@class,\"MuiForm\"))]/div[contains(@class,'MuiInput')][..//*[contains(text(),\"{0}\")]]//*[name()=\"input\" or name()=\"textarea\"]");
     Target target;
     String input;
     Boolean clear = false;

     public Input(Target target, String input,Boolean clear){
         this.target = target;
         this.input = input;
         this.clear = clear;
     }


    Input(Target target){
        this.target = target;
    } // Temporary Constructor
    Input(){this.target = null;} // Serenity suggests having an empty Constructor
    public static Input of(Target target){
        return new Input(target);
    }
    public Input with(String InputText){
        return Instrumented.instanceOf(Input.class).withProperties(target,InputText,true);
    }
    public Input update(String InputText){
        return Instrumented.instanceOf(Input.class).withProperties(target,InputText,false);
    }


    @Override
    @Step("{0} Inputs text to #target")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Wait.forVisibilityOf(target),
                Check.whether(clear).andIfSo(
                        Clear.field(target),
                        Ensure.thatTheAnswerTo(value()).isEqualTo("")
                )

        );
        String before = actor.asksFor(value());
        actor.attemptsTo(
                Enter.keyValues(input).into(target),
                Ensure.thatTheAnswerTo(value()).isEqualTo(before + input)
        );
    }

    public Question<String> value(){
         return Question.about("Input Value").answeredBy(actor -> target.resolveFor(actor).getAttribute("value"));
    }
    public Performable enter() {
        return CompositePerformable.from(this, new Performable() {
            @Override
            @Step("{0} hits Enter key")
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(
                        Hit.the(Keys.ENTER).into(target)
                );
            }
        });
    }
}
