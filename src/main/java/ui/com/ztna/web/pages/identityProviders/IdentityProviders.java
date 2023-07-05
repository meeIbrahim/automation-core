package ui.com.ztna.web.pages.identityProviders;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.common.ListingImplementor;
import ui.com.ztna.web.parameters.IDPParameters;

public class IdentityProviders<T extends IDPParameters>  extends ResourcePage<T> {

    @Override
    public String url() {
        return "/app/tenant-identity-providers";
    }

    @Override
    public ListingImplementor table() {
        return null;
    }

    @Override
    public ResourceUI ui() {
        return null;
    }

    @Override
    public ResourceQuestions question() {
        return null;
    }

    @Override
    public Question<Boolean> isFree(Row row) {
        return null;
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return null;
    }

    @Override
    public Performable create(IDPParameters parameters) {
        return null;
    }
}
