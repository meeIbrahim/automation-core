package ui.com.ztna.web.pages.services.tab.secure.shell;


import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.pages.services.Services;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;
import ui.com.ztna.web.pages.services.tab.secure.shell.modals.AddSecureShell;
import ui.com.ztna.web.parameters.secureShellParameters;

public class secureShell extends Services<secureShellParameters> {

    @Override
    public String url() {
        return "/app/ssh";
    }

    @Override
    public Performable create(secureShellParameters parameters) {
        return Task.where("{0} Adds a secure Shell Service",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddSecureShell.open(),
                                    AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                                    Modal.next(),
                                    AddSecureShell.fillApplicationInfo(parameters.url,parameters.port),
                                    Modal.next(),
                                    AddService.testConnectivity(),
                                    Modal.save()
                            );
                        }
                    }, ServicesUI.SERVICE_STATUS.of("Active"),ServicesUI.ACTIVE_SERVICE)
            );
        });
    }

    @Override
    public Question<Boolean> testConnectivity(secureShellParameters parameters) {
        return Question.about("Connectivity Status of secure Shell").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage(),
                    AddSecureShell.open(),
                    AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                    Modal.next(),
                    AddSecureShell.fillApplicationInfo(parameters.url,parameters.port),
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
