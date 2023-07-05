package ui.com.ztna.web.pages.applicationGroups.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.applicationGroups.ApplicationGroupsUI;

import static ui.com.ztna.web.common.interactions.CommonInteraction.clickButton;

public class UpdateApplicationGroup {
    public static Target NAME = Input.BY_ORDER.of("1").called("Name of Application Group");
    public static Target DESCRIPTION = Input.BY_ORDER.of("2").called("Description of Application Group");
    public static Target APPLICATIONS = Dropdown.BY_ORDER.of("1").called("Applications");
    public static final Target DESELECT_APPLICATIONS = Target.the("DeSelect Applications")
            .locatedBy("//div[.//p[.='{0}']]/following-sibling::div[contains(@aria-label,'Remove')]");

    public static Performable open(Row row){
        return Task.where("{0} opens Update Application Group Modal for ROW".replace("ROW",row.name()),actor -> {
            actor.attemptsTo(
                    row.action(ApplicationGroupsUI.UPDATE_ACTION_MENU),
                    Modal.waitUntilOpen()
            );
        });
    }

    public  static Performable update(String Name, String Description){
        return Task.where("{0} fills update Modal for Application Group",actor -> {
            actor.attemptsTo(
                    Check.whether(Name.isEmpty()).otherwise(
                            Input.of(NAME).with(Name)
                    ),
                    Check.whether(Description.isEmpty()).otherwise(
                            Input.of(DESCRIPTION).with(Name)
                    )
            );
        });
    }

    public  static Performable updateApplication(String application){
        return Task.where("{0} update Application Group to add Application "+application,actor -> {
            actor.attemptsTo(
                    Check.whether(application.isEmpty()).otherwise(
                            Dropdown.of(APPLICATIONS).select(application)
                    )
            );
        });
    }
    public  static Performable updateAndRemoveApplication(String... applications){
        return Task.where("{0} fills update Modal for Application Group",actor -> {
            for(String application : applications) {
                actor.attemptsTo(
                        Check.whether(application.isEmpty()).otherwise(
                                clickButton(DESELECT_APPLICATIONS.of(application))
                        )
                );
            }
        });
    }
}
