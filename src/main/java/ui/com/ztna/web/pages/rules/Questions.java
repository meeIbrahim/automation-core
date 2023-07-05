package ui.com.ztna.web.pages.rules;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Rule Parameters").answeredBy(actor -> {
            String name = row.name();
            String parent = actor.asksFor(getPolicy());
            ZTAParameters params = new ZTAParameters(name,"",parent);
            return params;
        });

    }

    public static Question<String> getPolicy(){
        return Question.about("Parent Policy's Name").answeredBy(actor -> RulesUI.PARENT_POLICY.resolveFor(actor).getText());
    }
    public static Question<Boolean> isActive(Row row){
        return Question.about("Is Rule ROW active".replace("ROW",row.name())).answeredBy(actor -> {
            return actor.asksFor(row.contains(RulesUI.RULE_STATUS_ACTIVE));
        });
    }
}
