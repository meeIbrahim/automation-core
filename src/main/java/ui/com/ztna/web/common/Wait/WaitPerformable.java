package ui.com.ztna.web.common.Wait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;


public class WaitPerformable implements Performable {
    Duration timeout;
    Target target;
    String Name;
    String waitName;
    ExpectedCondition<?> condition;
    Boolean hardAssert = true;
    public WaitPerformable(){
        this.timeout = Duration.ofSeconds(2);
        condition = null;
        target = null;
        this.Name = "Timeout";
    }
    WaitPerformable(Target target){
        this.timeout = Duration.ofSeconds(2);
        condition = null;
        this.target = target;
        this.Name = target.getName();
    }
    public WaitPerformable(Target target, ExpectedCondition<?> condition, Duration timeout, String waitName){
        this.timeout = timeout;
        this.condition = condition;
        this.target = target;
        if (this.target != null){
            this.Name = target.getName();
        }
        else {
            this.Name = "Timeout";
        }
        this.waitName = waitName;
    }
    public WaitPerformable condition(ExpectedCondition<?> condition, String waitName){
        this.condition = condition;
        this.waitName = waitName;
        return Instrumented.instanceOf(WaitPerformable.class).withProperties(this.target,this.condition,this.timeout,this.waitName);
    }
    public WaitPerformable forTime(Duration Time){
        this.timeout = Time;
        return Instrumented.instanceOf(WaitPerformable.class).withProperties(this.target,this.condition,this.timeout,this.waitName);
    }

    public Question<Boolean> isSuccess(){
        return Question.about("{0} is Wait Successful").answeredBy(this::softAssert);
    }
    @Override
    @Step("{0} waits #waitName of #Name")
    public <T extends Actor> void performAs(T actor) {
            hardAssert(actor);
    }

    protected  <T extends Actor> Boolean softAssert(T actor){
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = getWait(actor,timeout);
        try {
            wait.until(condition);
        } catch (Exception ignored){
            return false;
        }
        return true;
    }
    protected  <T extends Actor> void hardAssert(T actor){
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = getWait(actor,timeout);
        wait.until(condition);
    }

    public static void main(String[] args) {

    }

    static org.openqa.selenium.support.ui.Wait<WebDriver> getWait(Actor actor,Duration timeout){
        long timeoutMultiplier = 1;
        try {
            EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
            String WaitMultiplier = env.getProperty("serenity.wait.timeout.multiplier");
            timeoutMultiplier = Long.parseLong(WaitMultiplier);
        }
        catch (Exception ignored){}
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        return new FluentWait<>(driver).withTimeout(timeout.multipliedBy(timeoutMultiplier))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(ElementNotFoundException.class)
                .ignoring(NoSuchElementException.class);
    }
}
