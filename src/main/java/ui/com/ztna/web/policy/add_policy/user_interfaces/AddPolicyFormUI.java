package ui.com.ztna.web.policy.add_policy.user_interfaces;

import net.serenitybdd.screenplay.targets.Target;

public class AddPolicyFormUI {

    public static final Target NAME_OF_POLICY_FIELD = Target.the("name of policy field")
            .locatedBy("//input[@name='name']");

    public static final Target NAME_OF_POLICY_FIELD_VALUE = Target.the("name of policy field value")
            .locatedBy("//input[@name='name'][@value='{0}']");

    public static final Target DESCRIPTION_FIELD = Target.the("description field")
            .locatedBy("//textarea[@name='description']");

    public static final Target DESCRIPTION_FIELD_VALUE = Target.the("description field value")
            .locatedBy("//textarea[@name='description'][.='{0}']");

    public static final Target RESOURCE_TYPE_DROPDOWN_BUTTON = Target.the("resource type dropdown button")
            .locatedBy("//div[.//div[contains(@class,'react-select__single-value')]]/div[contains(@class,'react-select__control')]//div[contains(@class, 'react-select__indicators')]");

    public static final Target RESOURCE_NAME_DROPDOWN_BUTTON = Target.the("resource name dropdown button")
            .locatedBy("//div[./p[contains(text(),'Select')]]//div[.//div[contains(@class,'react-select__placeholder')]]/div[contains(@class,'react-select__control')]//div[contains(@class, 'react-select__indicators')]");

    public static final Target RESOURCE_TYPE_DROPDOWN_OPTION = Target.the("resource type dropdown option")
            .locatedBy("//div[contains(@class,'react-select__option')][.='{0}']");

    public static final Target RESOURCE_NAME_DROPDOWN_OPTION = Target.the("resource name dropdown option")
            .locatedBy("//div[contains(@class, 'react-select__option')]//p[.='{0}']");

    public static final Target ADD_POLICY_BUTTON = Target.the("add policy button")
            .locatedBy("#en-ztna-modal-saveButton");

    public static final Target ADD_POLICY_FORM_TITLE = Target.the("add policy form title")
            .locatedBy("//h2[.='Add Policy']");
}
