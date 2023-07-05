package serenity.custom.performable.conditional;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;


public class KeywordPerformableBuilder {
    String[] keyword;

    public KeywordPerformableBuilder(String[] keyword) {
        this.keyword = keyword;
    }

    public KeywordPerformable performing(Performable... tasks) {
        return new KeywordPerformable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                t.attemptsTo(tasks);
            }
        }.setKeywords(keyword);
    }

}
