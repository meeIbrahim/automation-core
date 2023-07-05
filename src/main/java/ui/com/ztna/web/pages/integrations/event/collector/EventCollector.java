package ui.com.ztna.web.pages.integrations.event.collector;


import ui.com.ztna.web.common.page.Page;

public class EventCollector implements Page {
    static Splunk splunk;
    @Override
    public String url() {
        return "/app/event-collector";
    }
    public Splunk splunk(){
        if (splunk == null){splunk = new Splunk();}
        return splunk;
    }
}
