package api.zta.endpoints;

import restassured.Endpoint;

/// Endpoints for RESOURCE
public class Resource {
    public static final Endpoint policies = Endpoint.url("{resource}/policies/");
    public static final Endpoint policy_soft_delete = Endpoint.url("{resource}/policies/{0}/manage-deletion/");
    public static final Endpoint projects = Endpoint.url("{resource}/resources/projects/");
    public static final Endpoint projects_delete = Endpoint.url("{resource}/resources/projects/{0}/hard-deletion/");
    public static final Endpoint services = Endpoint.url("{resource}/resources/services/");
    public static final Endpoint services_soft_delete = Endpoint.url("{resource}/resources/services/{0}/");
    public static final Endpoint services_hard_delete = Endpoint.url("{resource}/resources/services/hard-delete/{0}");
    public static final Endpoint connectors = Endpoint.url("{resource}/resources/hosts/");
    public static final Endpoint connector_soft_delete = Endpoint.url("{resource}/resources/hosts/{0}/");
    public static final Endpoint connector_hard_delete = Endpoint.url("{resource}/resources/hosts/hard-delete/{0}");
    public static final Endpoint cloud_relays = Endpoint.url("{resource}/resources/relay-nodes/");
    public static final Endpoint cloud_relay_soft_delete = Endpoint.url("{resource}/resources/relay-nodes/{0}/");
    public static final Endpoint sites = Endpoint.url("{resource}/resources/sites/");
    public static final Endpoint sites_hard_delete = Endpoint.url("{resource}/resources/sites/{0}/hard-deletion/");
    public static final Endpoint access_groups = Endpoint.url("{resource}/access-group/");
    public static final Endpoint access_group_delete = Endpoint.url("{resource}/access-group/{0}/delete/");
    public static final Endpoint device_postures = Endpoint.url("{resource}/resources/device-posture-check/");
    public static final Endpoint device_posture_delete = Endpoint.url("{resource}/resources/device-posture-check/{0}/soft-delete/");
}
