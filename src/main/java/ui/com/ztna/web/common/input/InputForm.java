package ui.com.ztna.web.common.input;


import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;

import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Hit;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

import java.util.ArrayList;

import java.util.List;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;


public class InputForm  implements Performable{
    public static final Target BY_ORDER = Input.BY_ORDER;
    public static final Target BY_LABEL = Input.BY_LABEL;


    List<String> input;
    Boolean clear = false;
    Target target;
    final String valuesEntered = "../div";
    final String valueDelete = ".//*[contains(@class,\"delete\")]";
    public InputForm(Target target, Boolean clear, String... input) {
        this.input = List.of(input);
        this.target = target;
        this.clear = clear;
    }

    private InputForm(Target target){
        this.target = target;
        }
    public static InputForm of(Target target){
        return new InputForm(target);
    }
    public InputForm with(String... InputText){
        return Instrumented.instanceOf(InputForm.class).withProperties(target,true,InputText);
    }

    public InputForm update(String... InputText){
        return Instrumented.instanceOf(InputForm.class).withProperties(target,false,InputText);
    }


    @Override
    @Step("{0} Inputs text to #target")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                waitPresenceOf(target),
                Check.whether(clear).andIfSo(
                        clearForm(),
                        Ensure.thatTheAnswerTo(isEmpty()).isTrue()
                )

        );
        String before = actor.asksFor(value());
        for(String value: input){
            actor.attemptsTo(
                    Enter.keyValues(value).into(target),
                    Hit.the(Keys.ENTER).into(target)
            );
        }
        actor.attemptsTo(
                Ensure.thatTheAnswerTo(valuesEntered()).isTrue()
        );
    }

    private Performable clearForm(){
        return Task.where("{0} clears Input Form",actor -> {
            List<WebElementFacade> elements = target.resolveFor(actor).thenFindAll(valuesEntered);
            while(!elements.isEmpty()){
                WebElementFacade elementFacade = elements.get(0);
                actor.attemptsTo(
                        Scroll.to(elementFacade),
                        Click.on(elementFacade.thenFindAll(valueDelete).get(0))
                );
                elements = target.resolveFor(actor).thenFindAll(valuesEntered);
            }
            actor.attemptsTo(
                    Ensure.thatTheAnswerTo(isEmpty()).isTrue()
            );
        });
    }

    protected Question<String> value(){
        return Question.about("Input was successful").answeredBy(actor -> null);
    }

    private Question<Boolean> isEmpty(){
        return Question.about("is Input form empty").answeredBy(actor -> actor.asksFor(inputValues()).isEmpty());
    }

    private Question<List<String>> inputValues(){
        return Question.about("Values Entered").answeredBy(actor -> {
            List<WebElementFacade> inputs = target.resolveFor(actor).thenFindAll(valuesEntered);
            List<String> values = new ArrayList<>();
            for (WebElementFacade element: inputs){
                values.add(element.getText());
            }
            return values;
        });
    }


    private Question<Boolean> valuesEntered(){
        return Question.about("Values Successfully Entered").answeredBy(actor -> {
            List<String> Entered = actor.asksFor(inputValues());
            Entered.removeAll(input);
            return Entered.isEmpty();
        });
    }
}
