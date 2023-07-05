package ui.com.ztna.web.common.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.MultiSelect;

import java.util.ArrayList;
import java.util.List;

import static constants.Waits.SIXTY;
import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static postgresql.databases.AuthDb.elementPresent;
import static ui.com.ztna.web.common.user_interface.CommonUI.*;
import static ui.com.ztna.web.common.user_interface.NavBarUI.REFRESH_BUTTON;
import static ui.com.ztna.web.common.user_interface.RemoveResourcesUI.*;
import static ui.com.ztna.web.common.user_interface.SideBarUI.*;

public class CommonInteraction {

    public static Performable removeAllFieldsInWorkSpace(){
        return Task.where("User removes all fields from the workspace",actor -> {
            while((Presence.of(FIRST_ROW_NAME).asABoolean().answeredBy(actor))){

                String name = FIRST_ROW_NAME.resolveFor(actor).getText();

                actor.attemptsTo(
                        removeField(First_ROW_ACTION_ICON.of(name),REMOVE_POPOVER_BUTTON,REMOVE_FORM_TITLE,REMOVE_MODAL_BUTTON, name)
                );
            }
        });
    }


    public static Performable removeAllIntegrationsInWorkSpace(){
        return Task.where("User removes all fields from the workspace",actor -> {


            while((Presence.of(INTEGRATIONS_FIRST_ROW_NAME).asABoolean().answeredBy(actor))){

                String name = INTEGRATIONS_FIRST_ROW_NAME.resolveFor(actor).getText();

                actor.attemptsTo(
                        removeIntegration(First_ROW_ACTION_ICON.of(name),REMOVE_POPOVER_BUTTON,REMOVE_FORM_TITLE,REMOVE_MODAL_BUTTON, name)
                );
            }
        });
    }


    public static Performable removeField(Target actionButton, Target popOverButton, Target modalTitle, Target modalRemoveButton,String name){
        return Task.where("user removes field from the workspace",actor -> {

            actor.attemptsTo(clickButton(actionButton));
            if (Presence.of(FORCE_REMOVE_POP_OVER_BUTTON).asABoolean().answeredBy(actor)){
                actor.attemptsTo(forceDelete(actionButton,name));
            }else {
                actor.attemptsTo(
                        Click.on(PAGE_BODY),
                        openDeleteFieldModal(actionButton, popOverButton, modalTitle, modalRemoveButton),
                        deleteFieldFromModal(modalTitle, modalRemoveButton),
                        verifyFieldIsDeleted(actionButton, name)
                );
            }

        });
    }

    public static Performable removeIntegration(Target actionButton, Target popOverButton, Target modalTitle, Target modalRemoveButton,String name){
        return Task.where("user removes field from the workspace",actor -> {

            actor.attemptsTo(clickButton(actionButton));
            if (Presence.of(FORCE_REMOVE_POP_OVER_BUTTON).asABoolean().answeredBy(actor)){
                actor.attemptsTo(forceDelete(actionButton,name));
            }else {
                actor.attemptsTo(
                        Click.on(PAGE_BODY),
                        openDeleteFieldModal(actionButton, popOverButton, modalTitle, modalRemoveButton),
                        deleteFieldFromModal(modalTitle, modalRemoveButton,FORCE_REMOVE_NAME_INPUT,name),
                        verifyFieldIsDeleted(actionButton, name)
                );
            }

        });
    }



    public static Performable openDeleteFieldModal(Target actionButton, Target popOverButton, Target modalTitle, Target modalRemoveButton){
        return Task.where("User opens delete form",actor -> {
            actor.attemptsTo(
                    openActionIconMenu(actionButton),
                    clickButton(popOverButton),
                    waitPresenceOf(modalTitle),
                    waitPresenceOf(modalRemoveButton)

            );
        });
    }

    public static Performable deleteFieldFromModal(Target modalTitle, Target modalRemoveButton){
        return Task.where("User deletes the field using the delete modal",actor -> {
            actor.attemptsTo(
                    clickButton(modalRemoveButton),
                    waitNotPresenceOf(modalRemoveButton),
                    waitNotPresenceOf(modalTitle)
            );
        });
    }

