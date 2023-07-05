package api.zta.endpoints;

import restassured.Endpoint;

/// Endpoints for AUTH
public class Auth {
    public static final Endpoint sign_in = Endpoint.url("{auth}/accounts/login/");
    public static final Endpoint sign_up = Endpoint.url("{auth}/accounts/invite/signup/");
    public static final Endpoint users = Endpoint.url("{auth}/accounts/customer-admin-users/");

    public static final Endpoint users_polling = Endpoint.url("{auth}/accounts/customer-admin-users/");

    public static final Endpoint user_delete = Endpoint.url("{auth}/accounts/users/{0}/hard-deletion/");

    public static final Endpoint aws_cloud_integrations = Endpoint.url("{auth}/cloud-catalogue/aws-cloud/");

    public static final Endpoint aws_cloud_integration_delete = Endpoint.url("{auth}/accounts/users/{0}/hard-deletion/");

    public static final Endpoint azure_cloud_integrations = Endpoint.url("{auth}/cloud-catalogue/azure-cloud/");

    public static final Endpoint azure_cloud_integration_delete = Endpoint.url("{auth}/accounts/users/{0}/hard-deletion/");

    public static final Endpoint gcp_cloud_integrations = Endpoint.url("{auth}/cloud-catalogue/gcp-cloud/");

    public static final Endpoint gcp_cloud_integration_delete = Endpoint.url("{auth}/accounts/users/{0}/hard-deletion/");

}
