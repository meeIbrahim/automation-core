package resource.data;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class MatchingResourceNotFound  extends TestCompromisedException {
    public  MatchingResourceNotFound(String Match){
        super("No Resource Found in Data File matching criteria: " + Match);
    }
}
