package ui.com.ztna.web.signin.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class UsersGridUI {

    public static final Target CUSTOMER_ADMIN_EMAIL = Target.the("customer admin email")
            .locatedBy("//tr[.//div[contains(@title, \"Account Admin; This is default user\")]]/td/div/span | //tr//div[./div[@data-testid = 'otherUser-icon']]//span");

    public static final Target INVITE_USERS_FORM_TITLE = Target.the("Invite Users form title")
            .locatedBy("//h2[.='Invite Users']");

    public static final Target ADMIN_EMAIL = Target.the("admin email")
            .locatedBy("//tr[.//div[.='{0}']]");

    public static final Target TENANT_ADMIN_EMAIL = Target.the("tenant admin email")
            .locatedBy("//tr//div[./span[.='{0}']]/div[@data-testid = 'otherUser-icon']");

    public static final Target ROOT_OVERLAY = Target.the("root overlay")
            .locatedBy("#avatar");

    public static final Target INVITE_USERS_BUTTON = Target.the("Invite users button")
            .locatedBy("//button[.='Invite Users']");

    public static final Target END_USER_EMAIL = Target.the("end user email")
            .locatedBy("//tbody//tr//td[1][.='{0}']");

    public static final Target ACCESS_GROUP_IN_TABLE = Target.the("access group in table")
            .locatedBy("//div[./span='Name']//div[.='{0}']");

    public static final Target CLOSE_SEARCH_BUTTON = Target.the("Close Search Icon")
            .locatedBy("//button[@data-testid='search-close-icon']");

    public static final Target SIDEBAR_MENU_BUTTON = Target.the("sidebar menu button")
            .locatedBy("//span[@class='MuiIconButton-label']");

    public static final Target FILTER_INPUT = Target.the("filter input")
            .locatedBy("//div[.//table]//input[contains(@class,'MuiInputBase-input')]");

    public static final Target FILTER_SET = Target.the("filter is set")
            .locatedBy("//div[contains(@class,'outlined')]/span[text()='{0}']");

    public static final Target USER_FILTER = Target.the("User Filter")
            .locatedBy("//li[@class='MuiAutocomplete-option']//div[.='User']");

    public static final Target ACCESS_GROUP_FILTER = Target.the("Access Group Filter")
            .locatedBy("//div[@role='presentation']//p[.='Access Group']");

    public static final Target USERNAME_FILTER = Target.the("Username Filter")
            .locatedBy("//div[@role='presentation']//p[.='Username']");

    public static final Target USER_EXACT_FILTER = Target.the("User Exact Filter")
            .locatedBy("//div[@role='presentation'][.//li[@class='MuiAutocomplete-option']/div[.='{0}']]");

    public static final Target EXACT_FILTER = Target.the("Exact Filter")
            .locatedBy("//div[@role='presentation']//li[@class='MuiAutocomplete-option'][.//p[.='{0}']]");

    public static final Target SEARCH_ICON = Target.the("Search Icon")
            .locatedBy("//button[@id='en-ztna-Users-searchBar']");

    public static final Target ACCESS_GROUP_SEARCH_ICON = Target.the("Access Group Search Icon")
            .locatedBy("//button[@id='en-ztna-AccessGroups-searchBar']");

    public static final Target SEARCH_BAR_INPUT = Target.the("Search Bar Input")
            .locatedBy("//input[@id='en-ztna-Users-searchBar-input']");

    public static final Target ACCESS_GROUP_SEARCH_BAR_INPUT = Target.the("Access Group Search Bar Input")
            .locatedBy("//input[@id='en-ztna-AccessGroups-searchBar-input']");

    public static final Target USERS_LOADING_ICON = Target.the("users loading")
            .locatedBy("//div[contains(@class,'MuiCircularProgress')]");

    public static final Target LOADING_ICON = Target.the("loading icon")
            .locatedBy("//div[contains(@class,'MuiCircularProgress')]");

    public static final Integer USERS_LOADING_ICON_WAIT = 10;

    public static final Target ACTIVE_USER_STATUS = Target.the("active user status")
            .locatedBy("//tr[.//div/span[.='Active']][.//td[.='{0}']]");

    public static final Target USERS_IN_ACCESS_GROUPS = Target.the("users in access groups, to be saved")
            .locatedBy("//tr[@id='en-ztna-TableRow-{0}']//td[@id='RowCell-email-0']//span");

    public static final Target FILTERED_ACCESS_GROUPS = Target.the("filtred access groups, to be saved")
            .locatedBy("//div[@id='en-ztna-CardRow-{0}']//div[@id='CardRowCell-name-0']//span[@data-testid='row-body']//p");

    public static final Target ACCESS_GROUP_BUTTON_SIDE_BAR = Target.the("Access group button side-bar")
            .locatedBy("//div[@class='MuiListItemText-root'][./span[.='Access Groups']]");

    public static final Target ACCESS_GROUP_DROPDOWN_BUTTON = Target.the("Access group dropdown button")
            .locatedBy("//div[.//p='Add to Access Group']//div[contains(@class,'react-select__indicators')]");

    public static final Target TENANT_IN_ZTNA_ADMIN = Target.the("tenant in ztna admin")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[contains(@id,'creatorEmail')]//p[text()='{0}']][.//div[contains(@id,'invitationCode')]//span[text()='{1}']]");

    public static final Target PENDING_USER_STATUS = Target.the("pending user status")
            .locatedBy("//tr[.//div[.='Pending']][.//td[@id='RowCell-email-0'][.='{0}']]");
    public static final Target ONBOARDING_MODAL_MAYBE_LATER_BUTTON = Target.the("Onboarding modal 'Maybe Later' Button")
            .locatedBy("//button[.//span[text()='Skip']]");
}
