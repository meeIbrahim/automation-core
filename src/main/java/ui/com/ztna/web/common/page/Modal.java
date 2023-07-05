package ui.com.ztna.web.common.page;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;

import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.Wait.Wait;


import java.time.Duration;
import java.util.List;

import static ui.com.ztna.web.common.interactions.CommonInteraction.*;


public class Modal {

    public static Performable close(){
        return Task.where("Close the modal",actor -> {
           actor.attemptsTo(
                   Check.whether(isOpen())
                           .andIfSo(clickButton(MODAL_CLOSE_BUTTON)),
                   Wait.forInvisibilityOf(MODAL_BOX).forTime(Duration.ofSeconds(30))
                   );
        });
    }

    public static Performable next(){
        return Task.where(" Traverse next in modal",actor -> {
            actor.attemptsTo(
                    clickButton(MODAL_NEXT_BUTTON)
            );
        });
    }

    public static Performable previous(){
        return Task.where("Traverse previous in modal",actor -> {
            actor.attemptsTo(
                    clickButton(MODAL_PREVIOUS_BUTTON)
            );
        });
    }

    public static Performable save(){
        return Task.where("Save modal",actor -> {
            actor.attemptsTo(
                    Modal.next(),
                    Wait.forInvisibilityOf(MODAL_BOX).forTime(Duration.ofSeconds(30))
            );
        });
    }

    public static Performable cancel(){
        return Task.where("Cancel modal",actor -> {
            actor.attemptsTo(
                    Modal.previous(),
                    Wait.forInvisibilityOf(MODAL_BOX).forTime(Duration.ofSeconds(30))
            );
        });
    }
    public static Question<Boolean> isOpen(){
        return Question.about("Is Modal Open").answeredBy(
                actor -> {
                    List<WebElementFacade> elements = MODAL_BOX.resolveAllFor(actor);
                    return !elements.isEmpty();
                }
        );
    }

    public static Question<String> title(){
        return Question.about("Title of the Modal").answeredBy(actor -> {
            String title = "";
            List<WebElementFacade> elements = Modal.MODAL_TITLE.resolveAllFor(actor);
            if (!elements.isEmpty()){title = elements.get(0).getText();}
            return title;
        });
    }

    public static Performable waitUntilOpen(){
        return Task.where("{0} waits for Modal to open",actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(Modal.MODAL_BOX)
                            .forTime(Duration.ofSeconds(5)),
                    Wait.forVisibilityOf(Modal.MODAL_CONTENT)
                            .forTime(Duration.ofSeconds(5)),
                    Wait.forInvisibilityOf(Modal.MODAL_LOAD_ICON)
                            .forTime(Duration.ofSeconds(10))
            );
        }).withNoReporting();
    }



    public static Target MODAL_LABEL = Target.the("Modal label")
            .locatedBy("//div[contains(@class,'MuiDialogTitle')][.='{0}']");

    public static Target MODAL_DEFAULT_LABEL = Target.the("Modal default label")
            .locatedBy("//div[contains(@class,'MuiDialogTitle')]");

    public static Target MODAL_BOX = Target.the("Modal box")
            .locatedBy("//div[@role='dialog']").waitingForNoMoreThan(Duration.ofSeconds(5));

    public static Target MODAL_LOAD_ICON = Target.the("Loading Icon inside Modal")
            .locatedBy(MODAL_BOX.getCssOrXPathSelector() + "//*[contains(@data-testid,\"loading\")]");

    public static Target MODAL_CONTENT = Target.the("Content of Modal")
            .locatedBy(MODAL_BOX.getCssOrXPathSelector() + "//div[contains(@class,'MuiDialogContent')]");
    public static Target MODAL_FOOTER = Target.the("Modal Footer")
            .locatedBy(MODAL_BOX.getCssOrXPathSelector() + "//div[contains(@class,\"MuiDialogActions-spacing\")]");

    public static Target MODAL_FOOTER_BUTTONS = Target.the("Buttons in Modal Footer")
            .locatedBy(MODAL_FOOTER.getCssOrXPathSelector() + "//button");

    public static Target MODAL_PREVIOUS_BUTTON = Target.the("Modal previous button")
            .locatedBy(MODAL_FOOTER_BUTTONS.getCssOrXPathSelector() + "[1]");
    public static Target MODAL_NEXT_BUTTON = Target.the("Modal next button")
            .locatedBy(MODAL_FOOTER_BUTTONS.getCssOrXPathSelector() + "[2]");

    public static Target MODAL_CLOSE_BUTTON = Target.the("Modal close button")
            .locatedBy("//div[@role='dialog']/div[1]//div[@role=\"button\"]");
    public static Target MODAL_TITLE = Target.the("Title of Modal")
            .locatedBy("//div[@role='dialog']//*[@data-testid=\"header-typography\"]");

    Target INPUT_FIELD = Target.the("Input field")
            .locatedBy("//form/div[{0}]//div[contains(@class,'MuiInput')]");

    Target DROPDOWN_INDICATOR = Target.the("Drop down indicator")
            .locatedBy("//form/div[{0}]//div[contains(@class,'dropdown')]");

    Target DROPDOWN_OPTION = Target.the("Dropdown option")
            .locatedBy("//div[contains(@class,'react-select__menu-list')]//*[contains(text(),'{0}')]");

    Target MULTI_VALUE_INPUT_VALUE =Target.the("Multi-value input value")
            .locatedBy("//div[contains(@class,'select__multi-value')][.='{0}'] | //div[contains(@class,'MuiChip-deletable')][.='{0}']//*[local-name()='svg' and @class='MuiSvgIcon-root MuiChip-deleteIcon'] | //div[contains(@class,'select__multi-value')]//p[.='{0}']");




}
