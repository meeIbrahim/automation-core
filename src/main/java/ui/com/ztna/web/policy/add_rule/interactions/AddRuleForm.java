package ui.com.ztna.web.policy.add_rule.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui.com.ztna.web.common.model.UpdateTimeParameters;
import ui.com.ztna.web.policy.add_rule.models.RuleParameters;

import java.util.List;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static ui.com.ztna.web.common.interactions.CommonInteraction.*;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.*;
import static ui.com.ztna.web.policy.add_rule.user_interfaces.ManageRulesForPoliciesPageGridUI.*;

public class AddRuleForm {


    public static Performable fillAddRuleForm(RuleParameters rule) {
        return Task.where("{0} fills Add Rule form", actor -> {
            actor.attemptsTo(
                    fillInputField(RULE_NAME_ENTER, rule.ruleName),
                    selectDropDownField(RULE_TYPE_DROPDOWN_BUTTON, rule.ruleType),
                    waitNotPresenceOf(SELECT_TYPE_DROPDOWN_BUTTON_DISABLED.of(rule.ruleType)),
                    selectDropDownField(SELECT_TYPE_DROPDOWN_BUTTON.of(rule.ruleType), rule.ruleRecipient),
                    handleTimeAccess(rule),
                    locationAccess(rule)
            );

        });
    }

    public static int setValue(String b){
        if(b.isEmpty()){
            return 0;
        }else {
            return Integer.parseInt(b);
        }
    }

