package ui.com.ztna.web.pages.services.tab.remote.desktop;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.services.Services;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;
import ui.com.ztna.web.pages.services.tab.remote.desktop.modals.AddRemoteDesktop;
import ui.com.ztna.web.parameters.remoteDesktopParameters;


public class remoteDesktop extends Services<remoteDesktopParameters> {
    @Override
    public String url() {
        return "/app/rdp-vnc";
    }

    @Override
    public Performable create(remoteDesktopParameters parameters) {
        return Task.where("{0} adds remote Desktop Service",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddRemoteDesktop.open(),
                                    AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                                    Modal.next(),
                                    AddRemoteDesktop.fillApplicationInfo(parameters.protocol,parameters.url,parameters.port),
                                    Modal.next(),
                                    AddService.testConnectivity(),
                                    Modal.save()
                            );
                        }
                    },ServicesUI.SERVICE_STATUS.of("Active"),ServicesUI.ACTIVE_SERVICE)
            );
        });
    }

    @Override
    public Question<Boolean> testConnectivity(remoteDesktopParameters parameters) {
        return Question.about("Connectivity Status of remote Desktop").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage(),
                    AddRemoteDesktop.open(),
                    AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                    Modal.next(),
                    AddRemoteDesktop.fillApplicationInfo(parameters.protocol,parameters.url,parameters.port),
                    Modal.next(),
                    checkConnectivity()
                    );
            Boolean test = actor.asksFor(AddService.connectivityStatus());
            actor.attemptsTo(
                    Modal.close()
            );
            return test;
        });

    }

    @Override
    public Performable removeAssociations(String name) {
        return super.removeAssociations(name);
    }
}
