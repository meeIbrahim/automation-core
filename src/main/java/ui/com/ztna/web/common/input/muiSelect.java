package ui.com.ztna.web.common.input;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;

/**
 *  Provides Basic Implementation for Material UI based Select Elements such as Dropdown and Multi-Select
 */
public abstract class muiSelect implements Performable {
    public static final Target BY_ORDER = Target.the("Dropdown Generic")
            .locatedBy("(//*[contains(@class,\"select__control\")])[{0}]");
    public static final Target BY_LABEL = Target.the("Dropdown Generic")
            .locatedBy("//*[contains(@class,\"select__control\")][./ancestor::*[.//*[contains(text(),\"{0}\")]][1]]");
    public static final Target DROPDOWN_SELECTION_BY_TEXT = Target.the("Dropdown Selection")
            .locatedBy("//div[contains(@class,'react-select__menu-list')]/*[descendant-or-self::*[.='{0}']]");
    public static final Target DROPDOWN_SELECTIONS = Target.the("Dropdown Selection")
            .locatedBy("//div[contains(@class,'react-select__menu-list')]/*[descendant-or-self::*[text()]]");

    public static final Target DROPDOWN_SELECTION_BY_ORDER = Target.the("Dropdown Selection")
            .locatedBy("//div[contains(@class,'react-select__menu-list')]/*[{0}]");


    String Dropdown_Button = "//div[contains(@class,'dropdown')]";
    String IS_FOCUSED = "[contains(@class,\"menu-is-open\")]";
    Target target;
    public muiSelect(Target target){
        this.target = target;
    }
    public Performable open(){
        return Task.where("{0} opens Dropdown", actor -> {
            Target target = Target.the(this.target.getName())
                    .locatedBy(this.target.getCssOrXPathSelector() + Dropdown_Button);
            actor.attemptsTo(
                    Check.whether(isOpen()).otherwise(
                            Click.on(target)
                    ),
                    Ensure.thatTheAnswerTo(isOpen()).isTrue()
            );
        }).withNoReporting();
    }

    public Performable close(){
        return Task.where("{0} closes Dropdown",actor -> {
            Target target = Target.the(this.target.getName())
                    .locatedBy(this.target.getCssOrXPathSelector() + Dropdown_Button);
            actor.attemptsTo(
                    Check.whether(isOpen()).andIfSo(
                            Click.on(target)
                    ),
                    Ensure.thatTheAnswerTo(isOpen()).isFalse()
            );
        }).withNoReporting();
    }
    public Question<Boolean> isOpen(){
        return Question.about("Is dropdown open?").answeredBy(actor ->  {
            List<WebElementFacade> open = Target.the("Dropdown is focused")
                    .locatedBy(this.target.getCssOrXPathSelector()+IS_FOCUSED).resolveAllFor(actor);
            return (!open.isEmpty());

        });
    }

}
