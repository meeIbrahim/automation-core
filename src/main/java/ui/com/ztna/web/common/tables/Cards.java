package ui.com.ztna.web.common.tables;

import ui.com.ztna.web.common.page.Page;
import ui.com.ztna.web.common.tables.common.ListingImplementor;

import java.util.List;

public class Cards extends ListingImplementor<Row> {

    /////////////////// TARGETS ///////////////////////////////////
    private final static String ROWS = "//div[contains(@class,\"MuiCard\")]";
    private static final String COLUMNS ="//div[contains(@id,\"CardRowCell\")]";
    private static final String COLUMN ="//*[contains(@id,\"CardRowCell\")][contains(@id,\"{0}\")]";
    private static final String ROW = "//div[contains(@class,\"MuiCard\")][.//*[text()=\"{0}\"]]";
    private static final String COLUMN_ELEMENTS = "//*[contains(@id,\"CardRowCell\")][contains(@id,\"{0}\")]//*[text()=\"{1}\"]";



    /////////////////// ATTRIBUTES ///////////////////////////////////
    private final String Name;

    public Cards(Page page, String Name, String... Headers){
        super(ROWS,COLUMNS,ROW,COLUMN_ELEMENTS,COLUMN,List.of(Headers),page,Row.class );
        this.Name = Name;
    }
}
