package ui.com.ztna.web.common.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class UpdateDevicePostureParameters extends IndexedPojo {

    public static final String DEVICE_POSTURE_KEY = "Device Posture";
    @SerializedName(DEVICE_POSTURE_KEY)
    public String devicePosture = "";


}
