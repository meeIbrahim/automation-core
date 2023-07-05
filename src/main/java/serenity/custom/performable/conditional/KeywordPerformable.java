package serenity.custom.performable.conditional;

import net.serenitybdd.screenplay.Performable;


public abstract class KeywordPerformable implements Performable {
    String[] keywords;



    public KeywordPerformable setKeywords(String... keywords) {
        this.keywords = keywords;
        return this;
    }


    public String[] getKeywords() {
        return keywords;
    }

    public static KeywordPerformableBuilder withNames(String... keyword) {
        return new KeywordPerformableBuilder(keyword);
    }
}
