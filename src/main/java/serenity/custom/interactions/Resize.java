package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Resize implements Interaction {
    Integer width;
    Integer height;
    Target element;

    public Resize(Target element) {
        this.element = element;
    }

    @Override
    @Step("{0} resizes #element to width: #width and height: #height")
    public <T extends Actor> void performAs(T actor) {
        WebElement webElement = element.resolveFor(actor).getElement();
        BrowseTheWeb.as(actor).evaluateJavascript("arguments[0].setAttribute('style', arguments[0].getAttribute('style')+'height: " + height + "px;width:" + width + "px;')", webElement);
    }

    public static Resize theElement(Target element) {
        return instrumented(Resize.class, element);
    }

    public Resize toWidthOf(Integer width) {
        this.width = width;
        return this;
    }

    public Resize toHeightOf(Integer height) {
        this.height = height;
        return this;
    }

}
