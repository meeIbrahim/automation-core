package serenity.custom.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

public class URL {
    public static Question<String> current(){
        return Question.about("Current URL").answeredBy(actor -> {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            return driver.getCurrentUrl();
        });
    }
}
