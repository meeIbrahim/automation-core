package ui.com.ztna.web.pages.integrations.event.collector.modals;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.pages.integrations.event.collector.EventCollectorUI;

public class EditSplunk {

    public static Performable open(){
        return Task.where("{0} opens Edit Splunk Modal",actor -> {
            actor.attemptsTo(
                    Wait.forVisibilityOf(EventCollectorUI.SPLUNK_EDIT_BUTTON),
                    Click.on(EventCollectorUI.SPLUNK_EDIT_BUTTON),
                    Modal.waitUntilOpen()
            );
        });
    }
    public static Question<String> splunkURI(){
        return AddSplunk.splunkURI();
    }
}
