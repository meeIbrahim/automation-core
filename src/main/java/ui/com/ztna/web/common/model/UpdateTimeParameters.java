package ui.com.ztna.web.common.model;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

public class UpdateTimeParameters extends IndexedPojo {

    public static final String ANY_TIME_KEY = "Any Time";
    @SerializedName(ANY_TIME_KEY)
    public String anyTime = "";

    public static final String START_TIME_AFTER_HOURS_KEY = "Start Time After Hours";
    @SerializedName(START_TIME_AFTER_HOURS_KEY)
    public String startTimeAfterHours = "";

    public static final String START_TIME_AFTER_MINS_KEY = "Start Time After Mins";
    @SerializedName(START_TIME_AFTER_MINS_KEY)
    public String startTimeAfterMins = "";

    public static final String END_TIME_AFTER_HOURS_KEY = "End Time After Hours";
    @SerializedName(END_TIME_AFTER_HOURS_KEY)
    public String endTimeAfterHours = "";

    public static final String END_TIME_AFTER_MINS_KEY = "End Time After Mins";
    @SerializedName(END_TIME_AFTER_MINS_KEY)
    public String endTimeAfterMins = "";



}
