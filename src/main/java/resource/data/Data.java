package resource.data;

import com.google.gson.JsonObject;
import indexed.pojo.model.IndexedPojo;
import json.utils.JsonFile;
import net.datafaker.Faker;
import resource.data.parameters.file.DataJSON;
import resource.data.parameters.file.OneTimeResource;
import resource.data.parameters.file.services.remoteDesktopJSON;
import resource.data.parameters.file.services.secureShellJSON;
import resource.data.parameters.file.services.webAppJSON;
import resource.data.readers.*;
import resource.data.readers.connectors.AwsConnectorReader;
import resource.data.readers.connectors.AzureConnectorReader;
import resource.data.readers.connectors.GcpConnectorReader;
import resource.data.readers.connectors.PrivateConnectorReader;
import resource.data.readers.sites.AwsSiteReader;
import resource.data.readers.sites.AzureSiteReader;
import resource.data.readers.sites.GcpSiteReader;
import resource.data.readers.sites.OnPremSiteReader;
import resource.manager.ResourceException;
import ui.com.ztna.web.parameters.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static indexed.pojo.model.IndexedPojoDeserializer.deserializeJsonTo;

public class Data {
    private final static String DEFAULT_DATA_PATH = "src/test/resources/test_data/data.json";
    private static DataJSON Parameters = null;
    private static HashSet<String> Used = new HashSet<>();

    public static <E> E getAny(List<E> listOfResources){
        Random rand = new Random();
        return listOfResources.get(rand.nextInt(listOfResources.size()));
    }
    public static <T extends OneTimeResource> T freeResource(List<T> items, String Name) throws UsedResource {
        for(T item: items){
            if(!Used.contains(item.primary())){used(item);return item;}
        }
        throw new UsedResource(Name);
    }

    public static <T extends IndexedPojo> T lookup(String identifier) throws UnknownResource {
        return FindData.lookup(identifier);
    }

    public static <T extends OneTimeResource> void used(T resource){
        Used.add(resource.primary());
    }
    public static <T extends OneTimeResource> void free(T resource) {
        Used.remove(resource.primary());
    }
    public static void free(String resource) {
        Used.remove(resource);
    }

    public static DataJSON parameters(){
        if (Parameters==null){
            JsonObject jsonData = JsonFile.fromPath(DEFAULT_DATA_PATH).retrieve().getAsJsonObject();
            Parameters = deserializeJsonTo(jsonData, DataJSON.class);
        }
        return Parameters;
    }

    public static String getText(Integer length){
        return new Faker().text().text(length,length);
    }
    public static String generateName(){
        Faker fake = new Faker();
        return fake.text().text(3,10,true,false,false);
    }
    public static String generateEmail(){
        Faker fake = new Faker();
        String mail = "@automation.zta";
        return fake.name().firstName().toLowerCase() + mail;
    }

    public static String generateFirstName(){
        Faker fake = new Faker();
        return fake.name().firstName();
    }

    public static String generateDescription(){
        Faker fake = new Faker();
        return fake.text().text(20,25,true,false,false);
    }

    ////////////////////// Resource Reader Getters  ////////////////////////////////////////
    ///////////////////// *Implement this using Inner Classes* //////////////////////////
    public static AccessGroupReader accessGroup(){
        return new AccessGroupReader(Data.generateName());
    }

    //// Connectors
    public static PrivateConnectorReader privateConnector(){
        return new PrivateConnectorReader();
    }
    public static AwsConnectorReader awsConnector(){return new AwsConnectorReader();}
    public static AzureConnectorReader azureConnector(){return new AzureConnectorReader();}
    public static GcpConnectorReader gcpConnector(){return new GcpConnectorReader();}
    /////////////End Connectors

    //// Policies
    public static PolicyReader policyForProject(String Project){
        return new PolicyReader(Data.generateName(),true,Project);
    }
    public static PolicyReader policyForService(String Service){
        return new PolicyReader(Data.generateName(),false,Service);
    }
    /////////////////// End Policies


    ////// Application Group
    public static ApplicationGroupReader applicationGroup(){
        return new ApplicationGroupReader(Data.generateName()).withDescription();
    }

    ////// End Application Group

    public static RelayReader relayAnyRegion(){
        return new RelayReader(Data.getAny(Data.parameters().regions));
    }
    /////////// Rules
    public static RuleReader ruleForUser(String User){
        return new RuleReader(Data.generateName(),User,false);
    }
    public static RuleReader ruleForAnyUser() throws UsedResource, ResourceException {
        return RuleReader.forAnyUser();
    }

    public static RuleReader ruleForAnyGroup() throws UsedResource, ResourceException {
        return RuleReader.forAnyGroup();
    }
    public static RuleReader ruleForAccessGroup(String AG){
        return new RuleReader(Data.generateName(),AG,true);
    }
    /////// End Rule
    /////// Services
    public static ServiceReader<webAppJSON,webAppParameters> webApp(){
        return new ServiceReader<webAppJSON,webAppParameters>(ServiceReader.SERVICES.webApp,webAppParameters.class);
    }
    public static ServiceReader<secureShellJSON,secureShellParameters> secureShell(){
        return new ServiceReader<secureShellJSON,secureShellParameters>(ServiceReader.SERVICES.secureShell, secureShellParameters.class);
    }
    public static ServiceReader<remoteDesktopJSON,remoteDesktopParameters> remoteDesktop(){
        return new ServiceReader<remoteDesktopJSON,remoteDesktopParameters>(ServiceReader.SERVICES.remoteDesktop, remoteDesktopParameters.class);
    }
    ////// End Services
    public static TenantParameter tenant(){
        return new TenantParameter(Data.parameters().tenant.email,Data.parameters().tenant.password);
    }
    /// Users
    public static UserReader localUser(){
        return new UserReader(Data.parameters().user.local,"local");
    }
    public static UserReader google(){
        return new UserReader(Data.parameters().user.google,"google");
    }
    public static UserReader microsoft(){
        return new UserReader(Data.parameters().user.microsoft,"microsoft");
    }
    /// End Users


    //Sites
    public static OnPremSiteReader onPremSite(){
        return new OnPremSiteReader();
    }
    public static AwsSiteReader awsSite(){
        return new AwsSiteReader();
    }

    public static AzureSiteReader azureSite(){
        return new AzureSiteReader();
    }

    public static GcpSiteReader gcpSite(){
        return new GcpSiteReader();
    }
    /// End Sites

    public static SplunkReader splunk(){return  new SplunkReader();}
    /// Identity Provider
    public static IdentityProviderReader gsuiteIDP(){return new IdentityProviderReader();}
    public static IdentityProviderReader azureIDP(){return new IdentityProviderReader();}

    /// End Identity Provider
    /// Integrations
    public static AwsIntegrationReader awsIntegration() {
        return new AwsIntegrationReader();
    }
    public static AzureIntegrationReader azureIntegration(){
        return new AzureIntegrationReader();
    }
    public static GCPIntegrationReader gcpIntegration(){
        return new GCPIntegrationReader();
    }

    /// End Integrations



}
