package resource.manager.collections;

import resource.manager.provider.remoteDesktop;
import resource.manager.provider.secureShell;
import resource.manager.provider.webApp;

public class ServiceCollection {
    private final remoteDesktop remoteDesktopService = new remoteDesktop();
    private final secureShell secureShellService = new secureShell();
    private final webApp webAppService = new webApp();
    private final ServiceCache cache;
    public ServiceCollection(){
        this.cache = new ServiceCache(remoteDesktopService.cache(),
                secureShellService.cache(),
                webAppService.cache());
    }

    public  webApp webApp(){return  webAppService;}
    public  secureShell secureShell(){return  secureShellService;}
    public  remoteDesktop remoteDesktop(){return  remoteDesktopService;}

    public ServiceCache cache(){return cache;}

}
