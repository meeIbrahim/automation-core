package ui.com.ztna.vm.relay;

public class RelayNotFound  extends Exception{
    public RelayNotFound(String Name){
        super("Relay with name " + Name + " Not found");
    }
}
