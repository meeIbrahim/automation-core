package ui.com.ztna.web.pages.integrations.event.collector.modals;


import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;

import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Dropdown;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.input.Toggle;

import ui.com.ztna.web.pages.integrations.event.collector.EventCollectorUI;
import ui.com.ztna.web.pages.integrations.event.collector.Splunk;



public class AddSplunk {
    public static Performable open(){
        return Task.where("{0} opens Add Splunk Modal",actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(EventCollectorUI.SPLUNK_ADD_BUTTON),
                    Click.on(EventCollectorUI.SPLUNK_ADD_BUTTON),
                    Modal.waitUntilOpen()
            );
        });
    }

    public static Performable configure(String Host, String Port,String AuthToken, Boolean isHttps, Boolean SSL){
        return Task.where("{0} configures Splunk Settings",actor -> {
            String URI_VALUE = "PROTOCOL://HOST:PORT" + Splunk.URI_ENDPOINT;
            URI_VALUE = URI_VALUE.replace("HOST",Host).replace("PORT",Port);
            URI_VALUE = isHttps ? URI_VALUE.replace("PROTOCOL","https") : URI_VALUE.replace("PROTOCOL", "http");
            actor.attemptsTo(
                    Input.of(Splunk.HOST).with(Host),
                    Input.of(Splunk.PORT).with(Port),
                    Input.of(Splunk.AUTH_TOKEN).with(AuthToken),
                    Check.whether(isHttps).andIfSo(
                            Dropdown.of(Splunk.PROTOCOL).select("HTTPS"),
                            Check.whether(SSL)
                                    .andIfSo(Toggle.of(Splunk.VERIFY_SSL).enable())
                                    .otherwise(Toggle.of(Splunk.VERIFY_SSL).disable())
                    ).otherwise(
                            Dropdown.of(Splunk.PROTOCOL).select("HTTP")
                            ),
                    Ensure.thatTheAnswerTo(splunkURI()).isEqualTo(URI_VALUE)
            );
        });
    }
    public static Question<String> splunkURI(){
        return Question.about("splunk URI").answeredBy(actor -> actor.asksFor(Input.of(Splunk.URI).value()));
    }
}
