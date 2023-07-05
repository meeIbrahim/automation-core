package ui.com.ztna.web.common.variables;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CommonVariables {

    public static String signUpInvitationCode;
    public static String signUpPin;
    public static String awsAccessKeyId;
    public static String awsSecret;
    public static String sessionToken;

    public static String relayName;


    public static Performable storeValue(String var, Target target) throws NoSuchFieldException {
        return Task.where("Store Value", actor -> {
            String temp=target.resolveFor(theActorInTheSpotlight()).getText();
            System.out.println(target+" : "+temp);
            try {
                CommonVariables.class.getField(var).set(CommonVariables.class.getField(var),temp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Value of "+target+" is stored in global variable : "+var);
        });
    }
}
