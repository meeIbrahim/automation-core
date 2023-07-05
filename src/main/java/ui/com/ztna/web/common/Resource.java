package ui.com.ztna.web.common;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import resource.cached.ZTAParameters;

import java.util.List;

public interface Resource <T extends ZTAParameters> {
    Performable create(T parameters);
    Performable purge();
    Performable purge(ZTAParameters parameters);
    Performable removeAssociations(ZTAParameters parameters);
    Question<ZTAParameters> getFree(List<ZTAParameters> Cached);
    Question<List<ZTAParameters>> getAllFree();
    Question<ZTAParameters> getActive(List<ZTAParameters> Cached);
    Question<List<ZTAParameters>> getAllActive();
    Question<List<ZTAParameters>> getAll();
    Question<Boolean> hasAssociations(ZTAParameters parameters);
}
