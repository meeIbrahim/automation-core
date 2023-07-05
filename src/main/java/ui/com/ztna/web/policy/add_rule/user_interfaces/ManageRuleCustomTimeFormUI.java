package ui.com.ztna.web.policy.add_rule.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class ManageRuleCustomTimeFormUI {
    public static final Target OK_BUTTONS = Target.the("ok button")
            .locatedBy("//button[contains(@class,'MuiButton')]//span[text()='OK']");

    public static final Target CANCEL_BUTTON = Target.the("cancel button")
            .locatedBy("//button[contains(@class,'MuiButton')]//span[text()='Cancel']");

    public static final Target YEAR_OPTION = Target.the("year option")
            .locatedBy("(//button[contains(@class,'MuiPickersToolbarButton-toolbarBtn')])[1]");

    public static final Target SELECT_YEAR = Target.the("year selector")
            .locatedBy("//div[@class='MuiPickersYearSelection-container']//div[contains(text(),'{0}')]");

    public static final Target SELECTED_YEAR = Target.the("selected year")
            .locatedBy("//button[contains(@class,'MuiPickersToolbarButton-toolbarBtn')]//p[contains(text(),'{0}')]");

    public static final Target SELECTED_MONTH = Target.the("selected month")
            .locatedBy("//div[contains(@class,'MuiPickersSlideTransition')][.//p[contains(text(),'{0}')]]");

    public static final Target NEXT_MONTH_SELECTOR = Target.the("next month selector")
            .locatedBy("(//button[contains(@class,'MuiPickersCalendarHeader-iconButton')])[2]");

//    public static final Target DATE_SELECTOR = Target.the("date selector")
//            .locatedBy("//div[contains(@role,'presentation')]//p[text()='{0}']");

    public static final Target DATE_SELECTOR = Target.the("date selector")
            .locatedBy("//div[./button[not(contains(@class, 'Disabled'))]][./button[not(contains(@class, 'hidden'))]][contains(@role,'presentation')]//p[text()='{0}']");

    public static final Target AM_PM_SELECTOR = Target.the("am/pm selector")
            .locatedBy("//span[contains(@class,'MuiButton-label')]//p[text()='{0}']");

    public static final Target HOUR_MIN_SELECTOR = Target.the("hour minute selector")
            .locatedBy("//span[contains(@class,'MuiPickersClockNumber') and text()='{0}']");

    public static final Target SELECTED_MONTHS = Target.the("selected months")
            .locatedBy("//div[contains(@class,'MuiPickersCalendarHeader-switchHeader')]//p[contains(text(),'{0}')]");



}