    public static Performable handleTimeAccess(RuleParameters rule) {
        return Task.where("Handle time access", actor -> {

            int startTimeAfterHour = setValue(rule.startTimeAfterHours);
            int startTimeAfterMins = setValue(rule.startTimeAfterMins);
            int endTimeAfterHour = setValue(rule.endTimeAfterHours);
            int endTimeAfterMins = setValue(rule.endTimeAfterMins);
            int currentHour;
            int currentMins;






            if (!rule.accessTime.equals("any")) {


                WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                JavascriptExecutor js = (JavascriptExecutor) driver;
                actor.attemptsTo(
                        Click.on(TIME_BASED_ACCESS_CHECKBOX),
                        Click.on(START_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON),
                        waitNotPresenceOf(SELECTED_DATE_BUTTON)
//                        MoveMouse.to(CLOCK_POINTER_THUMB)
                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());

                if((currentMins + startTimeAfterMins)>59){
                    startTimeAfterHour+=1;
                }



                if((currentHour + startTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }

                WebElement pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                WebElement twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                int getX = twelveClock.getLocation().getX();
                int getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                String str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                String degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                int getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                int x = getX2 - getX;
                int y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                Actions actions = new Actions(driver);
                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));
                WebElement zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));

                int getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                int getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                int getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                int xm = getX2m - getXm;
                int ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON),
                        clickButton(END_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON)

                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());


                if((currentMins + endTimeAfterMins)>59){
                    endTimeAfterHour+=1;
                }


                if((currentHour + endTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }


                pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                getX = twelveClock.getLocation().getX();
                getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                System.out.println("before wait");
                actor.attemptsTo(waitTime(2));
                System.out.println("After wait");

                getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                x = getX2 - getX;
                y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));

                zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));
                getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                xm = getX2m - getXm;
                ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON)
                );

            }
        });
    }

    public static Performable handleTimeAccess(String STH,String STM, String ETH, String ETM) {
        return Task.where("Handle time access", actor -> {

            int startTimeAfterHour = setValue(STH);
            int startTimeAfterMins = setValue(STM);
            int endTimeAfterHour = setValue(ETH);
            int endTimeAfterMins = setValue(ETM);
            int currentHour;
            int currentMins;



                WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                JavascriptExecutor js = (JavascriptExecutor) driver;
                actor.attemptsTo(
                        Click.on(TIME_BASED_ACCESS_CHECKBOX),
                        waitPresenceOf(TIME_BASED_ACCESS_CHECKBOX_UNCHECKED),
                        Click.on(START_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON),
                        waitNotPresenceOf(SELECTED_DATE_BUTTON)
//                        MoveMouse.to(CLOCK_POINTER_THUMB)
                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());

                if((currentMins + startTimeAfterMins)>59){
                    startTimeAfterHour+=1;
                }



                if((currentHour + startTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }

                WebElement pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                WebElement twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                int getX = twelveClock.getLocation().getX();
                int getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                String str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                String degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                int getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                int x = getX2 - getX;
                int y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                Actions actions = new Actions(driver);
                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));
                WebElement zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));

                int getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                int getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                int getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                int xm = getX2m - getXm;
                int ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON),
                        clickButton(END_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON)

                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());


                if((currentMins + endTimeAfterMins)>59){
                    endTimeAfterHour+=1;
                }


                if((currentHour + endTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }


                pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                getX = twelveClock.getLocation().getX();
                getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                System.out.println("before wait");
                actor.attemptsTo(waitTime(2));
                System.out.println("After wait");

                getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                x = getX2 - getX;
                y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));

                zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));
                getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                xm = getX2m - getXm;
                ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON)
                );

        });
    }



    public static Performable handleTimeAccess(UpdateTimeParameters rule) {
        return Task.where("Handle time access", actor -> {

            int startTimeAfterHour = setValue(rule.startTimeAfterHours);
            int startTimeAfterMins = setValue(rule.startTimeAfterMins);
            int endTimeAfterHour = setValue(rule.endTimeAfterHours);
            int endTimeAfterMins = setValue(rule.endTimeAfterMins);
            int currentHour;
            int currentMins;






            if (!rule.anyTime.equals("yes")) {


                WebDriver driver = BrowseTheWeb.as(actor).getDriver();
                JavascriptExecutor js = (JavascriptExecutor) driver;
                actor.attemptsTo(
                        Click.on(TIME_BASED_ACCESS_CHECKBOX),
                        Click.on(START_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON),
                        waitNotPresenceOf(SELECTED_DATE_BUTTON)
//                        MoveMouse.to(CLOCK_POINTER_THUMB)
                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());

                if((currentMins + startTimeAfterMins)>59){
                    startTimeAfterHour+=1;
                }



                if((currentHour + startTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }

                WebElement pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                WebElement twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                int getX = twelveClock.getLocation().getX();
                int getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                String str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                String degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                int getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                int x = getX2 - getX;
                int y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                Actions actions = new Actions(driver);
                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));
                WebElement zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));

                int getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                int getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + startTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                int getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                int getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                int xm = getX2m - getXm;
                int ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON),
                        clickButton(END_DATE_TIME_INPUT),
                        clickButton(SELECTED_DATE_BUTTON)

                );

                currentHour = Integer.parseInt(CURRENT_TIME_HOURS.resolveFor(actor).getTextContent());
                currentMins = Integer.parseInt(CURRENT_TIME_MINUTUES.resolveFor(actor).getTextContent());


                if((currentMins + endTimeAfterMins)>59){
                    endTimeAfterHour+=1;
                }


                if((currentHour + endTimeAfterHour)>12){
                    actor.attemptsTo(clickButton(AM_PM_NOT_SELECTED));
                }


                pointerThumb = driver.findElement(By.xpath("//div[contains(@class,'MuiPickersClockPointer-thumb')]"));
                twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


                getX = twelveClock.getLocation().getX();
                getY = twelveClock.getLocation().getY();
                getY = getY - 5;


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterHour*30);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                System.out.println("before wait");
                actor.attemptsTo(waitTime(2));
                System.out.println("After wait");

                getX2 = pointerThumb.getLocation().getX();
//                System.out.println(getX2);
                getY2 = pointerThumb.getLocation().getY();
