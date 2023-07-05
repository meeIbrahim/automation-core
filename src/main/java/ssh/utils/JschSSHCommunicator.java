package ssh.utils;

import com.jcraft.jsch.*;

import java.io.*;

public class JschSSHCommunicator implements ISSHCommunicate{

    Session session;
    ChannelExec channel;
    InputStream inputStream;
    OutputStream outputStream;

    public JschSSHCommunicator(Session session) {
        this.session = session;
    }

    @Override
    public void sendCommand(String command) {
       /* PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(command);
        printWriter.flush();*/
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readCommand() {
        return null;
    }

    public static JschSSHCommunicator forSession(Session session) {
        return new JschSSHCommunicator(session);
    }

    public JschSSHCommunicator connectChannel() {

      /*  try {
*//*
            channel = (ChannelShell) session.openChannel("exec");
            inputStream = channel.getInputStream();
            outputStream = channel.getOutputStream();
            channel.setPty(true);
            channel.connect();*//*
           // channelShell.setInputStream(System.in);
           // channelShell.setOutputStream(System.out);
            //outputStream = new PrintStream(channelShell.getOutputStream());

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }*/

        return this;
    }

    public JschSSHCommunicator disconnectChannel() {
        channel.disconnect();
        return this;
    }

}
