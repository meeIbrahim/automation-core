package ui.com.ztna.web.policy.delete_policy.user_interfaces;
import net.serenitybdd.screenplay.targets.Target;

public class DeleteAllPoliciesUI {

    public static final Target FIRST_ROW_POLICY_NAME = Target.the("First row policy name")
            .locatedBy("//div[@id='en-ztna-CardRow-0']//div[@id='CardRowCell-name-0']//span[@data-testid='row-body']//p");

    public static final Target First_ROW_POLICY_ACTION_ICON = Target.the("First row policy action icon button")
            .locatedBy("//div[@id='en-ztna-CardRow-0'][.//div[@id='CardRowCell-name-0']//span[@data-testid='row-body'][.//p='{0}']]//button[@id='en-ztna-CardRow-0-actionIcon']");
}
