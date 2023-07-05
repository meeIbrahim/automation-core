package serenity.custom.questions;

import com.google.gson.Gson;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public class JSExecutorResponse {
    public static Question<String> asJsonForFetchRequest(String request) {
        return Question.about("Response of Fetch request: \n" + request).answeredBy(actor -> {
            Object response = ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver()).executeAsyncScript(
                    "var callback = arguments[arguments.length - 1];" +
                            "fetch(" + request + ").then(response => response.json())" +
                            ".then(data => callback(data))");
            Gson gson = new Gson();
            String stringResponse = gson.toJson(response);
            return stringResponse;
        });
    }
}
