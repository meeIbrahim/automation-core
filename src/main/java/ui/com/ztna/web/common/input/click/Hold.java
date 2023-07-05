package ui.com.ztna.web.common.input.click;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class Hold implements Performable{

    Target target;
    Target destination;
    Actions actions = null;
    boolean held = false;
    Duration timeout = Duration.ofMillis(200);

    Hold(Target target){
        this.target = target;
    }

    public Hold(Target target,Target destination){
        this.target = target;
        this.destination = destination;
    }

    /**
     * Empty Constructor as required by Serenity
     */
    Hold(){
        this.target = null;
    }
    public static Hold target(Target target){
        return new Hold(target);
    }
    public Hold dragTo(Target destination){
        return Instrumented.instanceOf(Hold.class).withProperties(target,destination);
    }

    private Actions actions(Actor actor){
        if (actions == null){this.actions = new Actions(BrowseTheWeb.as(actor).getDriver());}
        return actions;
    }

    private Performable holdElement(){
        return Task.where("{0} clicks and hold",actor -> {
            if (!held){
                actions(actor).moveToElement(target.resolveFor(actor)).pause(timeout).clickAndHold().perform();
                held = true;
            }
        }).withNoReporting();
    }
    public Performable move(Target destination){
        return Task.where("{0} moves cursor",actor -> {
           actor.attemptsTo(holdElement());
            WebElementFacade destinationElement = destination.resolveFor(actor);
           int x = destinationElement.getLocation().x;
           int y = destinationElement.getLocation().y;
           actions(actor)
                   .pause(timeout)
                   .moveByOffset(x,y)
                   .moveToElement(destinationElement)
                   .moveByOffset(x,y)
                   .pause(timeout)
                   .perform();
        });
    }
    public Performable release(){
        return Task.where("{0} releases held cursor click",actor -> {
            actor.attemptsTo(holdElement());
            actions(actor).release().pause(timeout).perform();
            held = false;
        });
    }


    @Override
    @Step("{0} holds and drags element")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                move(destination),
                release()
        );
    }
}
