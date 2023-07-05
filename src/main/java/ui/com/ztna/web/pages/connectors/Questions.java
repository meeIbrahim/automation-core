package ui.com.ztna.web.pages.connectors;

import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;
import ui.com.ztna.vm.relay.RelayVM;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.tables.Row;

public class Questions implements ResourceQuestions {
    public Question<ZTAParameters>  getParameters(Row row){
        return Question.about("Parameters of Connector").answeredBy(actor -> {
           String name =  row.name();
           String parent = row.getCellText(4,actor);
           String relayName = row.getCellText(3,actor);
            actor.attemptsTo(
                    row.expand()
            );
            String WG_IP = row.webElement(actor).findBy(".//following-sibling::tr//p[contains(text(),\"WG\")]/following-sibling::*").getText();
            actor.attemptsTo(
                    row.collapse()
            );

           String IP;
            try {
                RelayVM relay = new RelayVM(relayName);
                IP = relay.getConnectorIP(WG_IP);
            } catch (Exception e) {
                IP = "";
            }
            return new ZTAParameters(name,IP,parent);
        });
    }

    public static Question<String> relay(Row row){
        return Question.about("Relay of Connector ROW".replace("ROW", row.name())).answeredBy(actor -> {
            return row.getCell(3,actor).getText();
        });
    }

    public static Question<String> site(Row row){
        return Question.about("Site of Connector ROW".replace("ROW",row.name())).answeredBy(actor -> {
            return row.getCellText(4,actor);
        });
    }

    public static Question<Boolean> isDisabled(Row row){
        return Question.about("is Disabled Connector ROW".replace("ROW",row.name())).answeredBy(actor -> {
            return row.contains(ConnectorUI.CONNECTOR_STATUS_DISABLED).answeredBy(actor);
        });
    }

    public static Question<Boolean> isEnabled(Row row){
        return Question.about("is Enabled Connector ROW".replace("ROW",row.name())).answeredBy(actor -> {
            return row.contains(ConnectorUI.CONNECTOR_STATUS_ENABLED).answeredBy(actor);
        });
    }

    public static Question<Boolean> isRevoked(Row row){
        return Question.about("Is Connector Revoked").answeredBy(actor -> {
            return row.contains(ConnectorUI.CONNECTOR_STATUS_REVOKED).answeredBy(actor);
        });
    }
    public Question<Boolean> isAwsHosted(Row row){
        return Question.about("Is Aws Hosted").answeredBy(actor -> {
            return row.contains(ConnectorUI.SITE_ICON.of("5","0"),4).answeredBy(actor);
        });
    }
    public Question<Boolean> isAzureHosted(Row row){
        return Question.about("Is Azure Hosted").answeredBy(actor -> {
            return row.contains(ConnectorUI.SITE_ICON.of("4","1"),4).answeredBy(actor);
        });
    }
    public Question<Boolean> isGcpHosted(Row row){
        return Question.about("Is GCP Hosted").answeredBy(actor -> {
            return row.contains(ConnectorUI.SITE_ICON.of("4","0"),4).answeredBy(actor);
        });
    }
    public Question<Boolean> isPrivateHosted(Row row){
        return Question.about("Is Private Hosted").answeredBy(actor -> {
            return row.contains(ConnectorUI.SITE_ICON.of("3","0"),4).answeredBy(actor);
        });
    }
    public Question<String> hostingProvider(Row row){
        return Question.about("Hosting Provider").answeredBy(actor -> {
            if (isAwsHosted(row).answeredBy(actor)){
                return "aws";
            }
            else if (isAzureHosted(row).answeredBy(actor)){
                return "azure";
            }
            else if (isGcpHosted(row).answeredBy(actor)){
                return "gcp";
            } else {
                return "private";
            }
        });
    }

}
