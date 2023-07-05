package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SetAttribute implements Interaction {
    String attributeName;
    String attributeValue;
    Target target;

    public SetAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    public static SetAttribute named(String attributeName) {
        return new SetAttribute(attributeName);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        WebDriver driver = BrowseTheWeb.as(t).getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", target.resolveFor(t).getElement(), attributeName, attributeValue);
    }

    public SetAttribute to(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public SetAttribute forTargetNamed(Target target) {
        this.target = target;
        return this;
    }
}
