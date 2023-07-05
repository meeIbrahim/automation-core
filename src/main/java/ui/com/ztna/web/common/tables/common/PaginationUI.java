package ui.com.ztna.web.common.tables.common;

import net.serenitybdd.screenplay.targets.Target;

public class PaginationUI {
    public static final Target CURRENT_PAGE = Target.the("Current Page")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[@aria-current=\"true\"]");
    public static final Target CURRENT_PAGED_NUMBERED = Target.the("Current Page Numbered")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[@aria-current=\"true\"][text()=\"{0}\"]");
    public static final Target PAGE = Target.the("Page Numbered")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[text()=\"{0}\"]");
    public static final Target FIRST_PAGE = Target.the("First Page")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[2]");
    public static final Target LAST_PAGE = Target.the("Last Page")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[last()-1]");
    public static final Target FOOTER = Target.the("Pagination Footer")
            .locatedBy("//div[./*[contains(@class,\"MuiPagination\")]]");
    public static final Target CONTENT = Target.the("Page Content")
            .locatedBy(FOOTER.getCssOrXPathSelector() + "/preceding-sibling::*[1]");
    public static final Target PREVIOUS_PAGE = Target.the("Previous Page")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[position()=1]");
    public static final Target NEXT_PAGE = Target.the("Next Page")
            .locatedBy("(//*/button[contains(@class,\"MuiPagination\")][@aria-label])[position()=last()]");

    public static final Target GO_TO_PAGE = Target.the("Go to page")
            .locatedBy("//button[@aria-label='Go to page {0}']");



    public static final Target FIRST_PAGE_BUTTON = Target.the("First Page Button")
            .locatedBy("//*[contains(@data-testid,\"FeatherChevron\")][1]");
    public static final Target LAST_PAGE_BUTTON = Target.the("Last Page Button")
            .locatedBy("//*[contains(@data-testid,\"FeatherChevron\")][2]");
    public static final Target SELECT_PAGE = Target.the("Jump to Page")
            .locatedBy("//div[./*[contains(text(),\"Jump\")]]/following-sibling::*/input");
    public static final Target RESULTS = Target.the("Results text box")
            .locatedBy("//div[./*[contains(@class,\"MuiPagination\")]]/div[position()=last()]");
}
