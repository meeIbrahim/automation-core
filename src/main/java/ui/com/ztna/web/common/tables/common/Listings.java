package ui.com.ztna.web.common.tables.common;


import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import ui.com.ztna.web.common.tables.Row;

import java.util.LinkedHashSet;
import java.util.List;

public interface Listings <T extends Row> {

    Question<T> row(String matcher);
    Question<T> row(Target matcher);
    Question<T> row(Integer count);
    Performable refresh();
    Performable waitUntilReady();
    Question<Boolean> isReady();

    Question<LinkedHashSet<T>> rows();
    Question<LinkedHashSet<T>> rows(Target matcher);
    Question<LinkedHashSet<T>> rows(String matcher);
    Question<LinkedHashSet<T>> rows(String header, String matcher);

    Question<Boolean> contains(String matcher);
    Question<Boolean> contains(String header, String matcher);
    Question<Boolean> contains(Integer header, String matcher);

    /// GET ALL ELEMENTS UNDER A COLUMN
    Question<List<WebElementFacade>> columnElements(String column);
    Question<List<WebElementFacade>> columnElements(Integer column);
    Question<Integer> countRows();
    Question<Integer> countColumns();




}
