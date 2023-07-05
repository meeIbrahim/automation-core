package ui.com.ztna.web.policy.add_rule.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class ManageRulesForPoliciesPageGridUI {

    public static final Target ADD_A_NEW_RULE_FORM_TITLE = Target.the("Add a New Rule form title")
            .locatedBy("//div[@role='dialog']//div[./h2[text()='Add Rule']]");

    public static final Target MODAL_LABEL = Target.the("Modal label")
            .locatedBy("//h2");

    public static final Target RULE_TYPE_DROPDOWN_BUTTON = Target.the("rule type dropdown button")
            .locatedBy("//div[./p='User/Access Group']//div[.='Access Group']//div[contains(@class, 'react-select__indicators')] | //div[./p='User/Access Group']//div[.='User']//div[contains(@class, 'react-select__indicators')]");

    public static final Target RULE_NAME_DROPDOWN_BUTTON = Target.the("rule name dropdown button")
            .locatedBy("//tbody//tr[1]//td[2]/div/div[2]//div[contains(@class, 'react-select__indicators')]//div[contains(@class, 'react-select__dropdown-indicator')]");

    public static final Target RULE_NAME_ENTER = Target.the("enter rule name")
            .locatedBy("//input[@name='name'][@placeholder='Enter rule name']");

    public static final Target SELECT_TYPE_DROPDOWN_BUTTON = Target.the("Select User Dropdown Button")
            .locatedBy("//div[.='Select {0}']//div[contains(@class, 'react-select__indicators')]");

    public static final Target SELECT_TYPE_DROPDOWN_BUTTON_DISABLED = Target.the("Select User Dropdown Button disabled")
            .locatedBy("//div[.='Select User']//div[contains(@class,'is-disabled')]");

    public static final Target SELECT_TYPE_BUTTON = Target.the("Select User {0} Button")
            .locatedBy("//div[.='{0}'][contains(@id,'react-select')]");




    public static final Target DROPDOWN_OPTION = Target.the("dropdown option")
            .locatedBy("//div[contains(@class, 'react-select__option')][.='{0}']");

    public static final Target DONE_ACTION_BUTTON = Target.the("done action button")
            .locatedBy("//tbody//tr[1]//td[5]//button[1]");

    public static final Target RULE_ROW_NAME = Target.the("rule row")
            .locatedBy("//tbody//tr[./td='{0}']");

    public static final Target RULE_ROW = Target.the("rule row")
            .locatedBy("//tbody//tr[./td='{0}'][./td='{1}']");


    public static final Target RULE_ROW_LOCATION_REFERENCE = Target.the("rule row")
            .locatedBy("//tbody//tr[./td[.='{0}']][//div[@title='{1}']]");

    public static final Target RULE_ROW_DELETE_BUTTON = Target.the("rule row delete button")
            .locatedBy("//tbody//tr[.//p[.='{0}']]//button[2]");

    public static final Target SHOW_REMOVED_RULES_BUTTON = Target.the("Show removed rules button")
            .locatedBy("//div[.='Show Removed Rules']//input");


    public static final Target RULE_ROW_ACTION_BUTTON = Target.the("rule row action button")
            .locatedBy("//tbody//tr[.//span[.='{0}']]//button[contains(@id,'actionIcon')]");
    public static final Target REMOVE_RULE_ACTION_BUTTON = Target.the("remove rule action button")
            .locatedBy("//div[.='Remove Rule']");

    public static final Target DELETED_STATUS = Target.the("deleted status")
            .locatedBy("//tbody//tr[.//span[.='{0}']]//span[.='Deleted']");

    public static final Target ACCESS_TIME_TOGGLE_BUTTON = Target.the("access time toggle button")
            .locatedBy("//tbody//tr[1]//td[3]//input[@type='checkbox']");

    public static final Target ACCESS_TIME_TOGGLE_CHECKED = Target.the("access time checkbox checked")
            .locatedBy("//tbody//tr[1]//td[3]//input[@type='checkbox'][@checked]");

    public static final Target LOCATION_TOGGLE_BUTTON = Target.the("location toggle button")
            .locatedBy("//tbody//tr[1]//td[4]//input[@type='checkbox']");

    public static final Target CUSTOMIZED_LOCATION_TOGGLE = Target.the("location toggle button")
            .locatedBy("//tbody//tr[1]//td[4]//input[@type='checkbox']");

    public static final Target START_TIME_INPUT_FIELD = Target.the("start time input field")
            .locatedBy("//tbody//tr[1]//td[3]//input[@placeholder='Start Date & Time']");

    public static final Target END_TIME_INPUT_FIELD = Target.the("end time input field")
            .locatedBy("//tbody//tr[1]//td[3]//input[@placeholder='End Date & Time']");

    public static final Target START_TIME = Target.the("start time")
            .locatedBy("//tbody//tr[1]//td[3]//input[@placeholder='Start Date & Time' and @value='{0}']");

    public static final Target END_TIME = Target.the("end time")
            .locatedBy("//tbody//tr[1]//td[3]//input[@placeholder='End Date & Time' and @value='{0}']");

    public static final Target LOCATION_MODAL_TITLE = Target.the("location modal title")
            .locatedBy("//div[@id='customized-dialog-title']//h2[contains(text(),'Customize Locations')]");

    public static final Target COUNTRY_INPUT_FIELD = Target.the("country input field")
            .locatedBy("#en-ztna-AddEditRule-selectCountries");

    public static final Target COUNTRY_SELECTOR = Target.the("country selector")
            .locatedBy("//div[./div[./span[text()='{0}']]]");

    public static final Target COUNTRY_SELECTED = Target.the("country selected")
            .locatedBy("//div[contains(@class,'react-select__multi-value__label')][./div[./span[text()='{0}']]]");

    public static final Target CONFIRM_BUTTON = Target.the("confirm button")
            .locatedBy("#en-ztna-modal-saveButton");

    public static final Target ADD_RULE_FORM_TITLE = Target.the("Add Rule form title")
            .locatedBy("//div[@role='dialog']//div[./h2[text()='Add Rule']]");

    public static final Target ADDED_RULE_NAME = Target.the("Added Rule")
            .locatedBy("//div[@role='dialog']//div[./h2[text()='Add Rule']]");

    public static final Target POLICY_CONFLICT_MESSAGE = Target.the("Policy conflict message")
            .locatedBy("//div[@data-testid=\"error-container\"]//p[contains(text(), 'already has access to')]");

    public static final Target CANCEL_BUTTON = Target.the("Cancel button")
            .locatedBy("//button[.//span[text()='Cancel']]");

    public static final Target UPDATE_RULE_MODAL_BUTTON = Target.the("Update rule modal button")
            .locatedBy("//button[.='Update Rule']");





}