//                System.out.println(getY2);

                x = getX2 - getX;
                y = getY2 - getY;


                if (!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                    x += 6;
                    y += 6;
                }

                System.out.println(x);
                System.out.println(y);


                actions.moveToElement(twelveClock, 0, 0);
                actions.moveByOffset(x, y).click().build().perform();


                actor.attemptsTo(waitTime(2));

                zeroClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='00'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='00']"));
                getXm = zeroClock.getLocation().getX();
                System.out.println(getXm);
                getYm = zeroClock.getLocation().getY();
                getYm = getYm - 5;
                System.out.println(getYm);


                str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
                str = str.substring(str.indexOf("(") + 1, str.indexOf("deg"));
                degrees = String.valueOf(Integer.parseInt(str));
                degrees = String.valueOf(Integer.parseInt(degrees) + endTimeAfterMins*6);
                js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ(" + degrees + "deg);\"");

                actor.attemptsTo(waitTime(2));

                getX2m = pointerThumb.getLocation().getX();
                System.out.println(getX2m);
                getY2m = pointerThumb.getLocation().getY();
                System.out.println(getY2m);

                xm = getX2m - getXm;
                ym = getY2m - getYm;

                if (!String.valueOf(xm).equals("0") && !String.valueOf(ym).equals("114")) {
                    xm += 6;
                    ym += 6;
                }


                System.out.println(xm);
                System.out.println(ym);


                actions.moveToElement(zeroClock, 0, 0);
                actions.moveByOffset(xm, ym).click().build().perform();

                actor.attemptsTo(
                        waitTime(2),
                        clickButton(OK_BUTTON)
                );

            }
        });
    }

    public static Performable locationAccess(RuleParameters rule){
        return Task.where("Handle location access",actor -> {

            if (!rule.accessLocation.equals("any") && !(rule.countries.isEmpty())){
                actor.attemptsTo(
                        Click.on(LOCATION_BASED_ACCESS_CHECKBOX),
                        addMultipleValuesIntoInput(COUNTRIES_INPUT,rule.countries),
                        Click.on(MODAL_LABEL));
            }

        });
    }


    public static Performable locationAccess(List<String> countries){
        return Task.where("Handle location access",actor -> {
                actor.attemptsTo(
                        Click.on(LOCATION_BASED_ACCESS_CHECKBOX),
                        addMultipleValuesIntoInput(COUNTRIES_INPUT,countries),
                        Click.on(MODAL_LABEL));
        });
    }

    public static Performable saveAddRuleForm() {
        return Task.where("{0} fills Add Rule form",
                clickButton(CONFIRM_BUTTON)
        );
    }

    public static  Performable setClock( JavascriptExecutor js, WebDriver driver ,WebElement Origin, WebElement pointerThumb){
        return Task.where("User sets clock dial",actor -> {

            WebElement twelveClock = driver.findElement(By.xpath("//span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiTypography-body1')][.='12'] | //span[contains(@class,'MuiTypography-root MuiPickersClockNumber-clockNumber MuiPickersClockNumber-clockNumberSelected MuiTypography-body1')][.='12']"));


            int getX = twelveClock.getLocation().getX();
            int getY = twelveClock.getLocation().getY();
            getY = getY - 5;



            String str = CLOCK_POINTER.resolveFor(actor).getAttribute("style");
            str = str.substring(str.indexOf("(")+1,str.indexOf("deg"));
            String degrees = String.valueOf(Integer.parseInt(str));
            degrees = String.valueOf(Integer.parseInt(degrees)+180);
            js.executeScript("document.getElementsByClassName(\"MuiPickersClockPointer-pointer\")[0].style=\"height: 40%; transform: rotateZ("+degrees+"deg);\"");

            actor.attemptsTo(waitTime(2));

            int getX2 = pointerThumb.getLocation().getX();
            int getY2 = pointerThumb.getLocation().getY();

            int x = getX2 - getX;
            int y = getY2 - getY;


            if(!String.valueOf(x).equals("0") && !String.valueOf(y).equals("114")) {
                x += 6;
                y += 6;
            }

            System.out.println(x);
            System.out.println(y);



            Actions actions = new Actions(driver);
            actions.moveToElement(twelveClock, 0, 0);
            actions.moveByOffset( x, y).click().build().perform();



            actor.attemptsTo(waitTime(2));


        });
    }


}
