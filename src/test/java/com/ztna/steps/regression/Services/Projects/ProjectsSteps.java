package com.ztna.steps.regression.Services.Projects;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.ensure.Ensure;
import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.data.Data;
import resource.manager.RM;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.applicationGroups.modals.UpdateApplicationGroup;
import ui.com.ztna.web.parameters.ApplicationGroupParameters;

import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;


public class ProjectsSteps {

    @When("User creates empty Project {reference}")
    public void createProject(String reference){
        ApplicationGroupParameters parameters = Data.applicationGroup().withDescription().get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.projects().create(parameters)
        );
        RM.project().cache().cache(reference,parameters);
    }
    @When("User creates Project {reference} with service {reference}")
    public void createProject(String reference, String serviceRef) throws cachedNotFound {
        ZTAParameters serviceParameters = RM.service().cache().retrieve(serviceRef);
        ApplicationGroupParameters parameters = Data.applicationGroup().withDescription().withServices(serviceParameters.name).get();
        theActorInTheSpotlight().attemptsTo(
                ZTA.projects().create(parameters)
        );
        RM.project().cache().cache(reference,parameters);
    }

    @Then("Project {reference} should be added")
    public void projectIsAdded(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.project().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.projects().openPage(),
                Ensure.thatTheAnswerTo(ZTA.projects().table().contains(parameters.name)).isTrue()
        );

    }

    @Then("user removes Project {reference}")
    public void removesProject(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.project().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.projects().purge(parameters));
    }


    @Then("Project {reference} should be removed")
    public void projectIsRemoved(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.project().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(ZTA.projects().openPage());
        Row project = theActorInTheSpotlight().asksFor(ZTA.projects().table().row(parameters.name));
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(
                        Wait.forQuestion(project.exists()).untilEqualTo(false)
                                .forTime(Duration.ofMinutes(3))
                                .isSuccess()).isTrue()
        );
    }

    @When("User updates Project {reference} name and description")
    public void UpateProjectName(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.project().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                ZTA.projects().openPage()
        );
        Row project = theActorInTheSpotlight().asksFor(ZTA.projects().table().row(parameters.name));
        String newName = Data.generateName();
        String newDescription =Data.generateDescription();
        theActorInTheSpotlight().attemptsTo(
                UpdateApplicationGroup.open(project),
                UpdateApplicationGroup.update(newName,newDescription),
                Modal.save()
        );
        parameters.name = newName;
        RM.connector().cache().update(reference,parameters);

    }
    @When("User adds Application {reference} to Application Group {reference}")
    public void UpateProject(String reference,String Project) throws cachedNotFound {
        ZTAParameters applicationParams = RM.service().cache().retrieve(reference);
        ZTAParameters projectParams = RM.project().cache().retrieve(Project);
        theActorInTheSpotlight().attemptsTo(ZTA.projects().openPage());
        Row project = theActorInTheSpotlight().asksFor(ZTA.projects().table().row(projectParams.name));
        theActorInTheSpotlight().attemptsTo(
                UpdateApplicationGroup.open(project),
                UpdateApplicationGroup.updateApplication(applicationParams.name),
                Modal.save()
        );
    }
    @When("User removes Application {reference} from Application Group {reference}")
    public void UpateAppInProject(String reference,String Project) throws cachedNotFound {
        ZTAParameters applicationParams = RM.service().cache().retrieve(reference);
        ZTAParameters projectParams = RM.project().cache().retrieve(Project);
        theActorInTheSpotlight().attemptsTo(ZTA.projects().openPage());
        Row project = theActorInTheSpotlight().asksFor(ZTA.projects().table().row(projectParams.name));
        theActorInTheSpotlight().attemptsTo(
                UpdateApplicationGroup.open(project),
                UpdateApplicationGroup.updateAndRemoveApplication(applicationParams.name),
                Modal.save()
        );
    }


    @Then("Project {reference} should be updated")
    public void projectUpdate(String reference) throws cachedNotFound {
        ZTAParameters parameters = RM.project().cache().retrieve(reference);
        theActorInTheSpotlight().attemptsTo(
                Ensure.thatTheAnswerTo(ZTA.projects().table().contains(parameters.name))
                        .isTrue()
        );
    }


}
