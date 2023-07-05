package ui.com.ztna.web.pages.sites;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.input.MultiSelect;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.sites.modals.AddSite;

import java.util.ArrayList;
import java.util.List;

public class Questions implements ResourceQuestions {

    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Parameters of Site").answeredBy(actor -> {
            String name =  row.name();
            row.menu().action(SiteUi.UPDATE_SITE_ACTION_MENU).performAs(actor);
            List<String> relays = new ArrayList<>();
            relays = MultiSelect.of(AddSite.CLOUD_RELAY_DROPDOWN).selectedValues().answeredBy(actor);
            String parentRelay = relays.isEmpty() ? "" : relays.get(0);
            ZTAParameters SiteParams =  new ZTAParameters(name,"",parentRelay);
            actor.attemptsTo(Modal.close());
            return SiteParams;
        });
    }

}
