package ui.com.ztna.web.pages.ZTA;

import ui.com.ztna.web.pages.access.groups.AccessGroups;

import ui.com.ztna.web.pages.applicationGroups.*;
import ui.com.ztna.web.pages.cloud.relays.CloudHostedRelays;
import ui.com.ztna.web.pages.connectors.ServiceConnectors;
import ui.com.ztna.web.pages.integrations.event.collector.EventCollector;
import ui.com.ztna.web.pages.network.configurations.NetworkConfigurations;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.pages.rules.Rules;
import ui.com.ztna.web.pages.sites.Site;
import ui.com.ztna.web.pages.users.Users;

public class ZTA {
    private static final Users users = new Users() ;
    private static final AccessGroups accessGroups = new AccessGroups();
    private static final CloudHostedRelays relays = new CloudHostedRelays();
    private static final ServiceConnectors connectors = new ServiceConnectors();
    private static final Services services = new Services();
    private static final ApplicationGroups projects = new ApplicationGroups();
    private static final Policies policies = new Policies();
    private static final Site sites = new Site();
//    private static final Rules rules = new Rules();
    private static final MultiCloudIntegrations cloudIntegrations = new MultiCloudIntegrations();
    private static final NetworkConfigurations networkConfig = new NetworkConfigurations();
    private static final EventCollector eventCollector = new EventCollector();
    private static final IDP identityProviders = new IDP();

    public static Users users(){
        return users;
    }
    public static AccessGroups accessGroups(){
        return accessGroups;
    }
    public static CloudHostedRelays relays(){
        return relays;
    }
    public static ServiceConnectors serviceConnectors(){
        return connectors;
    }
    public static Services services(){
        return services;
    }
    public static ApplicationGroups projects(){
        return projects;
    }
    public static Policies policies(){
        return policies;
    }
    public static Site sites(){return sites;}
//    public static Rules rules(){return rules;}
    public static NetworkConfigurations networkConfigurations(){return networkConfig;}
    public static EventCollector eventCollector(){return eventCollector;}
    public static IDP identityProviders(){return identityProviders;}
    public static MultiCloudIntegrations cloudIntegrations(){return cloudIntegrations;}

}
