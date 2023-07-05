package ui.com.ztna.web.common.input;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import ui.com.ztna.web.common.Wait.Wait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitPresenceOf;

public class Dropdown extends muiSelect {

    String LOADER = "//div[contains(@class,'loadingIndicator')]";
    String Text_Element = ".//*[text()]";

    Target Selection;
    public Dropdown(Target target,Target Selection){
        super(target);
        this.Selection = Selection;
    }
    private Dropdown(Target target){
        super(target);
    } // temporary Constructor
    protected Dropdown(){super(null);} // Serenity suggests having an empty Constructor

    public static Dropdown of(Target target){
        return new Dropdown(target);
    }
    public Dropdown select(String Selection){
        return Instrumented.instanceOf(Dropdown.class).withProperties(target,DROPDOWN_SELECTION_BY_TEXT.of(Selection)
                .called("Dropdown Selection for " + Selection));
    }
    public Dropdown select(Integer Selection){
        return Instrumented.instanceOf(Dropdown.class).withProperties(target,DROPDOWN_SELECTION_BY_ORDER.of(String.valueOf(Selection))
                .called("Dropdown Selection of " + Selection));
    }
    @Override
    @Step("{0} Selects #Selection for #target")
    public <T extends Actor> void performAs(T actor) {


        actor.attemptsTo(
                Wait.forInvisibilityOf(Target.the("Dropdown Loader").locatedBy(this.target.getCssOrXPathSelector() + LOADER))
                        .forTime(Duration.ofSeconds(10)),
                open(),
                Scroll.to(Selection));
        String toSelect = Selection.resolveFor(actor).getText();
        actor.attemptsTo(
                Click.on(Selection),
                close(),
                Ensure.thatTheAnswerTo(selected()).isEqualTo(toSelect)
        );
    }



    public Question<String> selected(){
        return Question.about("Selected Value in TARGET".replace("TARGET",target.getName())).answeredBy(actor -> target.resolveFor(actor).findBy(Text_Element).getText());
    }
    public Question<List<String>> contains(){
        return Question.about("Available Values in Dropdown").answeredBy(
                actor -> {
                    actor.attemptsTo(
                            Wait.forInvisibilityOf(Target.the("Dropdown Loader").locatedBy(this.target.getCssOrXPathSelector() + LOADER))
                                    .forTime(Duration.ofSeconds(10)),
                            open()
                    );
                    List<WebElementFacade> elements = target.resolveFor(actor).thenFindAll(DROPDOWN_SELECTIONS.getCssOrXPathSelector());
                    List<String> items = new ArrayList<>();
                    for (WebElementFacade element:elements){
                        items.add(element.getText());
                    }
                    actor.attemptsTo(
                            close()
                    );
                    return items;
                }
        );
    }


}