package ui.com.ztna.web.common.Wait;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class Wait {
    public static WaitPerformable forPresenceOf(Target target){
        WaitPerformable wait = new WaitPerformable(target);
        return wait.condition(ExpectedConditions.presenceOfElementLocated(By.xpath(target.getCssOrXPathSelector())),"for Presence");
    }


    public static WaitPerformable forInvisibilityOf(Target target){
        WaitPerformable wait = new WaitPerformable(target);
        return wait.condition(ExpectedConditions.invisibilityOfElementLocated(By.xpath(target.getCssOrXPathSelector())),"for Invisibility");
    }

    public static WaitPerformable forInvisibilityOf(WebElementFacade element){
        WaitPerformable wait = new WaitPerformable();
        return wait.condition(ExpectedConditions.invisibilityOf(element),"for Invisibility");
    }

    public static WaitPerformable forVisibilityOf(Target target){
        WaitPerformable wait = new WaitPerformable(target);
        return wait.condition(ExpectedConditions.visibilityOfElementLocated(By.xpath(target.getCssOrXPathSelector())),"for Visibility");
    }

    public static WaitPerformable forTime(Duration Wait){
        WaitPerformable wait = new WaitPerformable();
        return wait.condition(CustomConditions.timeout(Wait),"for Z Seconds".replace("Z",String.valueOf(Wait.toSeconds())))
                .forTime(Wait.plusSeconds(1));
    }
    public static <T> QuestionWaits<T> forQuestion(Question<T> question){ /// Question
        return new QuestionWaits<T>(Duration.ofSeconds(5),question);
    }


}
