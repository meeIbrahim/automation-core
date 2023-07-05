package ui.com.ztna.web.pages.services;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Parameters for web App").answeredBy(
                actor -> {
                    String name = row.name();
                    String connector = row.getCellText(5,actor);
                    String url = row.getCellText(4,actor);
                    return new ZTAParameters(name,url,connector);
                }
        );
    }
}
