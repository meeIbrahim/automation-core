package com.ztna.parameters;

import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.io.File;
import java.util.List;

import static string.utils.StringUtils.toList;

public class Parameters {

    @ParameterType("(?i)(true|false)")
    public static Boolean aBoolean(String bool){
        return bool.equalsIgnoreCase("true");
    }
    @ParameterType(".*")
    public static Actor actor(String actor) {
        return OnStage.theActorCalled(actor);
    }

    @ParameterType(".*")
    public static List<String> list(String stringifiedList) {
        return toList(stringifiedList);
    }

    @ParameterType("\\w")
    public static String reference(String reference){
        return reference;
    }

    @ParameterType(".*")
    public static File file(String filePath) {
        return new File(filePath);
    }
}
