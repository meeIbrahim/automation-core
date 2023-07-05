package ui.com.ztna.web.common.tables;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import ui.com.ztna.web.common.tables.action.menu.ActionMenu;
import ui.com.ztna.web.common.tables.action.menu.Action_Menu;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Row {
    final String root;
    private Action_Menu actionMenu;


    public static final String CHILD_ELEMENTS = ".//*[@*[contains(., \"Cell\")]][@id]";
    Target rowTarget = Target.the("Row")
            .locatedBy("//*[@*[contains(., \"Cell\")]][@id][1][descendant-or-self::*[text()=\"{0}\"]]" +
                    "//ancestor::*[" + Action_Menu.ROW_MENU.getCssOrXPathSelector() + "][1]");
    Target stringMatcher = Target.the("Row String Match")
            .locatedBy("//*[./text()[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),\"{0}\")]]");
    Target expanded = Target.the("Expanded Row")
            .locatedBy( "//*[@aria-expanded=\"true\"]");
    Target expandButton = Target.the("Expand Button")
            .locatedBy("//*[@data-testid=\"expand-button\"]");
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return name().equals(row.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }

    public Row(WebElementFacade rowElement){
        this.root = getElementText(rowElement,1);
    }

    public Action_Menu menu(){
        if (actionMenu == null){
            actionMenu = ActionMenu.For(this);}
        return actionMenu;
    }

    public String name(){
        return root;
    }

    public <T extends Actor> WebElementFacade webElement(T actor){
        return rowTarget.of(name()).resolveFor(actor);
    }
    public Question<WebElementFacade> webElement(){
        return Question.about("Web Element of Row").answeredBy(
                actor -> rowTarget.of(name()).resolveFor(actor)
        );
    }
    public static String getElementText(WebElementFacade element, Integer column){
        String name = element.thenFindAll(CHILD_ELEMENTS).get(column - 1).getText();
        Matcher time  = Pattern.compile("^(.*)\\n+(.*ago.*)",Pattern.MULTILINE | Pattern.DOTALL).matcher(name);
        if (time.find()){
            name = time.group(1);
        }
        Matcher NA = Pattern.compile("^(.*\\n*.*)\\n+(N/A)",Pattern.MULTILINE).matcher(name);
        if(NA.find()){
            name = NA.group(1);
        }
        Matcher title = Pattern.compile("^.*\\n+(.*\\n*.+)",Pattern.MULTILINE).matcher(name);
        if (title.find()){
            name = title.group(1).trim();
        }
        return name.trim();
    }


    public Performable expand(){
        return Task.where("{0} Expands Row",actor -> {
            WebElementFacade button = webElement(actor).findBy(
                    NormalizeSubElement(expandButton).getCssOrXPathSelector()
            );
            actor.attemptsTo(
                    Check.whether(isExpanded()).otherwise(
                            Click.on(button),
                            Ensure.thatTheAnswerTo(isExpanded()).isTrue()
                    )
            );
        });
    }


    public Performable collapse(){
        return Task.where("{0} Collapses Row",actor -> {
            WebElementFacade button = webElement(actor).findBy(
                    NormalizeSubElement(expandButton).getCssOrXPathSelector()
            );
            actor.attemptsTo(
                    Check.whether(isExpanded()).andIfSo(
                            Click.on(button),
                            Ensure.thatTheAnswerTo(isExpanded()).isFalse()
                    )
            );
        });
    }

    public Performable action(Target actionTarget){
        return menu().action(actionTarget);
    }
    public <T extends Actor> WebElementFacade getCell(Integer column,T actor){
        return webElement(actor).thenFindAll(By.xpath(CHILD_ELEMENTS)).get(column - 1);
    }
    public <T extends Actor> String   getCellText(Integer column,T actor){
        return getElementText(webElement(actor),column);
    }

    public Question<String> getCellText(Integer column){
        return Question.about("Text in Cell: Z".replace("Z",String.valueOf(column))).answeredBy(actor -> getCellText(column,actor));
    }

    protected Target NormalizeSubElement(Target input){
        if(input.getCssOrXPathSelector().startsWith("."))
        {
            return input;
        } else if (input.getCssOrXPathSelector().startsWith("/")) {
            return Target.the(input.getName()).locatedBy("." + input.getCssOrXPathSelector());
        }
        else {throw new IllegalArgumentException();}
    }

    public Question<Boolean> isExpanded(){
        return Question.about("Is Row Expanded").answeredBy(actor -> {
            List<WebElementFacade> elements = webElement(actor)
                    .thenFindAll(
                            NormalizeSubElement(expanded).getCssOrXPathSelector()
                    );
            return !elements.isEmpty();
        });
    }

    public Question<Boolean> contains(Target matchingTarget){
        return Question.about("Row has element MATCHER".replace("MATCHER",matchingTarget.getName())).answeredBy(
                actor -> {
                    Target target = NormalizeSubElement(matchingTarget);
                    List<WebElementFacade> elements = webElement(actor).thenFindAll(target.getCssOrXPathSelector());
                    return !elements.isEmpty();
                }
        );
    }
    public Question<Boolean> contains(String matchingString){
        Target MATCHER = stringMatcher.of(matchingString).called(matchingString);
        return contains(MATCHER);
    }
    public Question<Boolean> contains(String matchingString,Integer column){
        Target MATCHER = stringMatcher.of(matchingString.toLowerCase()).called(matchingString);
        return contains(MATCHER,column);
    }

    public Question<Boolean> contains(Target matchingTarget, Integer column){
        return Question.about("Has MATCH in Column: NUM".replace("NUM",String.valueOf(column)).replace("MATCH",matchingTarget.getName()))
                .answeredBy(actor -> {
                    Target target = NormalizeSubElement(matchingTarget);
                    WebElementFacade cell = webElement(actor).thenFindAll(CHILD_ELEMENTS).get(column - 1);
                    return !cell.thenFindAll(target.getCssOrXPathSelector()).isEmpty();
                });
    }

    public Question<Boolean> exists(){
        return Question.about("Does row exist").answeredBy(actor -> {
            List<WebElementFacade> elements = rowTarget.of(name()).resolveAllFor(actor);
            return !elements.isEmpty();
        });
    }

}
