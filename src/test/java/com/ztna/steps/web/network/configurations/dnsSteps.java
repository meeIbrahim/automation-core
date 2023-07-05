package com.ztna.steps.web.network.configurations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import org.apache.hc.core5.net.InetAddressUtils;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;

import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.network.configurations.DnsCard;


import java.util.Objects;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class dnsSteps {

    @Given("User is on Network Configurations page")
    public void userIsOnNetworkConfigurationsPage() {
        theActorInTheSpotlight().attemptsTo(
                ZTA.networkConfigurations().openPage(),
                Ensure.thatTheAnswerTo(Visibility.of(DnsCard.DNS).asABoolean()).isTrue()
        );
    }



    @And("^User enters (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}) as (secondary|primary) DNS$")
    public void userEntersAsSecondaryDNS(String IP,String placement) {
        if (!InetAddressUtils.isIPv4Address(IP)){
            throw new IllegalArgumentException("IP can only be IP4");
        }

        theActorInTheSpotlight().attemptsTo(
                ZTA.networkConfigurations().DnsCard().expand(),
                Check.whether(Objects.equals(placement, "primary"))
                        .andIfSo(Input.of(DnsCard.PRIMARY_DNS).with(IP))
                        .otherwise(Input.of(DnsCard.SECONDARY_DNS).with(IP))
        );
        theActorInTheSpotlight().remember(placement + "_dns",IP);
    }

    @When("User updates DNS configuration")
    public void userUpdatesDNSConfiguration() {
        theActorInTheSpotlight().attemptsTo(
                Wait.forVisibilityOf(DnsCard.UPDATE_BUTTON),
                Click.on(DnsCard.UPDATE_BUTTON),
                Modal.waitUntilOpen(),
                Modal.save()
        );
    }

    @Then("User should see DNS is updated")
    public void userShouldSeeDNSIsUpdated() {
        String primary = theActorInTheSpotlight().recall("primary_dns");
        String secondary = theActorInTheSpotlight().recall("secondary_dns");
        theActorInTheSpotlight().attemptsTo(
                ZTA.networkConfigurations().openPage(),
                ZTA.networkConfigurations().DnsCard().expand(),
                Ensure.thatTheAnswerTo(Input.of(DnsCard.PRIMARY_DNS).value()).isEqualTo(primary),
                Ensure.thatTheAnswerTo(Input.of(DnsCard.SECONDARY_DNS).value()).isEqualTo(secondary)
        );
    }

    @And("User removes DNS enforcement")
    public void removesDNSEnforcement(){
        theActorInTheSpotlight().attemptsTo(
                ZTA.networkConfigurations().openPage(),
                ZTA.networkConfigurations().removeDNS()
        );
    }

    @And("DNS enforcement is not set")
    public void DNSEnforcementNotSet(){
        removesDNSEnforcement();
    }
}
