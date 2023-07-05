package ssh.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JSCHReadFile {

    Session session;
    ChannelSftp channelSFTP;

    public JSCHReadFile(Session session) {
        this.session = session;
    }

    public String from(String path) {

        String fileContent = "";
        try {

            channelSFTP = (ChannelSftp) session.openChannel("sftp");
            channelSFTP.connect();
            InputStream fileContentStream = channelSFTP.get(path);
            fileContent = new BufferedReader(new InputStreamReader(fileContentStream))
                    .lines().parallel().collect(Collectors.joining("\n"));
            channelSFTP.disconnect();

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }

        return fileContent;
    }
    public static JSCHReadFile forSession(Session session) {
        return new JSCHReadFile(session);
    }

}
