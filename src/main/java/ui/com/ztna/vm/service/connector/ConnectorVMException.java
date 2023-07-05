package ui.com.ztna.vm.service.connector;

public class ConnectorVMException extends AssertionError{

    public ConnectorVMException(String ip,Exception e){
        super("Error Running Command on : "+ip , e);
    }

}
