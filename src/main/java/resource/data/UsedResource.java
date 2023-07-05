package resource.data;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class UsedResource extends TestCompromisedException {
    public UsedResource(String ResourceType) {
        super("No Free Resource Available: " + ResourceType);
    }
}
