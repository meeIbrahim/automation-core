package ui.com.ztna.web.pages.integrations.event.collector;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;

import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.Toggle;

import ui.com.ztna.web.pages.integrations.event.collector.modals.AddSplunk;
import ui.com.ztna.web.parameters.SplunkParameters;

import java.time.Duration;
import java.util.List;

public class Splunk {
    public static Target HOST = Input.BY_ORDER.of("1").called("Splunk Host Input");
    public static Target PORT = Input.BY_ORDER.of("2").called("Splunk Port Input");
    public static Target PROTOCOL = Dropdown.BY_ORDER.of("1").called("Splunk Protocol Dropdown");
    public static Target VERIFY_SSL = Toggle.BY_ORDER.of("1").called("Splunk SSL Toggle");
    public static Target URI = Input.BY_ORDER.of("3").called("Splunk URI");
    public static Target AUTH_TOKEN = Input.BY_ORDER.of("4").called("Splunk Auth Token Input");

    public static String URI_ENDPOINT = "/services/collector/event";

    static EventCollector eventCollector;

    public Performable setUp(SplunkParameters parameters){
        return Task.where("{0} configures Splunk Event Collector",actor -> {
            actor.attemptsTo(
                    eventCollector().openPage(),
                    AddSplunk.open(),
                    Modal.next(),
                    AddSplunk.configure(parameters.host, parameters.port,parameters.token, parameters.isHttps,parameters.verifySSL),
                    Modal.next(),
                    Wait.forVisibilityOf(Modal.MODAL_NEXT_BUTTON).forTime(Duration.ofSeconds(30)),
                    Modal.save(),
                    Ensure.thatTheAnswerTo(isConnected()).isTrue()
            );
        });
    }

    public Performable remove(){
        return Task.where("{0} removes Splunk event Collector",actor -> {
            actor.attemptsTo(
                    Check.whether(isConnected()).andIfSo(
                            Click.on(EventCollectorUI.SPLUNK_REMOVE_BUTTON),
                            Modal.waitUntilOpen(),
                            Modal.save()
                    ),
                    Ensure.thatTheAnswerTo(isConnected()).isFalse()
            );
        });
    }

    public Question<Boolean> isConnected(){
        return Question.about("is Splunk Connected").answeredBy(actor -> {
            List<WebElementFacade> elements = EventCollectorUI.SPLUNK_STATUS.resolveAllFor(actor);
            if (elements.isEmpty()){return false;}
            return elements.get(0).getText().toLowerCase().contains("connected");
        });
    }

    EventCollector eventCollector(){
        if (eventCollector == null){eventCollector = new EventCollector();}
        return eventCollector;
    }
}
