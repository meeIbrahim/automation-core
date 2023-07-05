package ssh.serenity_wrappers.tasks;

import com.jcraft.jsch.Session;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;
import ssh.utils.JschSSHCommunicator;

public class Send implements Task {

    private final String command;
    private Session session;

    public Send(String command) {
        this.command = command;
    }

    @Step("{0} sends command: #command using session: #session")
    @Override
    public <T extends Actor> void performAs(T t) {
        JschSSHCommunicator communicator = JschSSHCommunicator.forSession(session).connectChannel();
        communicator.sendCommand(command);
    }

    public static Send command(String command) {
        return Instrumented.instanceOf(Send.class).withProperties(command);
    }

    public Send usingSession(Session session) {
        this.session = session;
        return this;
    }
}
