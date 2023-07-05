package ssh.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class JSCHConnector implements ISSHConnector {

    JSch shell;
    Session session;
    String keyPath;
    String hostIP;
    Integer portNumber;
    Integer timeOut = 60000;
    String user;

    public JSCHConnector(JSch shell) {
        this.shell = shell;
    }

    @Override
    public JSCHConnector connect() {

        try
        {
            shell.addIdentity(keyPath);
            session = shell.getSession(user,
                    hostIP, portNumber);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        }
        catch(JSchException jschX)
        {
            jschX.printStackTrace();
        }

        return this;
    }

    @Override
    public void disconnect() {
        session.disconnect();
    }

    public static JSCHConnector onChannel(JSch jschSSHChannel) {
        return new JSCHConnector(jschSSHChannel);
    }

    public static JSCHConnector usingNewChannel() {
        return new JSCHConnector(new JSch());
    }

    public JSCHConnector usingKeyOnPath(String keyPath) {
        this.keyPath = keyPath;
        return this;
    }

    public JSCHConnector host(String hostIP) {
        this.hostIP = hostIP;
        return this;
    }

    public JSCHConnector port(Integer portNumber) {
        this.portNumber = portNumber;
        return this;
    }

    public JSCHConnector forUser(String user) {
        this.user = user;
        return this;
    }

    public JSCHConnector setTimeout(Integer timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public JSch getShell() {
        return shell;
    }
}
