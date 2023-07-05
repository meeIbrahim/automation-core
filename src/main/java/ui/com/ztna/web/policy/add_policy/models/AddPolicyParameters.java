package ui.com.ztna.web.policy.add_policy.models;

import com.google.gson.annotations.SerializedName;
import indexed.pojo.model.IndexedPojo;

import java.util.ArrayList;
import java.util.List;

public class AddPolicyParameters extends IndexedPojo {

    public static final String POLICIES_KEY = "Policies";
    @SerializedName(POLICIES_KEY)
    public List<PolicyParameters> policies = new ArrayList<PolicyParameters>();
}
