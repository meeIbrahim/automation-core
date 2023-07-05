package ui.com.ztna.web.common.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.Presence;
import serenity.custom.interactions.Refresh;
import ui.com.ztna.web.common.page.Page;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.common.user_interface.SideBarUI.PAGE_TITLE_HAVING_TEXT;
import static ui.com.ztna.web.common.user_interface.SideBarUI.TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT;

public class OpenMenu implements Performable {
    String menu;

    public OpenMenu(String menu) {
        this.menu = menu;
    }

    public static OpenMenu named(String menu) {
        return new OpenMenu(menu);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
//                Check.whether(!CurrentlyEnabled.of(TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT.of(menu)).asABoolean().answeredBy(t))
//                        .andIfSo(
                                Refresh.theBrowser()
//                        )
        );

        t.attemptsTo(
                Check.whether(!Presence.of(PAGE_TITLE_HAVING_TEXT.of(menu)).asABoolean().answeredBy(t))
                        .andIfSo(
                                Click.on(TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT.of(menu)),
                                waitNotPresenceOf(Page.LOADER)
                        )
        );
    }
}
