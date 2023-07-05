package ui.com.ztna.web.policy.add_policy.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;


public class PoliciesGridUI {


    public static final Target POLICY_ROW = Target.the("policy row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]");

    public static final Target INACTIVE_LABEL = Target.the("inacitve label")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]//div[@id='CardRowCell-status-1']//span[@class='MuiChip-label'][.='Inactive']");

    public static final Target ACTIVE_LABEL = Target.the("acitve label")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]//div[@id='CardRowCell-status-1']//span[@class='MuiChip-label'][.='Active']");

    public static final Target DELETE_IN_PROGRESS_LABEL = Target.the("delete in progress label")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]//div[@id='CardRowCell-status-1']//span[@class='MuiChip-label'][.='Delete In Progress']");

    public static final Target POLICY_ROW_OPTION_BUTTON = Target.the("policy row option button")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]//button[contains(@id,'actionIcon')]");

    public static final Target MANAGE_RULES_DROPDOWN_OPTION = Target.the("manage rules dropdown option")
            .locatedBy("#en-ztna-PopoverButtons-manageRules");

    public static final Target REMOVE_POLICY_POPOVER_BUTTON = Target.the("remove policy pop-over button")
            .locatedBy("//div[@id='en-ztna-PopoverButtons-removePolicy'][.='Remove Policy']");

    public static final Target POLICY_RESOURCE = Target.the("policy resource")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]/div/div/div[4]//p[.='{1}']");

    public static final Target SERVICE_LABEL = Target.the("service label")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']][//div[./span='Resource']//p[.='{1}']]");

    public static final Target PROJECT_LABEL = Target.the("project label")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']][//div[./span='Resource']//p[.='{1}']]");

    public static final Target NO_OF_RULES = Target.the("no of rules")
            .locatedBy("//tr[.//td='{0}']//td[6]");

    public static final Target NO_OF_RULES_COUNT = Target.the("no of rules count")
            .locatedBy("//tr[.//td='{0}']//td[6][.='{1}']");
    public static final Target MANAGE_ACTIVATION_BUTTON = Target.the("Manage Activation Button")
            .locatedBy("//div[@id='en-ztna-PopoverButtons-manageActivation']");
    public static final Target ACTIVATION_TOGGLE_BUTTON = Target.the("Toggle Activation Status")
            .locatedBy("//div[./div/h2='Manage Activation']//span[contains(@class,'MuiSwitch-root')]");

    public static final Target UPDATE_MANAGE_ACTIVATION_BUTTON = Target.the("Update")
            .locatedBy("//div[./div/h2='Manage Activation']//span[.='Update']");

    public static final Target POLICY_ACTIVATED_STATUS = Target.the("Activated Status")
            .locatedBy("//div[contains(@id,'en-ztna')][.//p='{0}'][//div[./span='Policy Status']]//span//span[.='Active']");

    public static final Target POLICY_INACTIVATED_STATUS = Target.the("Deactivated Status")
            .locatedBy("//div[contains(@id,'en-ztna')][.//p='{0}'][//div[./span='Policy Status']]//span//span[.='Inactive']");

    public static final Target START_DATE_TIME_INPUT = Target.the("Start date and time input")
            .locatedBy("//div[./p='Start Date & Time']//input");

    public static final Target END_DATE_TIME_INPUT = Target.the("End date and time input")
            .locatedBy("//div[./p='End Date & Time']//input");

    public static final Target SELECTED_DATE_BUTTON = Target.the("Chose selected date")
            .locatedBy("//button[contains(@class,'daySelected')]");

    public static final Target SELECTED_HOUR = Target.the("Chose the selected hour")
            .locatedBy("//span[contains(@class,'clockNumberSelected')]");

    public static final Target CLOCK_POINTER = Target.the("")
            .locatedBy("//div[contains(@class,'MuiPickersClockPointer-pointer')]");

    public static final Target CLOCK_POINTER_THUMB = Target.the("Clock pointer thumb")
            .locatedBy("//div[contains(@class,'MuiPickersClockPointer-thumb')]");

    public static final Target OK_BUTTON = Target.the("Ok button")
            .locatedBy("//button[.='OK']");

    public static final Target TIME_BASED_ACCESS_CHECKBOX = Target.the("Time based access checkbox")
            .locatedBy("//div[./p='Time Based Access']//input[@type='checkbox']");

    public static final Target TIME_BASED_ACCESS_CHECKBOX_CHECKED = Target.the("Time based access checkbox checked")
            .locatedBy("//div[./p='Time Based Access']//span[contains(@class,'root')][contains(@class,'checked')]//input[@type='checkbox']");

    public static final Target TIME_BASED_ACCESS_CHECKBOX_UNCHECKED = Target.the("Time based access checkbox unchecked")
            .locatedBy("//div[./p='Time Based Access']//span[contains(@class,'root')][not(contains(@class,'checked'))]//input[@type='checkbox']");

    public static final Target LOCATION_BASED_ACCESS_CHECKBOX = Target.the("Location based access checkbox")
            .locatedBy("//div[./p='Location Based Access']//input[@type='checkbox']");

    public static final Target LOCATION_BASED_ACCESS_CHECKBOX_CHECKED = Target.the("Location based access checkbox checked")
            .locatedBy("//div[./p='Location Based Access']//span[contains(@class,'root')][contains(@class,'checked')]//input[@type='checkbox']");

    public static final Target LOCATION_BASED_ACCESS_CHECKBOX_UNCHECKED = Target.the("Location based access checkbox unchecked")
            .locatedBy("//div[./p='Location Based Access']//span[contains(@class,'root')][not(contains(@class,'checked'))]//input[@type='checkbox']");

    public static final Target COUNTRIES_INPUT = Target.the("Countries input")
            .locatedBy("//div[./p='Countries']//input");

    public static final Target COUNTRY_FLAG_CONTAINER_IN_ROW = Target.the("{0} flag shown in the rule row")
            .locatedBy("//tr[.//td//span='{1}']//div[contains(@data-testid,'flag-container-{0}')]");

    public static final Target TOOL_TIP_TEXT_DISPLAYED = Target.the("Ensure {0} is displayed when we hover over the element")
            .locatedBy("//div[contains(@class,'tooltip')][.='{0}'] | //div[contains(@class,'tooltip')][contains(text(),'{0}')] | //div[contains(@class,'tooltip')][.//p='{0}'][.//p='{1}']");

    public static final Target ANY_LOCATION_TABLE_DATA = Target.the("Any location")
            .locatedBy("//tr[.//td//span='{0}']//td[@id='RowCell-countries-3'][./div/p='ANY']");

    public static final Target ALL_TIME_ACCESS_TABLE_DATA = Target.the("All time access shown in table data for {0} rule")
            .locatedBy("//tr[.//td//span='{0}']//td[@id='RowCell-timeBasedAccess-4'][./p='All Time Access']");

    public static final Target TIME_BASED_ACCESS_MORE_INFO = Target.the("View more information about the time based access of {0} rule")
            .locatedBy("//tr[.//td//span='{0}']//td[@id='RowCell-timeBasedAccess-4']/div/div");

    public static final Target POLICY_ROW_ACTION_ICON_BUTTON = Target.the("policy row action icon button")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[./span[.='Name']]//p[.='{0}']]//button[@data-testid='actions-icon']");


    public static final Target SERVICE_NAME = Target.the("Service name")
            .locatedBy("//div[contains(@id,'CardRow-')][.//div[./span[.='Name']][.//p='{0}']]//div[./span[.='Resource']]//div[@title='Service']/following-sibling::p");

    public static final Target SHOW_MORE_BUTTON_FOR_FIRST_POLICY_ROW = Target.the("show more button for first policy row")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')]//button[@aria-label='show more']");

    public static final Target RULE_FOR_USER = Target.the("rule for user")
            .locatedBy("//*[name()='svg'][@id='svg']//div[./span[contains(text(), 'User or User Group')]]//span[not(@title)]");

    public static final Target TABLE_ROWS = Target.the("table rows")
            .locatedBy("//tr[contains(@id,'en-ztna-TableRow-{0}')]");

    public static final Target TABLE_ROWS_STATUS = Target.the("Table row status")
            .locatedBy("//tr[contains(@id,'en-ztna-TableRow-{0}')]/td[contains(@id,'RowCell-status')][.='{1}']");

    public static final Target SERVICE_IN_END_USER_PORTAL = Target.the("Service present in end user portal")
            .locatedBy("//div[@data-testid='service-container'][.//p='{0}']");

    public static final Target CURRENT_TIME_MINUTUES = Target.the("Minutes of the current time")
            .locatedBy("//h3[contains(@class,'Toolbar-separator')]/following-sibling::button//h3");

    public static final Target CURRENT_TIME_HOURS = Target.the("Hours of the current time")
            .locatedBy("//h3[contains(@class,'Toolbar-separator')]/preceding-sibling::button//h3");

    public static final Target AM_PM_SELECTED = Target.the("Selected between am to pm")
            .locatedBy("//button[.//p[contains(text(),'AM') or contains(text(),'PM') ][contains(@class,'Selected')]]");

    public static final Target AM_PM_NOT_SELECTED = Target.the("Not selected between am to pm")
            .locatedBy("//button[.//p[contains(text(),'AM') or contains(text(),'PM') ][not(contains(@class,'Selected'))]]");

    public static final Target DEVICE_POSTURE_DROPDOWN = Target.the("Device posture dropdown")
            .locatedBy("");


    public static final Target UPDATE_POLICY_MODAL_BUTTON = Target.the("Update Policy Modal Button")
            .locatedBy("//button[.='Update Policy']");

    public static final Target DUPLICATE_POLICY_ERROR_MESSAGE = Target.the("Duplicate policy error")
            .locatedBy("//div[@data-testid='error-container']//p[.='Policy with this name already exists in workspace.']");

    public static final Target DUPLICATE_RULE_ERROR_MESSAGE = Target.the("Duplicate policy error")
            .locatedBy("//div[@data-testid='error-container']//p[.='Rule with this name already exists in this policy.']");

}