package ui.com.ztna.web.common.Wait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.cucumber.java.bs.A;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.annotations.Subject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

import static ui.com.ztna.web.common.Wait.WaitPerformable.getWait;

public class QuestionWaits<T> implements Performable {
    Duration timeout;
    Question<T> question;
    T expectedAnswer;

    String Subject;
    String Step;
    Boolean hardAssert = true;
    public QuestionWaits(Duration timeout, Question<T> question, T expectedAnswer){
        this.timeout = timeout;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
        this.Subject = question.getSubject();
    }
    public QuestionWaits(Duration timeout, Question<T> question){
        this.timeout = timeout;
        this.question = question;
        this.Subject = question.getSubject();
    }
    public QuestionWaits<T> forTime(Duration timeout){
        this.timeout = timeout;
        this.Step = "{0} waits for " + this.Subject + " for Duration of " + timeout.toSeconds() + "Seconds";
        return this;
    }
    public QuestionWaits<T> untilEqualTo(T expectedAnswer){
        this.expectedAnswer = expectedAnswer;
        this.Step = "{0} waits for " + this.Subject + " until Equal to Expected Answer";
        return this;
    }
    public Question<Boolean> isSuccess(){
        String subject = "Wait for Question:  <" + this.Subject + ">  until <" + this.timeout.toSeconds() + "> seconds";
        if (this.expectedAnswer!=null){
            subject = subject + " to equal to <" + this.expectedAnswer +"> ";
        }
        return Question.about(subject).answeredBy(this::softAssert);
    }
    public QuestionWaits<T> dontFail(){
        this.hardAssert = false;
        return this;
    }

    @Override
    @Step("#Step")
    public <S extends Actor> void performAs(S actor) {
        try {
            if (this.expectedAnswer == null) {
                getAnswer(actor);
            } else {
                compareAnswer(actor);
            }
        }
        catch (Exception e){
            if (hardAssert){throw e;}
        }
    }

    protected <S extends Actor> boolean softAssert(S actor){
        try {
            performAs(actor);

        } catch (Exception ignored){
            return false;
        }
        return true;
    }



    protected <S extends Actor> void getAnswer(S actor) {
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = getWait(actor,timeout);
        wait.until(new Function<WebDriver, T>() {
            @Override
            public T apply(WebDriver driver) {
                try {
                    return question.answeredBy(actor);
                } catch (Exception e){
                    return null;
                }
            }
        });
    }


    protected void compareAnswer(Actor actor){
        org.openqa.selenium.support.ui.Wait<WebDriver> wait = getWait(actor,timeout);
            wait.until(new Function<WebDriver, Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return question.answeredBy(actor) == expectedAnswer;
                }
            });
    }



}
