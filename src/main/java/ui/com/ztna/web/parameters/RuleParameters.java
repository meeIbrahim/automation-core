package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

import java.util.ArrayList;
import java.util.List;

public class RuleParameters extends ZTAParameters {
    public String attachment;
    public Boolean forAccessGroup;
    public String policy;

    public Boolean timeAccess = false;
    public String STH;
    public String STM;
    public String ETH;
    public String ETM;

    public Boolean locationAccess = false;
    public List<String> countries;

    public RuleParameters(String Name,String Policy, String AttachTo,Boolean forAccessGroup){
        super(Name,"",AttachTo);
        this.forAccessGroup = forAccessGroup;
        this.policy = Policy;
        this.attachment = AttachTo;
        this.parent = policy;
    }

    public RuleParameters(String Name,String Policy, String AttachTo,Boolean forAccessGroup, Boolean timeAccess, String STH, String STM, String ETH, String ETM, Boolean locationAccess, List<String> countries){
        super(Name,"",AttachTo);
        this.forAccessGroup = forAccessGroup;
        this.policy = Policy;
        this.attachment = AttachTo;
        this.parent = policy;
        this.timeAccess = timeAccess;
        this.STH = STH;
        this.STM = STM;
        this.ETH = ETH;
        this.ETM = ETM;
        this.locationAccess = locationAccess;
        this.countries = countries;
    }
}
