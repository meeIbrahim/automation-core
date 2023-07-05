package ssh.utils;

import com.jcraft.jsch.Session;

public class MakeChanges {

    Session session;
    String path;
    String regex;
    String replacement;

    public MakeChanges(Session session) {
        this.session = session;
    }

    public MakeChanges inFile(String path) {
        this.path = path;
        return this;
    }

    public MakeChanges findString(String regex) {
        this.regex = regex;
        return this;
    }

    public MakeChanges replaceWith(String replacement) {
        this.replacement = replacement;
        return this;
    }

    public void update() {
        String fileContent = JSCHReadFile.forSession(session).from(path);
        fileContent = fileContent.replaceAll(regex,replacement);
        JSCHWriteFile.forSession(session).stringContent(fileContent).to(path);
    }
    public static MakeChanges usingSession(Session session) {
        return new MakeChanges(session);
    }
}
