package ui.com.ztna.web.pages.ZTA;

import ui.com.ztna.web.pages.services.tab.remote.desktop.remoteDesktop;
import ui.com.ztna.web.pages.services.tab.secure.shell.secureShell;
import ui.com.ztna.web.pages.services.tab.web.webApp;

public class Services {
    private webApp web = new webApp();
    private secureShell shell = new secureShell();
    private remoteDesktop desktop = new remoteDesktop();

    public webApp webApp(){return web;}
    public secureShell secureShell(){
        return shell;
    }
    public remoteDesktop remoteDesktop(){return desktop;}
}
