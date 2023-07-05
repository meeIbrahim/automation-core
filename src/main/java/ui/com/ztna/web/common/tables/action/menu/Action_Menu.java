package ui.com.ztna.web.common.tables.action.menu;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.Wait.Wait;

import java.util.List;

public interface Action_Menu {
    Target ROW_MENU = Target.the("Row Action Menu")
            .locatedBy(".//*[contains(@id,\"action\")]");
    Target ACTION_BUTTONS = Target.the("Action Menu Buttons")
            .locatedBy("(//*[contains(@class,\"MuiPopover\")]//div[@id])");

    Target ACTION_BUTTON = Target.the("Action Menu Button")
            .locatedBy(ACTION_BUTTONS.getCssOrXPathSelector() + "[{0}]");
    Target ACTION_BUTTON_LAST = Target.the("Last Action Menu Button")
            .locatedBy(ACTION_BUTTONS.getCssOrXPathSelector() + "[last()]");

    /**
     * EXAMPLE FOR USING ACTION BUTTON
     * public static final Target UPDATE_BUTTON = ACTION_BUTTON.of("1").called("Update Button");
     */
    Target POPOVER = Target.the("Popover Menu")
            .locatedBy("//*[contains(@class,\"MuiPopover\") and not(@role)]");
    Target POPOVER_OVERLAY = Target.the("Popover Overlay")
            .locatedBy("//*[@class=\"MuiPopover-root\"]");

    /// Lowest Level Tasks
    /// Click on Action Dots
    Performable clickDots();

    /// Click on Action Options
    default Performable selectAction(Target Action) {
        return Task.where("{0} clicks on ACTION".replace("ACTION", Action.getName()), actor -> {
            actor.attemptsTo(
                    Click.on(Action),
                    Wait.forInvisibilityOf(POPOVER)
            );
        });
    }

    /// High level Tasks
    /// Open Menu
    Performable open();

    /// Close Menu
    Performable close();

    ///  Task that can directly take an action
    Performable action(Target Action);


    /// Questions
    default Question<Boolean> isOpen() {
        return Question.about("Is Action Menu Open").answeredBy(actor -> POPOVER.resolveFor(actor).isPresent());
    }

//    default Question<Boolean> isEnabled(Target Action){
//        return Question.about("Action Button is Enabled".replace("Action",Action.getName())).answeredBy(
//                actor -> Action.resolveFor(actor).getAttribute("aria-disabled").equals("false")
//        );
//    }

    default Question<Boolean> isEnabled(Target Action){
        return Question.about("Action is Enabled".replace("Action",Action.getName())).answeredBy(
                actor -> {
                    actor.attemptsTo(
                            open()
                    );
                    return Action.resolveFor(actor).getAttribute("aria-disabled").equals("false");
                }
        );
    }

    default Question<Integer> availableActions(){
        return Question.about("Total available actions").answeredBy(actor -> {
            actor.attemptsTo(open());
            List<WebElementFacade> actions =  ACTION_BUTTONS.resolveAllFor(actor);
            actor.attemptsTo(close());
            return actions.size();
        });
    }

    default Question<Boolean> contains(Target Action){
        return Question.about("Menu contains action").answeredBy(actor -> {
            actor.attemptsTo(
                    open()
            );
            return !Action.resolveAllFor(actor).isEmpty();
        });
    }
}
