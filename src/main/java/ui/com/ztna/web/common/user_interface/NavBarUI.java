package ui.com.ztna.web.common.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class NavBarUI {

    public static final Target MENU_BUTTON = Target.the("Menu button")
            .locatedBy("//button[@class='MuiButtonBase-root MuiIconButton-root jss84']");

    public static final Target REFRESH_BUTTON = Target.the("Refresh button")
            .locatedBy("//button[.//*[contains(@id,\"refresh\")]]");

    public static final Target SIGNED_IN_EMAIL = Target.the("signed in email")
            .locatedBy("//li[@id='en-ztna-topNav-profileImage-0']//p[contains(@class,'MuiTypography-subtitle2')]");

    public static final Target PROFILE_BUTTON = Target.the("profile button")
            .locatedBy("//div[contains(@class,'MuiToolbar')]//div[contains(@class,'MuiAvatar')]");
    public static final Target MICROSOFT_OUTLOOK_PROFILE_BUTTON = Target.the("microsoft outlook profile button")
            .locatedBy("//button[@type='button'][contains(@title,'Account manager')]");
    public static final Target MICROSOFT_OUTLOOK_PROFILE_EMAIL = Target.the("microsoft outlook profile email")
            .locatedBy("//div[contains(@class,'accountDetails')]/div[contains(@id,'currentAccount_secondary')][text()='{0}']");

    public static final Target OUTLOOK_EMAIL_OTHER_BUTTON = Target.the("'Other' button in outlook email")
            .locatedBy("//button[@type='button' and @name='Other']");

    public static final Target ZTA_VERIFICATION_PIN_EMAILS= Target.the("'Other' button in outlook email")
            .locatedBy("(//div[contains(@aria-label,'ExtremeCloud ZTA Support ZTNA')][contains(@aria-label,'Your verification pin is')]//button[@type='button'][.//i[@data-icon-name='DeleteRegular']])[1]");

    public static final Target LOGOUT_BUTTON = Target.the("logout button")
            .locatedBy("//div[./p='Logout']");

    public static final Target VIEW_PROFILE_BUTTON = Target.the("view profile button")
            .locatedBy("//div[.='View Profile']");

    public static final Target PROFILE_TITLE = Target.the("profile title")
            .locatedBy("//h2[.='Profile']");

    public static final Target EDIT_ORGANIZATION_DETAILS_BUTTON = Target.the("edit organization details button")
            .locatedBy("//button[.='Edit Organization Details']");

    public static final Target EDIT_ACCOUNT_DETAILS_BUTTON = Target.the("edit account details button")
            .locatedBy("//button[.='Edit Account Details']");

    public static final Target COMPANY_NAME = Target.the("company name")
            .locatedBy("//input[@name='companyName']");

    public static final Target ENTER_NEW_PASSWORD_INPUT = Target.the("Enter new password input")
            .locatedBy("//input[@placeholder='Enter New Password']");

    public static final Target CONFIRM_NEW_PASSWORD_INPUT = Target.the("Confirm new password input")
            .locatedBy("//input[@placeholder='Confirm New Password']");

    public static final Target SAVE_ADMIN_ACCOUNT_SETTINGS_BUTTON = Target.the("Save admin account settings button")
            .locatedBy("//h2[.='Admin Account Settings']/following-sibling::button[1]");

}
