package ui.com.ztna.web.pages.policies;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

import java.util.Objects;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters> getParameters(Row row){
        return Question.about("Policy Parameters").answeredBy(actor -> {
            String name = row.name();
            String parent = row.getCellText(4,actor);
            parent = parent.substring(parent.indexOf("\n") + 1);
            return new ZTAParameters(name,"",parent);
        });
    }
    public static Question<Boolean> hasRules(Row row){
        return Question.about("Policy ROW has rules".replace("ROW",row.name())).answeredBy(
                actor -> !Objects.equals(actor.asksFor(row.getCellText(5)), "0"));
    }

    public static Question<Boolean> hasProject(Row row){
        return Question.about("Policy on project").answeredBy(
                actor -> {
                     return  row.contains(Target.the("Project Sign").locatedBy("//div[@title='Application Group']")).answeredBy(actor);
                }
        );
    }
}
