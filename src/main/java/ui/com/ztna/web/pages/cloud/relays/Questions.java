package ui.com.ztna.web.pages.cloud.relays;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Parameters of Relay").answeredBy(actor -> {
            String name = row.name();
            String region = row.getCellText(3,actor);
            region = region.substring(region.indexOf("\n") + 1);
            region = region.substring(0,region.indexOf("(") - 1);
            return new ZTAParameters(name,region,"");
        });

    }

    public static Question<Boolean> isHA(Row row){
        return Question.about("Is Relay HA Enabled").answeredBy(actor -> actor.asksFor(row.contains(RelayUI.HA_ENABLED)));
    }
}
