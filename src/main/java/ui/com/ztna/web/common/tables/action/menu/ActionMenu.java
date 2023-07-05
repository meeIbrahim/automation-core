package ui.com.ztna.web.common.tables.action.menu;

import constants.Waits;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.common.tables.Row;

/***
 * Class to handle action Menus for All Rows
 *
 */
public class ActionMenu {
    public static ActionRow For(Row row){
        return new ActionRow(row);
    }

    public static ActionTarget For(Target target){
        return new ActionTarget(target);
    }
    public static Question<Boolean> isOpen() {
        return Question.about("Is Action Menu Open").answeredBy(actor -> Action_Menu.POPOVER.resolveFor(actor).isPresent());
    }


}

class ActionTarget implements Action_Menu {
    private final Target target;
    public ActionTarget(Target target){
        this.target = target;
    }
    @Override
    public Performable clickDots() {
        return Task.where("{0} Clicks on Menu Dots",actor ->  {
            WebElementFacade actionElement = target.resolveFor(actor).findBy(ROW_MENU.getCssOrXPathSelector());
            actor.attemptsTo(
                    Click.on(actionElement)
            );
        });
    }

    @Override
    public Performable open() {
        return Task.where("{0} Opens Three Dot Menu",actor -> {
            WebElementFacade actionElement = target.resolveFor(actor).findBy(ROW_MENU.getCssOrXPathSelector());
            actor.attemptsTo(
                    Check.whether(isOpen()).otherwise(
                            Click.on(actionElement)
                    ),
                    Ensure.that(POPOVER).isDisplayed()

            );
        });
    }

    @Override
    public Performable close() {
        return Task.where("{0} Closes Three Dot Menu", actor -> {
            actor.attemptsTo(
                    Check.whether(isOpen()).andIfSo(
                            Click.on(POPOVER_OVERLAY)
                    ).then(
                            WaitUntil.the(POPOVER, WebElementStateMatchers.isNotPresent()).forNoMoreThan(Waits.FIFTEEN).seconds()
                    ),
                    Ensure.thatTheAnswerTo("Action Menu is Open", isOpen()).isFalse()
            );
        });
    }

    @Override
    public Performable action(Target Action) {
        return Task.where("{0} Clicks on ACTION".replace("ACTION",Action.getName()),actor -> {
            actor.attemptsTo(
                    open(),
                    selectAction(Action)
            );
        });
    }

}

class ActionRow implements Action_Menu{

    private final Row ROW;
    public ActionRow(Row ROW){
        this.ROW = ROW;
    }
    @Override
    public Performable action(Target Action) {
        return Task.where("{0} Clicks on ACTION for ROW".replace("ACTION",Action.getName()).replace("ROW",this.ROW.name()),actor -> {
            actor.attemptsTo(
                    open(),
                    selectAction(Action)
            );
        });
    }

    @Override
    public Performable open() {
        return Task.where("{0} Opens Three Dot Menu for ROW".replace("ROW",ROW.name()),actor -> {
            WebElementFacade actionElement = ROW.webElement(actor).findBy(ROW_MENU.getCssOrXPathSelector());
            actor.attemptsTo(
                    Check.whether(isOpen()).otherwise(
                            Click.on(actionElement)
                    ),
                    Ensure.that(POPOVER).isDisplayed()

            );
        });
    }
    @Override
    public Performable close(){
        return Task.where("{0} Closes Three Dot Menu for ROW".replace("ROW",ROW.name()),actor -> {
            actor.attemptsTo(
                    Check.whether(isOpen()).andIfSo(
                            Click.on(POPOVER_OVERLAY)
                    ).then(
                            WaitUntil.the(POPOVER, WebElementStateMatchers.isNotPresent()).forNoMoreThan(Waits.FIFTEEN).seconds()
                    ),
                    Ensure.thatTheAnswerTo("Action Menu is Open",isOpen()).isFalse()
            );
        });
    }

    @Override
    public Performable clickDots() {
        return Task.where("{0} Clicks on Menu Dots",actor ->  {
            WebElementFacade actionElement = ROW.webElement(actor).findBy(ROW_MENU.getCssOrXPathSelector());
            actor.attemptsTo(
                    Click.on(actionElement)
            );
        });
    }


}

