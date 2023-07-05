package ui.com.ztna.web.pages.network.configurations;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.apache.hc.core5.net.InetAddressUtils;

import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.Page;
import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.input.Input;



public class NetworkConfigurations implements Page {

    DnsCard dns = new DnsCard();

    @Override
    public String url() {
        return "/app/network-configurations";
    }

    public DnsCard DnsCard(){return dns;}

   public Performable setDNS(String Primary, String Secondary){
       if (!InetAddressUtils.isIPv4Address(Primary) && !InetAddressUtils.isIPv4Address(Secondary)){
           throw new IllegalArgumentException("DNS Can only be IP4 Address");
       }
       return Task.where("{0} Updates DNS with Z and X".replace("Z",Primary).replace("X",Secondary),actor -> {
           actor.attemptsTo(
                   DnsCard().expand(),
                   Wait.forVisibilityOf(DnsCard.PRIMARY_DNS),
                   Input.of(DnsCard.PRIMARY_DNS).with(Primary),
                   Input.of(DnsCard.SECONDARY_DNS).with(Secondary),
                   Click.on(DnsCard.UPDATE_BUTTON),
                   Modal.waitUntilOpen(),
                   Modal.save(),
                   Ensure.thatTheAnswerTo(Input.of(DnsCard.PRIMARY_DNS).value()).isEqualTo(Primary),
                   Ensure.thatTheAnswerTo(Input.of(DnsCard.SECONDARY_DNS).value()).isEqualTo(Secondary)
           );
       });

   }

   public Performable removeDNS(){
        return Task.where("{0} removes DNS",actor -> {
            actor.attemptsTo(
                    DnsCard().expand(),
                    Check.whether(DnsCard().isDnsSet()).andIfSo(
                            Click.on(DnsCard.REMOVE_BUTTON),
                            Modal.waitUntilOpen(),
                            Modal.save()
                    ),
                    Ensure.thatTheAnswerTo(DnsCard().isDnsSet()).isFalse()
            );
        });
   }
}
