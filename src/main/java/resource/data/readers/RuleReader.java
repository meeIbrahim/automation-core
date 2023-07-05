package resource.data.readers;

import resource.manager.RM;
import resource.manager.ResourceException;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.parameters.RuleParameters;

import java.util.List;

public class RuleReader {
    String Name;
    String Attachment;
    Boolean forAccessGroup;

    public Boolean timeAccess = false;
    public String STH;
    public String STM;
    public String ETH;
    public String ETM;

    public Boolean locationAccess = false;
    public List<String> countries;

    // To add Time based and Location

    public RuleReader(String Name, String Attachment, Boolean forAG){
        this.Name = Name;
        this.Attachment = Attachment;
        this.forAccessGroup = forAG;
    }

    public RuleReader(String Name,String Attachment,Boolean forAG, Boolean timeAccess, String STH, String STM, String ETH, String ETM, Boolean locationAccess, List<String>countries){
        this.Name = Name;
        this.forAccessGroup = forAG;
        this.Attachment = Attachment;
        this.timeAccess = timeAccess;
        this.STH = STH;
        this.STM = STM;
        this.ETH = ETH;
        this.ETM = ETM;
        this.locationAccess = locationAccess;
        this.countries = countries;
    }


    public static RuleReader forUser(String User){
        return new RuleReader(Data.generateName(),User,false);
    }
    public static RuleReader forAccessGroup(String AG){
        return new RuleReader(Data.generateName(),AG,true);
    }
    public static RuleReader forAnyUser() throws UsedResource, ResourceException {
        return new RuleReader(Data.generateName(),"",false);
    }
    public static RuleReader forAnyGroup(){
        return new RuleReader(Data.generateName(),"",true);
    }

    public RuleReader timeBased(String sth, String stm, String eth, String etm ){
        return new RuleReader(Data.generateName(),this.Attachment,this.forAccessGroup,true,sth,stm,eth,etm,this.locationAccess,this.countries);
    }

    public RuleReader locationBased(List<String> countries ){
        return new RuleReader(Data.generateName(),this.Attachment,this.forAccessGroup,this.timeAccess,STH,STM,ETH,ETM,true,countries);
    }

    public RuleParameters withPolicy(String Policy){
        return new RuleParameters(Name,Policy,Attachment,forAccessGroup,timeAccess,STH,STM,ETH,ETM,locationAccess,countries);
    }
    public RuleParameters withAnyPolicy(){
        return new RuleParameters(Name,"",Attachment,forAccessGroup,timeAccess,STH,STM,ETH,ETM,locationAccess,countries);
    }
}
