package ui.com.ztna.web.policy.delete_policy.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class DeletePolicyParameters extends IndexedPojo {

    public static final String POLICES_KEY = "Policies";
    @SerializedName(POLICES_KEY)
    public List<String> policies = new ArrayList<>();
}
