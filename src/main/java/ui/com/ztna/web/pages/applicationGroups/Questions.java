package ui.com.ztna.web.pages.applicationGroups;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.By;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.applicationGroups.modals.AddApplicationGroup;

import java.util.ArrayList;
import java.util.List;

import static ui.com.ztna.web.common.input.MultiSelect.ENABLED;

public class Questions implements ResourceQuestions {

    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Parameters of Application Group").answeredBy(actor -> {
            String name =  row.name();
            row.menu().action(ApplicationGroupsUI.UPDATE_ACTION_MENU).performAs(actor);
            List<String> services = new ArrayList<>();
            services = enabledValues(MultiSelect.of(AddApplicationGroup.SERVICES_MULTISELECT)).answeredBy(actor);
            actor.attemptsTo(Modal.close());
            if (services.isEmpty()) {
                return new ZTAParameters(name, "","");
                            }
            else{
                return new ZTAParameters(name, services.get(0),"");
            }

        });

    }


    public static Question<List<String>> enabledValues(MultiSelect multiSelect){
        return Question.about("enabled values").answeredBy(actor ->  {

            List<WebElementFacade> elements = multiSelect.selectedElements().answeredBy(actor);
            List<String> values = new ArrayList<>();
            for( WebElementFacade element : elements){
                if(element.findElement(By.xpath(ENABLED)).getCssValue("background-color").equals("rgba(102, 188, 83, 1)")) {
                    values.add(element.getTextValue());
                }
            }
            return (values);

        });
    }

    public static Question<Boolean> hasServices(Row row){
        return Question.about("Project has Services").answeredBy(actor -> {
           String applications = row.getCellText(2,actor);
           return !applications.equals("0");
        });
    }


}
