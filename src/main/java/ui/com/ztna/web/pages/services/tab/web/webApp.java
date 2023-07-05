package ui.com.ztna.web.pages.services.tab.web;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import ui.com.ztna.web.common.page.Modal;

import ui.com.ztna.web.pages.services.Services;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;
import ui.com.ztna.web.pages.services.tab.web.modals.AddPrivate;
import ui.com.ztna.web.parameters.webAppParameters;


public class webApp extends Services<webAppParameters> {

    @Override
    public String url() {
        return "/app/http-https";
    }
    @Override
    public Performable create(webAppParameters parameters) {
        return Task.where("{0} Adds web App Service",actor -> {
            actor.attemptsTo(
                    openPage(),
            waitForNewResource(new Performable() {
                @Override
                public <T extends Actor> void performAs(T actor) {
                    actor.attemptsTo(
                            AddPrivate.open(),
                            AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                            Modal.next(),
                            AddPrivate.fillApplicationInfo(parameters.protocol,parameters.url,parameters.hidden),
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
    public Question<Boolean> testConnectivity(webAppParameters parameters) {
        return Question.about("Connectivity Status of Web App").answeredBy(actor -> {
           actor.attemptsTo(
                   openPage(),
                   AddPrivate.open(),
                   AddService.fillGeneralSettings(parameters.name,parameters.connector,parameters.site),
                   Modal.next(),
                   AddPrivate.fillApplicationInfo(parameters.protocol,parameters.url,parameters.hidden),
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
