package com.ztna.steps.web.accessgroup;

import io.cucumber.java.en.Given;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Create_Access_Group_Steps {

    public static String AVAILABLE_ACCESS_GROUPS = "available_access_groups";

    @Given("User remembers available access groups")
    public void userRemembersAccessGroup(){
        theActorInTheSpotlight().attemptsTo(
                ZTA.accessGroups().openPage()
        );
        LinkedHashSet<Row> allGroups = theActorInTheSpotlight().asksFor(ZTA.accessGroups().table().rows());
        List<String> accessGroups;
        if (!allGroups.isEmpty()) {
            accessGroups = allGroups.stream().map(Row::name).collect(Collectors.toList());
        }
        else {
            accessGroups = new ArrayList<>();
        }
        theActorInTheSpotlight().remember(AVAILABLE_ACCESS_GROUPS,accessGroups);
    }

}