    public static Performable deleteFieldFromModal(Target modalTitle, Target modalRemoveButton, Target input ,String inputString){
        return Task.where("User deletes the field using the delete modal",actor -> {
            actor.attemptsTo(
//                    fillInputField(input,inputString),
                    clickButton(modalRemoveButton),
                    waitNotPresenceOf(modalRemoveButton),
                    waitNotPresenceOf(modalTitle)
            );
        });
    }

    public static Performable verifyFieldIsDeleted(Target fieldToBeDeleted, String name){
        return Task.where("Verify field is deleted",actor -> {

               boolean fDelete = ! (waitUntilNotPresentBol(fieldToBeDeleted,60,5));
               if (fDelete){

                   actor.attemptsTo(
                           clickButton(fieldToBeDeleted),
                           forceDelete(fieldToBeDeleted,name)
                   );
               }
               actor.attemptsTo(waitNotPresenceOf(fieldToBeDeleted));

        });
    }

    public static Performable verifyFieldIsDeleted(Target fieldToBeDeleted, int waitTime){
        return Task.where("Verify field is deleted",actor -> {
            actor.attemptsTo(
                    waitNotPresenceOf(fieldToBeDeleted,waitTime)
            );
        });
    }

    public static Performable forceDelete(Target actionIcon,String name){
        return Task.where("Force delete element",actor -> {
            if (Presence.of(FORCE_REMOVE_POP_OVER_BUTTON).asABoolean().answeredBy(actor)) {
                for (int i = 0; i < 3; i++) {

                    if (!(Presence.of(FORCE_REMOVE_POP_OVER_BUTTON_DISABLED).asABoolean().answeredBy(actor))) {
                        break;
                    } else {
                        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                        actor.attemptsTo(waitTime(SIXTY));
                        driver.navigate().refresh();
                        actor.attemptsTo(clickButton(actionIcon));
                    }

                }
                actor.attemptsTo(
                        clickButton(FORCE_REMOVE_POP_OVER_BUTTON),
                        fillInputField(FORCE_REMOVE_NAME_INPUT, name),
                        clickButton(FORCE_REMOVE_MODAL_BUTTON),
                        waitNotPresenceOf(FORCE_REMOVE_MODAL_BUTTON),
                        waitNotPresenceOf(actionIcon)
                );
            } else {
                actor.attemptsTo(clickButton(PAGE_BODY));
            }
        });

    }


    public static Performable openActionIconMenu(Target actionButton){
        return Task.where("Open action icon menu",actor -> {

            actor.attemptsTo(
                    clickButton(actionButton)
            );

        });
    }

    public static Performable clickButton(Target button, int time){

        return Task.where("Click button:"+button,actor -> {
            actor.attemptsTo(
                    waitPresenceOf(button,time),
                    waitClickableFor(button,time),
                    Click.on(button)
            );

        });

    }

    public static Performable clickButton(Target button){

        return Task.where("Click button:"+button,actor -> {
           actor.attemptsTo(
                   waitPresenceOf(button),
                   waitClickableFor(button),
                   Click.on(button)
           );

        });

    }

