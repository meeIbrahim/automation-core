package ssh.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JSCHWriteFile {
    Session session;
    ChannelSftp channelSFTP;
    String content;
    String path;

    public JSCHWriteFile(Session session) {
        this.session = session;
    }

    public JSCHWriteFile to(String path) {
        this.path = path;
        return this;
    }

    public JSCHWriteFile stringContent(String content) {
        this.content = content;
        return this;
    }

    public void upload() {
        try {

            channelSFTP = (ChannelSftp) session.openChannel("sftp");
            channelSFTP.connect();
            InputStream contentStream = new ByteArrayInputStream(content.getBytes());
            channelSFTP.put(contentStream, path, ChannelSftp.OVERWRITE);
            channelSFTP.disconnect();

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
    public static JSCHWriteFile forSession(Session session) {
        return new JSCHWriteFile(session);
    }

}
