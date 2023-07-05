package ui.com.ztna.web.parameters;

import resource.cached.ZTAParameters;

public class PolicyParameters extends ZTAParameters {
    public String description;
    public String attachment;
    public Boolean forProject;

    public PolicyParameters(String Name, String Description, String AttachTo,Boolean forProject){
        super(Name,"",AttachTo);
        this.description = Description;
        this.attachment = AttachTo;
        this.forProject = forProject;
        this.parent = this.attachment;
    }
}