    public static Performable waitPresenceOf(Target locator, int waitTime){

        return Task.where("Wait presence of field: "+locator,actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isPresent()).forNoMoreThan(waitTime).seconds());
        });

    }

    public static Performable RefreshAndWaitPresenceOf(Target locator, int waitTime){

        return Task.where("Wait presence of field: "+locator,actor -> {
            int interval=waitTime/60;
            for (int i = 1; i <= interval; i++){
                actor.attemptsTo(
                        OpenPage.named(CLOUD_HOSTED_RELAYS_PAGE).in(RESOURCES_MENU),
                        OpenPage.named(SERVICE_CONNECTORS_PAGE).in(RESOURCES_MENU));
            if(Presence.of(locator).asABoolean().answeredBy(theActorInTheSpotlight()))
                break;
            else {
                actor.attemptsTo(clickButton(REFRESH_BUTTON),waitTime(SIXTY));
            }
            }
//            actor.attemptsTo(WaitUntil.the(locator,isPresent()).forNoMoreThan(waitTime).seconds());
        });

    }

    public static Performable waitPresenceOf(Target locator){

        return Task.where("Wait presence of field: "+locator,actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isPresent()).forNoMoreThan(THIRTY).seconds());
        });

    }

    public static Performable waitNotPresenceOf(Target locator, int waitTime){

        return Task.where("Wait until field "+locator+" is not present",actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isNotPresent()).forNoMoreThan(waitTime).seconds());
        });

    }

    public static Performable waitNotPresenceOf(Target locator){

        return Task.where("Wait until field "+locator+" is not present",actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isNotPresent()).forNoMoreThan(THIRTY).seconds());
        });

    }

    public static Performable waitClickableFor(Target locator,int waitTime){

        return Task.where("Waituntil field isclickable",actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isClickable()).forNoMoreThan(waitTime).seconds());
        });

    }

    public static Performable waitClickableFor(Target locator){

        return Task.where("Waituntil field isclickable",actor -> {
            actor.attemptsTo(WaitUntil.the(locator,isClickable()).forNoMoreThan(THIRTY).seconds());
        });

    }

    public static Performable waitTime(int time){

        return Task.where("wait for {0} secs ",actor -> {
            try {
                Thread.sleep(time* 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static Performable fillInputField(Target inputTarget, String input, int time){
        return Task.where("Fill input field",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(inputTarget,isEnabled()).forNoMoreThan(time).seconds(),
                    Enter.keyValues(input).into(inputTarget)
            );
        });
    }

    public static Performable fillInputField(Target inputTarget, String input){
        return Task.where("Fill input field",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(inputTarget,isEnabled()).forNoMoreThan(THIRTY).seconds(),
                    Enter.keyValues(input).into(inputTarget).thenHit(Keys.ENTER)
            );
        });
    }


    public static Performable fillInput(Target inputTarget, String input){
        return Task.where("Fill input field",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(inputTarget,isEnabled()).forNoMoreThan(THIRTY).seconds(),
                    Enter.keyValues(input).into(inputTarget)
            );
        });
    }

    public static Performable selectDropDownInputField(Target inputTarget,Target dropDownTarget, String parameter){
        return Task.where("Select a drop down field in a modal using input",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(inputTarget,isEnabled()).forNoMoreThan(THIRTY).seconds(),
                    Enter.keyValues(parameter).into(inputTarget),
                    WaitUntil.the(dropDownTarget.of(parameter),isPresent()).forNoMoreThan(THIRTY).seconds(),
                    Click.on(dropDownTarget.of(parameter))
            );
        });
    }

    public static Performable selectDropDownInputField(Target inputTarget, String parameter){
        return Task.where("Select a drop down field in a modal using input",actor -> {
            actor.attemptsTo(
                    WaitUntil.the(inputTarget,isEnabled()).forNoMoreThan(THIRTY).seconds(),
                    Enter.keyValues(parameter).into(inputTarget),
                    WaitUntil.the(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(parameter),isPresent()).forNoMoreThan(THIRTY).seconds(),
                    Click.on(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(parameter))
            );
        });
    }

    public static Performable selectDropDownField(Target dropDownTarget, String parameter){
        return Task.where("Select a drop down field {0} in a modal using drop-down button",actor -> {
            actor.attemptsTo(
                    clickButton(dropDownTarget),
                    Scroll.to(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(parameter)),
                    clickButton(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(parameter))
            );
        });
    }

    public static Performable waitUntilPresent(Target locator,int loopCount, int loopWait){
        return Task.where("Wait until target is present",actor -> {


            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            for (int count=0;count<loopCount;count++) {
                theActorInTheSpotlight().attemptsTo(
                        Check.whether(Presence.of(locator).asABoolean().answeredBy(actor)).otherwise(waitTime(loopWait))
                );
                driver.navigate().refresh();
                Boolean indicator = Presence.of(locator).asABoolean().answeredBy(actor);
                if (indicator) {
                    break;
                }
            }
            theActorInTheSpotlight().attemptsTo(WaitUntil.the(locator, isPresent()).forNoMoreThan(THIRTY).seconds());

        });
    }

    public static Performable waitUntilPresent(Target locator, Target subpage, int loopCount, int loopWait){
        return Task.where("Wait until target is present",actor -> {


            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            for (int count=0;count<loopCount;count++) {
                theActorInTheSpotlight().attemptsTo(
                        Check.whether(Presence.of(locator).asABoolean().answeredBy(actor)).otherwise(waitTime(loopWait))
                );
                driver.navigate().refresh();
                actor.attemptsTo(
                        WaitUntil.the(subpage,isPresent()).forNoMoreThan(THIRTY).seconds(),
                        Click.on(subpage)
                );
                Boolean indicator = Presence.of(locator).asABoolean().answeredBy(actor);
                if (indicator) {
                    break;
                }
            }
            theActorInTheSpotlight().attemptsTo(WaitUntil.the(locator, isPresent()).forNoMoreThan(THIRTY).seconds());

        });
    }

    public static boolean waitUntilNotPresentBol(Target locator, int loopCount, int loopWait){

        for (int count=0;count<loopCount;count++) {
            theActorInTheSpotlight().attemptsTo(
                    Check.whether(!(Presence.of(locator).asABoolean().answeredBy(theActorInTheSpotlight()))).otherwise(waitTime(loopWait))
            );
            Boolean indicator = Presence.of(locator).asABoolean().answeredBy(theActorInTheSpotlight());
            if (!indicator) {
                break;
            }
        }
        return(!(Presence.of(locator).asABoolean().answeredBy(theActorInTheSpotlight())));

    }

    public static Performable refreshPage(){
        return Task.where("Refresh page", actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            driver.navigate().refresh();


        });
    }

    public static Performable waitUntilNotPresent(Target locator, int loopCount, int loopWait){
        return Task.where("Wait until target is not present",actor -> {


            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            for (int count=0;count<loopCount;count++) {
                theActorInTheSpotlight().attemptsTo(
                        Check.whether(!(Presence.of(locator).asABoolean().answeredBy(actor))).otherwise(waitTime(loopWait))
                );
                driver.navigate().refresh();
                Boolean indicator = Presence.of(locator).asABoolean().answeredBy(actor);
                if (!indicator) {
                    break;
                }
            }
            theActorInTheSpotlight().attemptsTo(WaitUntil.the(locator, isNotPresent()).forNoMoreThan(THIRTY).seconds());

        });
    }

    public static Performable waitUntilNotPresent(Target locator, Target subpage, int loopCount, int loopWait){
        return Task.where("Wait until target is not present",actor -> {


            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            for (int count=0;count<loopCount;count++) {
                theActorInTheSpotlight().attemptsTo(
                        Check.whether(!(Presence.of(locator).asABoolean().answeredBy(actor))).otherwise(waitTime(loopWait))
                );
                driver.navigate().refresh();
                actor.attemptsTo(
                        WaitUntil.the(subpage,isPresent()).forNoMoreThan(THIRTY).seconds(),
                        Click.on(subpage)
                );
                Boolean indicator = Presence.of(locator).asABoolean().answeredBy(actor);
                if (!indicator) {
                    break;
                }
            }
            theActorInTheSpotlight().attemptsTo(WaitUntil.the(locator, isNotPresent()).forNoMoreThan(THIRTY).seconds());

        });
    }



    public static Performable clearField(Target field){

        return Task.where("Clear field",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(field),
                    Clear.field(field)
            );

        });

    }

    public static Performable chosePopOverButton(String popOverButton , Target label){
        return Task.where("Chose popover button {0}",actor -> {

            actor.attemptsTo(
                    clickButton(POPOVER_BUTTON.of(popOverButton)),
                    waitPresenceOf(label)
            );


        });
    }

    public static Performable choosePopOverMenu(Target actionIconButton, String popOverButton, String label){
        return Task.where("Chose popover button {0}",actor -> {

            actor.attemptsTo(
                    openActionIconMenu(actionIconButton),
                    clickButton(POPOVER_BUTTON.of(popOverButton)),
                    waitPresenceOf(MODAL_LABEL.of(label))
            );


        });
    }

    public static Performable openEndUserPortal(){
        return Task.where("Open end user portal",actor -> {
            actor.attemptsTo(
                    clickButton(END_USER_PORTAL_BUTTON),
                    closeAndSwitchToNextTab(),
                    waitPresenceOf(TENANT_ADMIN_PORTAL_BUTTON)
                   
            );
        });
    }

    public static Performable openAdminPortal(){
        return Task.where("Open admin portal",actor -> {
            actor.attemptsTo(
                    clickButton(TENANT_ADMIN_PORTAL_BUTTON),
                    closeAndSwitchToNextTab(),
                    waitPresenceOf(END_USER_PORTAL_BUTTON)

            );
        });
    }


    public static Performable openNewWindow(){
        return Task.where("Open new window",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            ((JavascriptExecutor) driver).executeScript("window.open()");
        });
    }

    public static Performable switchToNextTab(){
        return Task.where("switch to next tab",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

        });
    }
    public static Performable closeAndSwitchToNextTab(){
        return Task.where("close current tab and switch to next tab",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(1));

        });
    }

    public static Performable closeAndSwitchToPreviousTab(){
        return Task.where("close current tab and return to previous tab",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        });
    }

    public static Performable switchToPreviousTab(){
        return Task.where("switch to previous tab",actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(0));
        });
    }

    public static Performable verifyDropdownOptions(Target dropdown, List<String> options){
        return Task.where("Verify drop-down options",actor -> {
            actor.attemptsTo(
                    clickButton(dropdown)
            );
            for(String option : options) {
                actor.attemptsTo(
                        Scroll.to(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(option)),
                        WaitUntil.the(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(option),isVisible())
                );
            }
            actor.attemptsTo(
                    clickButton(dropdown)
            );
        });
    }

   public static Performable verifyPreselectedValueOfDropDown (Target preselectLocator , Target dropDownButton, String value){
        return Task.where("Verify preselected value of dropdown",actor -> {
            actor.attemptsTo(
                    waitPresenceOf(preselectLocator.of(value)),
                    clickButton(dropDownButton),
                    waitPresenceOf(Dropdown.DROPDOWN_SELECTION_BY_TEXT.of(value)),
                    clickButton(dropDownButton)
            );
        });
   }

    public static Performable addMultipleValuesIntoInput(Target inputField, Target dropDownTarget, List<String> values, Target modalLabel){
        return Task.where("Add Multiple values into input",actor -> {
            for (String value : values){
                actor.attemptsTo(
                        selectDropDownInputField(inputField,dropDownTarget,value)
                );
            }
            if(!values.isEmpty()) {
                actor.attemptsTo(Click.on(modalLabel));
            }
        });
    }

    public static Performable addMultipleValuesIntoInput(Target inputField, List<String> values){
        return Task.where("Add Multiple values into input",actor -> {
            for (String value : values){
                actor.attemptsTo(
                        fillInputField(inputField,value)
                );
            }

        });
    }

    public static Performable removeMultipleValuesFromInput(List<String> values){
        return Task.where("Remove multiple values from input",actor -> {
            for (String value : values){

                actor.attemptsTo(
                        Scroll.to(MultiSelect.DROPDOWN_SELECTION_BY_TEXT.of(value)),
                        WaitUntil.the(MultiSelect.DROPDOWN_SELECTION_BY_TEXT.of(value),isClickable()).forNoMoreThan(THIRTY).seconds(),
                        Click.on(MultiSelect.DROPDOWN_SELECTION_BY_TEXT.of(value)),
                        WaitUntil.the(MultiSelect.DROPDOWN_SELECTION_BY_TEXT.of(value),isNotPresent()).forNoMoreThan(THIRTY).seconds()
                );

            }
        });
    }

    public static Performable verifyMultipleValuesArePresent(List<String>values){
        return Task.where("Verify multiple values are present",actor -> {
            for(String value : values){
                actor.attemptsTo(WaitUntil.the(MultiSelect.DROPDOWN_SELECTION_BY_TEXT.of(value),isPresent()).forNoMoreThan(THIRTY).seconds());
            }
        });
    }

    public static Performable verifyMultipleValuesAreNotPresent(List<String>values){
        return Task.where("Verify multiple values are not present",actor -> {
            for (String value : values){
                actor.attemptsTo(WaitUntil.the(MultiSelect.DROPDOWN_SELECTION_BY_TEXT,isNotPresent()).forNoMoreThan(THIRTY).seconds());
            }
        });
    }

    public static Performable verifyUserRoleID(String email, String value){
        return Task.where("Varify that user role id is changed to "+ value, actor -> {

            Assert.assertEquals(elementPresent("users_user","email",email ,"role_id",value),true);


        });
    }


}
