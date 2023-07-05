package resource.data.parameters.file;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;
import resource.data.parameters.file.services.remoteDesktopJSON;
import resource.data.parameters.file.services.secureShellJSON;
import resource.data.parameters.file.services.webAppJSON;

import java.util.ArrayList;
import java.util.List;

public class ServicesJSON extends IndexedPojo {
    public static final String WEB_APP_KEY = "webApp";
    @SerializedName(WEB_APP_KEY)
    public List<webAppJSON> webApp = new ArrayList<>();

    public static final String REMOTE_DESKTOP_KEY = "remoteDesktop";
    @SerializedName(REMOTE_DESKTOP_KEY)
    public List<remoteDesktopJSON> remoteDesktop = new ArrayList<>();

    public static final String SECURE_SHELL_KEY = "secureShell";
    @SerializedName(SECURE_SHELL_KEY)
    public List<secureShellJSON> secureShell = new ArrayList<>();}
