package ui.com.ztna.web.common.page;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.web.common.tables.Row;


public interface ResourceQuestions {
    Question<ZTAParameters> getParameters(Row row);
}
