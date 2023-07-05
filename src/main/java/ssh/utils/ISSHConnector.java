package ssh.utils;

public interface ISSHConnector {
    JSCHConnector connect();
    void disconnect();
}
