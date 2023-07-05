package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;


public class DragDrop implements Interaction {
    Target item;
    Target destination;

    public DragDrop(Target item) {
        this.item = item;
    }

    public DragDrop(Target item, Target destination) {
        this.item = item;
        this.destination = destination;
    }

    @Override
    @Step("{0} drags and drop item #item to #destination")
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).withAction().dragAndDrop(item.resolveFor(actor).getElement(), destination.resolveFor(actor).getElement()).perform();
    }

    public static DragDrop theItem(Target item) {
        return instrumented(DragDrop.class, item);
    }

    public DragDrop to(Target destination) {
        this.destination = destination;
        return this;
    }
}
