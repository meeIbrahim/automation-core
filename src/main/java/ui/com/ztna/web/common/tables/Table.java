package ui.com.ztna.web.common.tables;

import constants.Waits;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.common.page.Page;
import ui.com.ztna.web.common.tables.common.ListingImplementor;

import java.util.List;

public class Table extends ListingImplementor<Row> {

    /////////////////// ATTRIBUTES ///////////////////////////////////
    private final String Name;
    private final static String ROWS = "//tr[@id]";
    private static final String COLUMNS ="//th[position()>1 and position()<last()]";
    private static final String ROW = "//tr[@id][.//*[text()=\"{0}\"]]";
    private static final String COLUMN_ELEMENTS = "//td[contains(@id,\"{0}\")]/descendant-or-self::*[text()=\"{1}\"]";
    private static final String COLUMN = "//td[contains(@id,\"{0}\")]";

    /////////////////// TARGETS ///////////////////////////////////
    private static final Target TABLE_BOX = Target.the("Table Container")
            .locatedBy("//*[contains(@class,\"MuiTableContainer\")]");

    private static final Target SORT = Target.the("Sort Button")
            .locatedBy("//th[.//*[text()=\"{0}\"]]//*[@data-testid=\"sort-icon-email\"]");


    private static final Target LOADER = Target.the("Loader Image")
            .locatedBy("//img[@alt=\"Loader\"]");
    public Table(Page page, String Name, String... Headers){
        super(ROWS,COLUMNS,ROW,COLUMN_ELEMENTS,COLUMN,List.of(Headers), page, Row.class);
        this.Name = Name;
    }

    ////// Interactions

    public Performable sortBy(String header){
        return Task.where("{0} Sorts TABLE".replace("TABLE",this.Name),actor -> {
            actor.attemptsTo(
                    Click.on(SORT.of(header)),
                    WaitUntil.the(LOADER, WebElementStateMatchers.isNotPresent()).forNoMoreThan(Waits.FIFTEEN).seconds()
            );
        });
    }

}
