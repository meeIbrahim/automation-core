package ui.com.ztna.web.pages.users;

import ui.com.ztna.web.common.filters.Query;

public class Filters {

    public static Query USERNAME = new Query("Username");
    public static Query STATUS_PENDING = new Query("Status").with("PENDING");
}
