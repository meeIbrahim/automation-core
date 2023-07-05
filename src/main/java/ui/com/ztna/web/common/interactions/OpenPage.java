package ui.com.ztna.web.common.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.waits.WaitUntil;
import serenity.custom.interactions.Refresh;
import ui.com.ztna.web.common.page.Page;

import static constants.Waits.THIRTY;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.user_interface.SideBarUI.*;

public class OpenPage implements Performable {
    String pageName;
    String tabName;

    public OpenPage(String pageName) {
        this.pageName = pageName;
    }

    public static OpenPage named(String pageName) {
        return new OpenPage(pageName);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
//                Check.whether(!CurrentlyEnabled.of(TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT.of(tabName)).asABoolean().answeredBy(t))
//                        .andIfSo(
                                Refresh.theBrowser()
//                        )
        );

        t.attemptsTo(
                Check.whether(!Presence.of(PAGE_TITLE_HAVING_TEXT.of(pageName)).asABoolean().answeredBy(t))
                        .andIfSo(
                                WaitUntil.the(TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT.of(tabName),isClickable()).forNoMoreThan(THIRTY).seconds(),
                                Click.on(TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT.of(tabName)),
                                WaitUntil.the(SUB_LIST_ITEMS_HAVING_TEXT.of(pageName),isClickable()).forNoMoreThan(THIRTY).seconds(),
                                Click.on(SUB_LIST_ITEMS_HAVING_TEXT.of(pageName)),
                                waitNotPresenceOf(Page.LOADER)
                        )
        );
    }

    public OpenPage in(String tabName) {
        this.tabName = tabName;
        return this;
    }
}
