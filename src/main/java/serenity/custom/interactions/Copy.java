package serenity.custom.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;

import java.awt.*;
import java.awt.event.KeyEvent;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Copy implements Interaction {

    public static Copy selectedData(){
        return instrumented(Copy.class);
    }

    @Override
    @Step("{0} copies the selected data")
    public <T extends Actor> void performAs(T actor) {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_C);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
