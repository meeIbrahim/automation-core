package ui.com.ztna.web.common.Wait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class CustomConditions {
    public static ExpectedCondition<Boolean> timeout(Duration timeout){
        long StartTime = System.currentTimeMillis();
        return new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver input) {
                return (System.currentTimeMillis() - StartTime) > timeout.toMillis();
            }
        };
    }
}
