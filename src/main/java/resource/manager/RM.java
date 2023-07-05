package resource.manager;

import resource.manager.collections.ServiceCollection;
import resource.manager.provider.*;
import resource.manager.provider.common.DependentEntity;
import resource.manager.provider.common.resourceEntity;
import ui.com.ztna.web.pages.ZTA.MultiCloudIntegrations;

public class RM {

    /**
     *    Initialize in order of dependency
     */
    private static final User user = new User();
    private static final Relay relay = new Relay();
    private static final AccessGroup accessGroup = new AccessGroup();
    private static final ApplicationGroup project = new ApplicationGroup();
    private static final Site site = new Site();
    private static final Connector connector = new Connector();

    private static final ServiceCollection service = new ServiceCollection();

    private static final Policy policy = new Policy();
    private static final Rule rule = new Rule();
    private static final AwsIntegration awsIntegrations = new AwsIntegration();
    private static final AzureIntegration azureIntegrations = new AzureIntegration();
    private static final GcpIntegration gcpIntegrations = new GcpIntegration();

    public static resourceEntity resource(String Resource){
        switch(Resource.toLowerCase()){
            case "user":
                return user();
            case "accessgroup":
            case "access-group":
                return accessGroup();
            case "relay":
            case "cloudrelay":
            case "cloud-relay":
                return relay();
            case "connector":
            case "serviceconnector":
            case "service-connector":
                return connector();
            case "service":
            case "webapp":
                return service().webApp();
            case "ssh":
                return service().secureShell();
            case "rdp":
                return service().remoteDesktop();
            case "project":
                return project();
            case "policy":
                return policy();
            case "rule":
                return rule();
            case "site":
                return site();
            case "awsintegration":
            case "aws-integration":
                return awsIntegrations();
            case "azureintegration":
            case "azure-integration":
                return azureIntegrations();
            case "gcpintegration":
            case "gcp-integration":
                return gcpIntegrations();
            default:
                throw new IllegalArgumentException();
        }
    }
    public static DependentEntity<?> dependentResource(String Resource){
        switch(Resource.toLowerCase()){
            case "connector":
                return connector();
            case "service":
            case "webapp":
                return service().webApp();
            case "ssh":
                return service().secureShell();
            case "rdp":
                return service().remoteDesktop();
            case "policy":
                return policy();
            case "rule":
                return rule();
            case "site":
                return site();
            default:
                throw new IllegalArgumentException();
        }
    }



    public static User user(){
        return user;
    }
    public static AccessGroup accessGroup(){
        return accessGroup;
    }

    public static Relay relay(){
        return relay;
    }
    public static Site site(){
        return site;
    }
    public static Connector connector(){
        return connector;
    }
    public static ServiceCollection service(){
        return service;
    }
    public static ApplicationGroup project(){
        return project;
    }
    public static Policy policy(){
        return policy;
    }
    public static Rule rule(){
        return rule;
    }

    public static AwsIntegration awsIntegrations() {return awsIntegrations;}
    public static AzureIntegration azureIntegrations() {return azureIntegrations;}
    public static GcpIntegration gcpIntegrations() {return gcpIntegrations;}
}



