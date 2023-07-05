package ui.com.ztna.web.pages.services.modals;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddService {

    public static Target CONNECTIVITY_BUTTON = Target.the("Connectivity Button")
            .locatedBy("//*[contains(@id,\"testConnectivity\")]");
    public static Target CONNECTIVITY_PROGRESS = Target.the("Connectivity in Progress")
            .locatedBy("//*[contains(@class,\"MuiCircularProgress\")][./following-sibling::*[contains(text(),\"Connectivity\")]]");
    public static Target CONNECTIVITY_STATUS = Target.the("Connectivity Status")
            .locatedBy("//*[contains(@id,\"testConnectivity\")]/preceding-sibling::*");
    public static Target NAME = Input.BY_ORDER.of("1").called("Application Name");
    public static Target SITE = Dropdown.BY_ORDER.of("1").called("Associated Site");
    public static Target CONNECTOR = Dropdown.BY_ORDER.of("2").called("Associated Service Connector");

    public static String normalizeURL(String url,String protocol){
        return protocol.toLowerCase() + "://" + normalizeURL(url);
        /// Appends URL to start
    }
    public static String normalizeURL(String url){
        String regex = "^.*://(.*)";
        Matcher matcher = Pattern.compile(regex,Pattern.MULTILINE).matcher(url);
        if(matcher.find()){
            url = matcher.group(1);
        }
        return url;
    }
    public static Performable testConnectivity(){
        return Task.where("{0} Tests Connectivity",actor -> {
            int count = 3;
            while (count > 0){
                actor.attemptsTo(
                        Click.on(CONNECTIVITY_BUTTON)
                );
                if(actor.asksFor(connectivityStatus())){break;}
                count--;
            }
            actor.attemptsTo(
                Ensure.thatTheAnswerTo(connectivityStatus()).isTrue()
            );
        });
    }

    public static Question<Boolean> connectivityStatus(){
        return Question.about("Service Connectivity Status").answeredBy(
                actor -> {
                    actor.attemptsTo(
                            Wait.forInvisibilityOf(CONNECTIVITY_PROGRESS).forTime(Duration.ofSeconds(10))
                    );
                    if (CONNECTIVITY_STATUS.resolveAllFor(actor).isEmpty()){return false;}
                    return CONNECTIVITY_STATUS.resolveFor(actor).getText().equalsIgnoreCase("connected");
                }
        );
    }

    public static Performable fillGeneralSettings(String Name, String Connector,String Site){
        return Task.where("{0} fills General Settings",actor -> {
            actor.attemptsTo(
                    Input.of(NAME).with(Name)
            );
            if (Objects.equals(Site, "")){
            int sites = actor.asksFor(Dropdown.of(SITE).contains()).size();
            System.out.println("JERE");
            for(int i = 0;i<sites;i++){
                actor.attemptsTo(
                        Dropdown.of(SITE).select(i+1)
                );
                if (actor.asksFor(Dropdown.of(CONNECTOR).contains()).contains(Connector)){
                   actor.attemptsTo(
                    Dropdown.of(CONNECTOR).select(Connector)
                    );
                    break;
                }
            }}
            else {
                actor.attemptsTo(
                        Dropdown.of(SITE).select(Site),
                        Dropdown.of(CONNECTOR).select(Connector)
                );
            }
            if (!actor.asksFor(Dropdown.of(CONNECTOR).selected()).equals(Connector)){
                throw new IllegalArgumentException("Connector: " + Connector + " not Found in Service Modal!");
            }
        });
    }
}
