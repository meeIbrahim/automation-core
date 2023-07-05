package ui.com.ztna.vm.relay;

public class RelayCommandException extends Exception{
    public RelayCommandException(String name, Exception e){
        super("Error Running Command on Relay " + name + "\n Error: " + e);
    }
}
