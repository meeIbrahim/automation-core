package ui.com.ztna.web.common.user_interface;

import net.serenitybdd.screenplay.targets.Target;

public class SideBarUI {

    public static final String TENANTS_MENU = "Tenants";
    public static final String IDENTITY_PROVIDERS_MENU = "Identity Providers";
    public static final String INSIGHTS_MENU = "Insights";
    public static final String DRAP_MENU = "DRAP";

    public static final String IAM_MENU = "IAM";
    public static final String DEVICES_MENU = "Devices";
    public static final String RESOURCES_MENU = "Resources";
    public static final String INTEGRATIONS_MENU = "Integrations";
    public static final String SERVICES_MENU = "Services";
    public static final String POLICIES_MENU = "Policies";
    public static final String SETTINGS_MENU = "Settings";

    public static final String EVENT_COLLECTOR = "Event Collector";
    public static final String PUBLIC_CLOUD_PAGE = "Public Cloud";
    public static final String USERS_PAGE = "Users";
    public static final String ACCESS_GROUP_PAGE = "Access Groups";
    public static final String ASSOCIATED_DEVICES_PAGE = "Associated Devices";
    public static final String DEVICE_POSTURE_CHECK_PAGE = "Device Posture Check";
    public static final String INSIGHTS_PAGE = "Insights";
    public static final String SITES_PAGE = "Sites";
    public static final String IDENTITY_PROVIDERS_PAGE = "Identity Providers";

    public static final String LINKED_DEVICES_PAGE = "Linked Devices";
    public static final String DOWNLOADS = "Downloads";
    public static final String SERVICE_HOSTS_PAGE = "Service Hosts";
    public static final String SERVICES_PAGE = "Application Services";
    public static final String NETWORK_SERVICE_PAGE = "Network Services";
    public static final String PROJECTS_PAGE = "Projects";
    public static final String APPLICATION_POLICIES_PAGE = "Application Policies";
    public static final String RELAY_NODES_PAGE = "Relay Nodes";

    public static final String SERVICE_CONNECTORS_PAGE = "Service Connectors";
    public static final String CLOUD_HOSTED_RELAYS_PAGE = "Cloud Hosted Relays";
    public static final String HTTP_HTTPS_PAGE = "HTTP/HTTPS";
    public static final String SSH_PAGE = "SSH";
    public static final String RDP_VNC_PAGE = "RDP/VNC";
    public static final String HTTP_HTTPS_TAB = "Web";

    public static final String SSH_TAB = "Secure Shell";

    public static final String RDP_VNC_TAB = "Remote Desktop";
    public static final String RDP_SSH_VNC_PAGE = "RDP/SSH/VNC";
    public static final String LOAD_BALANCERS_PAGE = "Load Balancers";
    public static final String NODE_PAGE = "Nodes";



    public static final Target IAM_SIDEBAR_LABEL = Target.the("IAM sidebar label")
            .locatedBy("//div[./span='IAM']");

    public static final Target TOP_LEVEL_SIDE_BAR_ITEMS = Target.the("top level side bar items")
            .locatedBy("//div[./div[contains(@class,'MuiListItemIcon')]]//span[contains(@class,'MuiListItemText')]");

    public static final Target ROOT_DIV = Target.the("Root Div").locatedBy("//div[@role='presentation']");

    public static final Target TOP_LEVEL_SIDE_BAR_ITEMS_HAVING_TEXT = Target.the("top level side bar items")
            .locatedBy("//div[./div[contains(@class,'MuiListItemIcon')]][@id=\"en-ztna-NavCollapseItem-{0}\"]");

    public static final Target SUB_LIST_ITEMS_HAVING_TEXT = Target.the("sub list items")
            .locatedBy("//div[contains(@class, 'Popover')]//span[contains(@class,'MuiListItem')][.='{0}']");

//    public static final Target ROOT_DIV = Target.the("Root Div").locatedBy("//div[@role='presentation']");

    public static final Target PAGE_TITLE = Target.the("Page Title").locatedBy("//span[text()='{0}']");



    public static final Target PAGE_TITLE_HAVING_TEXT = Target.the("page title having text")
            .locatedBy("//main/div/div[1]/div/span[contains(text(), '{0}')]");
}
