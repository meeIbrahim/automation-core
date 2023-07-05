package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class MakeFetchRequest implements Interaction {
    String header;

    public MakeFetchRequest(String header) {
        this.header = header;
    }

    @Override
    @Step("{0} makes fetch request with header:\n#header")
    public <T extends Actor> void performAs(T actor) {
        ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver()).executeAsyncScript(
                "var callback = arguments[arguments.length - 1];" +
                        "fetch(" + header + ").then(response => callback())");
    }

    public static Interaction forThe(String header) {
        return instrumented(MakeFetchRequest.class, header);
    }
}
