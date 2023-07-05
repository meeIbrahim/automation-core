package com.ztna.steps.web.service;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import postgresql.databases.ResourceDb;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.services.ServicesUI;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class DeleteServiceSteps {





    @Given("User changes status of webApp {reference} to DELETE_IN_PROGRESS")
    public void userChangesStatusToDelete(String reference) throws cachedNotFound {
        ZTAParameters service = RM.service().cache().retrieve(reference);
        ResourceDb.updateDataBase("resources_service", "name", service.name,"status","DELETE_IN_PROGRESS");
        ResourceDb.updateDataBaseTime("resources_service", "name", service.name, "last_status_update", "now()");
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row serviceRow  = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(service.name));
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().table().refresh(),
                Ensure.thatTheAnswerTo(
                        serviceRow.contains(ZTA.services().webApp().ui().deletionStatus())
                ).isTrue()
        );

    }


    @Then("User verifies DELETE_IN_PROGRESS state for webApp {reference}")
    public void userVerifiesDeleteStuck(String reference) throws cachedNotFound {
        ZTAParameters serviceParams = RM.service().cache().retrieve(reference);
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(serviceParams.name));
        theActorInTheSpotlight().attemptsTo(
                service.menu().open(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(Visibility.of(ServicesUI.FORCE_REMOVE_ENABLED).asABoolean())
                                .forTime(Duration.ofMinutes(2).plusSeconds(45))
                                .isSuccess()
                ).isFalse(),
                service.menu().open(),
                Wait.forQuestion(service.menu().contains(ServicesUI.FORCE_REMOVE_ENABLED))
                        .forTime(Duration.ofSeconds(30)),
                service.action(ServicesUI.FORCE_REMOVE_ENABLED),
                Modal.waitUntilOpen(),
                Input.of(Input.BY_ORDER.of("1")).with(serviceParams.name),
                Modal.save(),
                Ensure.thatTheAnswerTo(
                                Wait.forQuestion(service.exists())
                                        .untilEqualTo(false)
                                        .isSuccess())
                        .isTrue()
        );
    }


    @When("User tries to delete webApp {reference}")
    public void userAttemptsDeletionWebApp(String reference) throws cachedNotFound {
        ZTAParameters serviceParameters = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row service = theActorInTheSpotlight().asksFor(ZTA.services().webApp().table().row(serviceParameters.name));
        theActorInTheSpotlight().attemptsTo(
                service.action(ZTA.services().webApp().ui().removeActionMenu()),
                Modal.waitUntilOpen(),
                Ensure.thatTheAnswerTo(Modal.title()).containsIgnoringCase("remove")
        );

    }
    @Then("Remove should not be available as an option")
    public void removeButtonNotAvailable(){
        List<WebElementFacade> buttons =  Modal.MODAL_FOOTER_BUTTONS.resolveAllFor(theActorInTheSpotlight());
        List<String> buttonText = buttons.stream().map(WebElementFacade::getText).collect(Collectors.toList());
        for (String text : buttonText){
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(
                            text.toLowerCase().contains("remove")
                    ).isFalse()
            );
        }
    }

    @When("User deletes webApp {reference}")
    public void deleteService(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage()
        );
        Row row = theActorInTheSpotlight().asksFor(
                ZTA.services().webApp().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                row.action(ZTA.services().webApp().ui().removeActionMenu()),
                Modal.waitUntilOpen(),
                Modal.save()
        );

    }


    @Then("webApp {reference} is deleted")
    public void webAppDeleted(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.service().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.services().webApp().openPage(),
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(
                                        ZTA.services().webApp().table().contains(parameters.name))
                                .forTime(Duration.ofMinutes(3).plusSeconds(5))
                                .untilEqualTo(false).isSuccess()
                ).isTrue()
        );
    }


}
