package ssh.utils;

public interface ISSHCommunicate {
    void sendCommand(String command);
    String readCommand();
}
