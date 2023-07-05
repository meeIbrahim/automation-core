package ui.com.ztna.web.pages.users;

import net.serenitybdd.screenplay.targets.Target;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

public class UsersUI implements ResourceUI {
    public static final Target IMPORT_USERS_BUTTON = Target.the("Import Users button")
            .locatedBy("(//button[@id])[1]");

    public static final Target INVITE_USERS_BUTTON = Target.the("Invite Users button")
            .locatedBy("(//button[@id])[2]");
    public static Target USER_STATUS = Target.the("User Status")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][4]//*[text()=\"{0}\"]");

    public static Target USER_STATUS_PENDING = USER_STATUS.of("Pending").called("Pending User");

    public static Target USER_STATUS_ACTIVE = USER_STATUS.of("Active").called("Active User");
    public static final Target ADD_TO_GROUP_ACTION = Action_Menu.ACTION_BUTTON.of("1").called("Add to Group");
    public static final Target RESEND_INVITE_ACTION = Action_Menu.ACTION_BUTTON.of("2").called("Resend Invite");
    public static final Target REMOVE_USER_ACTION = Action_Menu.ACTION_BUTTON.of("3").called("Remove User");


    @NotNull
    @Override
    public Target removeActionMenu() {
        return REMOVE_USER_ACTION;
    }

    @NotNull
    @Override
    public Target activeStatus() {
        return USER_STATUS_ACTIVE;
    }

    @NotNull
    @Override
    public Target enabledStatus() {
        return USER_STATUS_ACTIVE;
    }

    @NotNull
    @Override
    public Target deletionStatus() {
        return ALWAYS_FALSE.called("Delete status of User");
    }
}
