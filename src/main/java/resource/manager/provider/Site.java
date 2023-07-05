package resource.manager.provider;

import resource.cached.ZTAParameters;
import resource.cached.cachedNotFound;
import resource.manager.RM;
import resource.manager.ResourceException;
import resource.manager.provider.common.DependentEntity;
import resource.data.Data;
import resource.data.UsedResource;
import ui.com.ztna.web.common.Resource;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.cloud.relays.CloudHostedRelays;
import ui.com.ztna.web.pages.cloud.relays.RelayUI;
import ui.com.ztna.web.pages.sites.SiteUi;
import ui.com.ztna.web.parameters.SiteParameters;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class Site extends DependentEntity<SiteParameters> {

    public Site() {
        super(new ui.com.ztna.web.pages.sites.Site(),RM.relay());
    }

    @Override
    protected SiteParameters getParameters(String parent) throws UsedResource {
        return Data.onPremSite().withRelay(parent).getParameters();
    }
    public ZTAParameters getActive(String Reference, String host) throws UsedResource, ResourceException {

        switch(host.toLowerCase()){
            case "onprem":
            case "on-prem":
               host="On-Prem";
                break;
            case "aws":
                host="AWS";
                break;
            case "azure":
                host="Azure";
                break;
            case "gcp":
                host="GCP";
                break;
            default:
                host="On-Prem";
        }

        try {
            cache().retrieve(Reference);
            throw new IllegalArgumentException("Reference Already Used!");
        }  catch (cachedNotFound ignored){ }
        ZTAParameters resource;
        try {
            resource = theActorInTheSpotlight().asksFor(ZTA.sites().getActive(cache().allCached(),host));
        } catch (Exception e){
            throw  new ResourceException(getClass().getName() + "-" + Reference);
        }
        if (resource == null){
            return create(Reference);
        }
        cache().cache(Reference, resource);
        return resource;
    }

}
