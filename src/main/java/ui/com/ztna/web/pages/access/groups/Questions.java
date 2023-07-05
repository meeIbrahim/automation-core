package ui.com.ztna.web.pages.access.groups;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Access Group Parameters").answeredBy(actor -> {
           String name = row.name();

           return new ZTAParameters(name,"","");
        });
    }



}
