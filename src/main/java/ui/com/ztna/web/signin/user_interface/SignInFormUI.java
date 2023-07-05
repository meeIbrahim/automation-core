package ui.com.ztna.web.signin.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class SignInFormUI {

    public static final Target EMAIL_FIELD = Target.the("Email field")
            .locatedBy("#en-ztna-loginForm-emailInput");

    public static final Target EMAIL_FIELD_FOR_SIGN_UP = Target.the("Email Field")
            .locatedBy("#en-ztna-SignupForm-emailInput");
    public static final Target INVITATION_CODE_FIELD = Target.the("invitation code field").locatedBy("//input[contains(@id,'tenantAdminInvitationCode')]");

    public static final Target PASSWORD_FIELD = Target.the("Password field")
            .locatedBy("//input[@id=\"en-ztna-passwordForm-passwordInput\"] | //input[@id=\"en-ztna-loginForm-passwordInput\"]");

    public static final Target SIGN_IN_BUTTON = Target.the("Sign in button")
            .locatedBy("#en-ztna-loginForm-signInButton");

    public static final Target GENERATE_INVITATION_CODE = Target.the("generate invitation code button").locatedBy("//button[.//span[contains(text(),'Generate Invitation Code')]]");

    public static final Target INVITATION_CODE = Target.the("invitation code text").locatedBy("//div[@role='dialog']//div[contains(@data-testid,'content-dialog')]//p[contains(text(),'ZTA')]");

    public static final Target CLOSE_BUTTON = Target.the("invitation code close button").locatedBy(" //button[@id='en-ztna-modal-saveButton'][.//span[contains(text(),'Close')]]");

    public static final Target CONTINUE_BUTTON = Target.the("Continue button")
            .locatedBy("//button[.='Continue']");

    public static final Target CREATE_NEW_ONE_BUTTON = Target.the("Create new one button")
            .locatedBy("//span[.='Create new one']");

    public static final Target TENANT_ADMIN_CHECKBOX = Target.the("tenant admin checkbox")
            .locatedBy("//span[text()='Tenant Admin']");

    public static final Target FORGOT_PASSWORD_EMAIL_FIELD = Target.the("Email field")
            .locatedBy("#en-ztna-forgotPassword-emailInput");

    public static final Target FORGOT_PASSWORD_BUTTON = Target.the("forgot password button")
            .locatedBy("#en-ztna-loginForm-forgotPasswordButton");

    public static final Target SEND_LINK_BUTTON = Target.the("send link button")
            .locatedBy("#en-ztna-forgotPassword-sendLinkButton");

    public static final Target LINK_SENT_SUCCESSFUL_MESSAGE = Target.the("link sent successful message")
            .locatedBy("#en-ztna-forgotPassword-successContainer");

    public static final Target BACK_TO_SIGN_IN_BUTTON = Target.the("back to sign in button")
            .locatedBy("#en-ztna-forgotPassword-backToSignInButton");

    public static final Target LOCAL_SIGNIN_ERROR_MESSAGE = Target.the("local signin error message")
            .locatedBy("//div[@id='en-ztna-loginForm-localLoginError']//p[.='Incorrect email address or password. Please enter valid account information.']");

    public static final Target SIGN_IN_TEXT = Target.the("sign in text")
            .locatedBy("//h2[contains(@class,'MuiTypography-root')][.='Sign in']");
    public static final  Target SIGN_IN_WITH_MICROSOFT_BUTTON = Target.the("Sign in with microsoft button")
            .locatedBy("//button[.='Sign in with Microsoft']");
    public static final  Target SIGN_IN_WITH_GOOGLE_BUTTON = Target.the("Sign in with Google button")
            .locatedBy("//button[.='Sign in with Google']");
}
