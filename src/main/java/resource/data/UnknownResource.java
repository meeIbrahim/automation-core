package resource.data;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class UnknownResource extends TestCompromisedException {
    public UnknownResource(String name){
        super("Cannot Find Resource matching: " + name);
    }
}
