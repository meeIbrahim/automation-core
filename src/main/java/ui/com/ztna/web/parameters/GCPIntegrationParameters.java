package ui.com.ztna.web.parameters;


public class GCPIntegrationParameters extends CloudIntegrationParameters {

    public String ProjectId="";
    public String JsonFilePath="";

    public GCPIntegrationParameters(String Name, String ProjectId, String JsonFilePath){
        super(Name,ProjectId);
        this.ProjectId=ProjectId;
        this.JsonFilePath=JsonFilePath;
    }
}
