package resource.data.readers;

import resource.data.Data;
import ui.com.ztna.web.parameters.PolicyParameters;

public class PolicyReader {
    String Name;
    String Description;
    String Attachment;
    Boolean forProject;

    public PolicyReader(String Name,Boolean forProject,String attachment){
        this.Name = Name;
        this.forProject = forProject;
        this.Attachment = attachment;
        this.Description = "";
    }
    public static PolicyReader forProject(String Project){
        return new PolicyReader(Data.generateName(),true,Project);
    }
    public static PolicyReader forService(String Service){
        return new PolicyReader(Data.generateName(),false,Service);
    }
    public PolicyReader withDescription(){
        this.Description = Data.generateDescription();
        return this;
    }
    public PolicyParameters get(){
        return new PolicyParameters(Name,Description,Attachment,forProject);
    }
}
