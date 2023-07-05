package com.ztna.steps.web.service;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.services.ServicesUI;
import ui.com.ztna.web.pages.services.modals.AddService;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class ManageServiceSteps {


    private String agentBaseServiceId;
    String UPDATED_SERVICE_NAME = "new_service_name";



    @When("User disables webApp {reference}")
    public void userDisablesWebApp(String reference) throws cachedNotFound {
        ZTAParameters serviceParams = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(serviceParams.name));
        theActorInTheSpotlight().attemptsTo(
                service.action(ServicesUI.MANAGE_STATE_ACTION_MENU),
                Modal.save()
        );

    }
    @When("User tries to disable webApp {reference}")
    public void userTriesToDisablesWebApp(String reference) throws cachedNotFound {
        ZTAParameters serviceParams = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(serviceParams.name));
        theActorInTheSpotlight().attemptsTo(
                service.action(ServicesUI.MANAGE_STATE_ACTION_MENU));

    }


    @When("User updates webApp {reference}")
    public void userUpdatesWebApp(String reference) throws cachedNotFound {
        ZTAParameters serviceParams = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(serviceParams.name));
        String newName = Data.generateName();
        theActorInTheSpotlight().attemptsTo(
                service.action(ServicesUI.UPDATE_APP_ACTION_MENU),
                Modal.waitUntilOpen(),
                Wait.forInvisibilityOf(Modal.MODAL_LOAD_ICON),
                Input.of(AddService.NAME).with(newName),
                Modal.next(),
                Modal.next(),
                ZTA.services().webApp().checkConnectivity(),
                Ensure.thatTheAnswerTo(
                        AddService.connectivityStatus()
                ).isTrue(),
                Modal.save()
        );
        theActorInTheSpotlight().remember(UPDATED_SERVICE_NAME,newName);

    }
    @Then("WebApp {reference} should be updated")
    public void userVerifiesServiceUpdate(String reference) throws cachedNotFound {
        ZTAParameters serviceParams = RM.service().cache().retrieve(reference);
        String newName = theActorInTheSpotlight().recall(UPDATED_SERVICE_NAME);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        ZTA.services().webApp().table().contains(serviceParams.name)
                ).isFalse(),
                Ensure.thatTheAnswerTo(
                        ZTA.services().webApp().table().contains(newName)
                ).isTrue()

        );
    }

    @Then("WebApp {reference} should be disabled")
    public void webAppIsDisabled(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.service().cache().retrieve(reference);
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Wait.forQuestion(
                        service.contains(ServicesUI.IN_PROGRESS_STATUS)
                ).forTime(Duration.ofMinutes(3).plusSeconds(5))
                                .untilEqualTo(false),
                Ensure.thatTheAnswerTo(
                        service.contains(ServicesUI.SERVICE_STATUS)
                ).isTrue()
        );
    }
    @Then("Disable should not be available as an option")
    public void DisableButtonNotAvailable(){
        List<WebElementFacade> buttons =  Modal.MODAL_FOOTER_BUTTONS.resolveAllFor(theActorInTheSpotlight());
        List<String> buttonText = buttons.stream().map(WebElementFacade::getText).collect(Collectors.toList());
        for (String text : buttonText){
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(
                            text.toLowerCase().contains("disable")
                    ).isFalse()
            );
        }
    }

}
