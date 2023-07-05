package api.zta.endpoints;

import restassured.Endpoint;

public class XIQ {
    public static final Endpoint catalog_apps = Endpoint.url("{custom}/xcd-apps/catalog-apps");
    public static final Endpoint xiq_login = Endpoint.url("{auth}/accounts/login/");
}
