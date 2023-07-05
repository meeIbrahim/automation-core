package ui.com.ztna.web.common.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class CommonUI {

    public static final Target FORCE_REMOVE_POP_OVER_BUTTON = Target.the("Force pop-over remove button")
            .locatedBy("//div[./p[contains(text(), 'Force')]][contains(@id,'en-ztna-PopoverButtons')] [not(contains(@style,'none'))]");

    public static final Target FORCE_REMOVE_POP_OVER_BUTTON_DISABLED = Target.the("Force remove button")
            .locatedBy("//div[./p[contains(text(), 'Force')]][contains(@id,'en-ztna-PopoverButtons')][contains(@class,'disabled')]");
    public static final Target FORCE_REMOVE_POP_OVER_BUTTON_ENABLED = Target.the("Force remove button")
            .locatedBy("//div[./p[contains(text(), 'Force')]][contains(@id,'en-ztna-PopoverButtons')] [not (@aria-disabled=\"true\")]");

    public static final Target FORCE_REMOVE_NAME_INPUT = Target.the("Force remove name input")
            .locatedBy("//input[contains(@placeholder,'Enter')]");

    public static final Target FORCE_REMOVE_MODAL_BUTTON = Target.the("Force remove modal button")
            .locatedBy("//button[@id='en-ztna-modal-saveButton'][./span[contains(text(),'Force')]]");

    public static final Target CANCEL_BUTTON = Target.the("Cancel button")
            .locatedBy("//button[.='Cancel']");

    public static final Target PAGE_BODY = Target.the("Page body")
            .locatedBy("//body[@style]");

    public static final Target POPOVER_BUTTON = Target.the("Popover Button {0}")
            .locatedBy("//div[contains(@id,'en-ztna-PopoverButton')][.='{0}']");

    public static final Target MODAL_LABEL = Target.the("Modal label")
            .locatedBy("//h2[.='{0}']");

    public static final Target MODAL_LABEL_GENERAL = Target.the("Modal label general")
            .locatedBy("//h2");

    public static final Target PAGE_LABEL = Target.the("Page label")
            .locatedBy("//span[.='{0}']");

    public static final Target END_USER_PORTAL_BUTTON = Target.the("End user portal button")
            .locatedBy("//a[@target='endUserPortalWindow']");

    public static final Target TENANT_ADMIN_PORTAL_BUTTON = Target.the("Tenant admin portal button")
            .locatedBy("//a[@target='tenantAdminPortalWindow']");

    public static final Target DROPDOWN_PRESELECT_OPTION = Target.the("dropdown pre-select option")
            .locatedBy("//div[.//p='{0}']//div[contains(@class,'react-select__control')][.='{1}']");

    public static final Target PROCEED_BUTTON = Target.the("Proceed button")
            .locatedBy("//button[.='Proceed']");

    public static final Target MODAL_LOADING_ICON = Target.the("Modal loading icon")
            .locatedBy("//img[@src='/static/media/Loading.d5620651.gif']");

    public static final Target VALIDATION_SUCCESSFUL_CHECKMARK = Target.the("Validation successful checkmark")
            .locatedBy("//*[name()='svg' and @fill='#66bc53']");

    public static final Target ACTION_ICON_TABLE_ROW_BUTTON = Target.the("Action icon table row button")
            .locatedBy("//tr[./td[.='{0}']]//button[@data-testid=\"actions-icon\"]");


}
