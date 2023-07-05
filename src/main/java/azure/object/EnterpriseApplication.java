package azure.object;

import com.microsoft.graph.models.ServicePrincipal;

/**
 * This is a wrapper class around Azure's Enterprise Application
 * It provides functionality that is required by the Parent Project
 */
public class EnterpriseApplication implements Deletion {
    private final RegisteredApplication application;
    ServicePrincipal servicePrincipal;

    /**
     * Create Enterprise Application Object using existing Azure enterprise application
     * @param application Parent Registered Application
     * @param servicePrincipal Existing Azure enterprise app
     */
    EnterpriseApplication(RegisteredApplication application,ServicePrincipal servicePrincipal) {
        this.application = application;
        this.servicePrincipal = servicePrincipal;
        EnterpriseApplications.ENTERPRISE_APPLICATION_LIST.add(this);
    }

    /**
     * Create Enterprise Application  using a new Azure microsoft enterprise app
     * @param application Parent registered application
     */
    EnterpriseApplication(RegisteredApplication application){
        this.application = application;
        ServicePrincipal sp = new ServicePrincipal();
        sp.appId = application.id();
        sp = application.client().servicePrincipals().buildRequest().post(sp);
        this.servicePrincipal = sp;
        EnterpriseApplications.ENTERPRISE_APPLICATION_LIST.add(this);
    }

    public String name() {
        return servicePrincipal.displayName;
    }

    public void delete() {
        assert servicePrincipal.id != null;
        application.client().servicePrincipals(servicePrincipal.id).buildRequest().delete();
    }

    public RegisteredApplication getRegisteredApp() {
        return application;
    }
}
