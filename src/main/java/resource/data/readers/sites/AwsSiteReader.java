package resource.data.readers.sites;

import resource.data.Data;
import ui.com.ztna.web.parameters.AwsSiteParameters;

import java.util.List;

public class AwsSiteReader extends SiteReader<AwsSiteParameters> {
    List<String> vpc;
    String awsAccountID;
    String region;
    public AwsSiteReader(){
        super(Data.generateName());
    }


    public AwsSiteReader accountID(String awsAccountID){
        this.awsAccountID = awsAccountID;
        return this;
    }
    public AwsSiteReader region(String region){
        this.region = region;
        return this;
    }

    public AwsSiteReader vpc(String vpc){
        this.vpc = List.of(vpc);
        return this;
    }
    public AwsSiteReader vpc(List<String> vpc){
        this.vpc = vpc;
        return this;
    }

    public SiteReader withRelays(String... relays){this.relay = List.of(relays);return this;}
    @Override
    public AwsSiteParameters getParameters() {
        return new AwsSiteParameters(name,relay,awsAccountID,region,vpc);
    }
    @Override
    public SiteReader<AwsSiteParameters> withName(String name) {
        return super.withName(name);
    }

    @Override
    public AwsSiteReader withRelay(String relay) {
        super.withRelay(relay);
        this.relay=List.of(relay);
        return this;
    }
    public AwsSiteParameters withAwsAccountId(String Id){
        return new AwsSiteParameters(Data.generateName(),this.relay,Id, this.region,this.vpc);
    }
    @Override
    public SiteReader<AwsSiteParameters> withRelays(List<String> relay) {
        return super.withRelays(relay);
    }
}
