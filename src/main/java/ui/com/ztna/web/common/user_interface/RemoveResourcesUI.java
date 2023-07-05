package ui.com.ztna.web.common.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class RemoveResourcesUI {

    public static final Target First_ROW_ACTION_ICON = Target.the("First row action icon button")
            .locatedBy("//div[@id='en-ztna-CardRow-0'][.//div[contains(@id,'CardRowCell')]//span[@data-testid='row-body'][.//p='{0}']]//button[@id='en-ztna-CardRow-0-actionIcon'] | //tbody//tr[.//td[.='{0}']]//button[contains(@id, 'actionIcon')]");

    public static final Target REMOVE_SC = Target.the("Service connector action icon")
            .locatedBy("//div[contains(@id,'en-ztna-CardRow')][.//div[contains(@id,'CardRowCell')]//span[@data-testid='row-body'][.//p='{0}']]//button[contains(@id,'en-ztna-CardRow')] | //tbody//tr//button[contains(@id, 'actionIcon')]");
    public static final Target FIRST_ROW_NAME = Target.the("First row name")
            .locatedBy("//div[@id='en-ztna-CardRow-0']//div[@id='CardRowCell-name-0']//span[@data-testid='row-body']//p | //tr[./td/div[@data-testid='tableRowPrePostIconWrapper']/div[not(contains(@data-Testid, \"defaultUser-icon\"))]][1]/td/div/span");

//    public static final Target FIRST_USERNAME_ROW = Target.the("First username row")
//            .locatedBy("//tr[./td/div[@data-testid='tableRowPrePostIconWrapper']/div[not(contains(@data-Testid, \"defaultUser-icon\"))]][1]/td/div/span");

    public static final Target REMOVE_POPOVER_BUTTON = Target.the("Remove pop-over button")
            .locatedBy("//div[contains(@id,'en-ztna-PopoverButtons')][./p[contains(text(),'Remove')]] [not(contains(@style,'none'))][@aria-disabled='false']");

    public static final Target REMOVE_POPOVER_BUTTON_DISABLED = Target.the("Remove pop-over button (disabled)")
            .locatedBy("//div[contains(@id,'en-ztna-PopoverButtons')][./p[contains(text(),'Remove')]] [not(contains(@style,'none'))][@aria-disabled='true']");

    public static final Target REMOVE_FORM_TITLE = Target.the("Remove form title")
            .locatedBy("//h2[contains(text(),'Remove')]");

    public static final Target REMOVE_MODAL_BUTTON = Target.the("Remove modal button")
            .locatedBy("//button[.//span[contains(text(),'Remove')]][@data-testid='modal-saveButton']");

    public static final Target INTEGRATIONS_FIRST_ROW_NAME = Target.the("Integrations first row name")
            .locatedBy("//div[@id='en-ztna-CardRow-0']//div[contains(@id,'CardRow')][contains(@id,'-1')]//span[@data-testid='row-body']//p");
    public static final Target ROWS =Target.the("Integrations first row")
            .locatedBy( "//tr[@id]");
    public static final Target COLUMN = Target.the("Integrations first row name")
            .locatedBy("//td[contains(@id,\"{0}\")]");
    public static final Target INTEGRATIONS_FIRST_ROW_ACCOUNT_ID = Target.the("Integrations first row name")
            .locatedBy("//div[@id='en-ztna-CardRow-0']//div[contains(@id,'CardRow')][contains(@id,'-1')]//span[@data-testid='row-body']//p");
}
