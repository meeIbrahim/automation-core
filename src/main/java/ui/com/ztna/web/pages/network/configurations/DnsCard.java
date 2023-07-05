package ui.com.ztna.web.pages.network.configurations;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;


import java.util.List;

public class DnsCard {
    public static Target DNS = Target.the("DNS Card").locatedBy("//div[contains(@class,\"MuiCard\")]");
    public static Target POINTER = Target.the("Expand Pointer")
            .locatedBy(DNS.getCssOrXPathSelector() + "//div[./*[local-name()=\"svg\"]]");

    public static Target PRIMARY_DNS = Input.BY_ORDER.of("1").called("Primary DNS Input");
    public static Target SECONDARY_DNS = Input.BY_ORDER.of("2").called("Secondary DNS Input");

    public static Target RESET_BUTTON = Target.the("Reset Button in DNS")
            .locatedBy(DNS.getCssOrXPathSelector() + "//button[1]");
    public static Target UPDATE_BUTTON = Target.the("Update Button in DNS")
            .locatedBy(DNS.getCssOrXPathSelector() + "//button[2]");
    public static Target REMOVE_BUTTON = Target.the("Remove DNS")
            .locatedBy(DNS.getCssOrXPathSelector() + "//*[./*[local-name()=\"svg\"]][.//following-sibling::p]");

    public Performable expand(){
        return Task.where("{0} Expands DNS Card",actor -> {
            actor.attemptsTo(
                    Check.whether(isExpanded()).otherwise(
                            Click.on(POINTER),
                            Wait.forVisibilityOf(UPDATE_BUTTON)
                    ),
                    Ensure.thatTheAnswerTo(isExpanded()).isTrue()
            );

        });
    }

    public Performable collapse(){
        return Task.where("{0} Collapse DNS Card",actor -> {
            actor.attemptsTo(
                    Check.whether(isExpanded()).andIfSo(
                            Click.on(POINTER)
                    ),
                    Ensure.thatTheAnswerTo(isExpanded()).isFalse()
            );

        });
    }


    public Question<Boolean> isExpanded(){
        Target Divisions = Target.the("Sub Divisions in DNS Card")
                .locatedBy(DNS.getCssOrXPathSelector() + "/div");
        return Question.about("Is DNS Card Expanded").answeredBy(actor -> {
            List<WebElementFacade> elements = Divisions.resolveAllFor(actor);
            return (elements.size() == 2);
        });
    }

    public Question<Boolean> isDnsSet(){
        return Question.about("Is DNS Set")
                .answeredBy(actor -> {
                    List<WebElementFacade> elements = REMOVE_BUTTON.resolveAllFor(actor);
                    return !elements.isEmpty();
                });
    }
}
