package resource.manager;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class ResourceException extends TestCompromisedException {
    public ResourceException(String Resource){
        super("Resource Manager couldn't Set up Required Resources\nResource :: " + Resource);
    }
}
