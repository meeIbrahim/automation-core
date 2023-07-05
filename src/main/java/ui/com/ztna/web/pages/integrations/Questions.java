package ui.com.ztna.web.pages.integrations;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    @Override
    public Question<ZTAParameters> getParameters(Row row) {
        return Question.about("Integration Parameters").answeredBy(actor -> {
            String name = row.name();
            String ID = row.getCellText(2,actor);
            return new ZTAParameters(name,ID,"");
        });
    }
}
