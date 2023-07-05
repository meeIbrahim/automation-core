package ui.com.ztna.web.pages.policies;

public class PolicyDoesntExist extends Exception{
    public PolicyDoesntExist(String policyName){
        super("Policy doesnt exist - " + policyName);
    }
}
