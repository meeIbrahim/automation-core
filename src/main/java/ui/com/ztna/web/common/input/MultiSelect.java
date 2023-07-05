package ui.com.ztna.web.common.input;

import io.cucumber.java.bs.A;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import ui.com.ztna.web.common.Wait.Wait;


import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class MultiSelect extends muiSelect{

    List<Target> Selections = null;
    List<Integer> IntegerSelections = null;

    public static String MultiSelectOptions = "//div[contains(@class,'select__multi-value__label')]";
    public static String ENABLED = ".//div[@role='button']/div[1]";
    String MultiSelectButton = ".//*[contains(@id,\"Button\")]";


    public static Target SELECTED_OPTION = Target.the("Selected option")
            .locatedBy("//div[contains(@class,'select__multi-value__')][.='{0}']");

    private MultiSelect(){
        super(null);
    }

    public MultiSelect(Target target){
        super(target);

    } // temporary Constructor

    public MultiSelect(Target target,List<Target> list){
        super(target);
        this.Selections = list;
    }
    /// Switched Parameter position to avoid type erasure error
    public MultiSelect(List<Integer> list,Target target){
        super(target);
        this.IntegerSelections = list;
    }

    public static MultiSelect of(Target target){
        return new MultiSelect(target);
    }



    ///////////////////////////////

    public MultiSelect select(String... Selections){
        List<Target> list = new ArrayList<>();
        for(String selection : Selections){
            list.add(DROPDOWN_SELECTION_BY_TEXT.of(selection).called(selection));
        }
        return Instrumented.instanceOf(MultiSelect.class).withProperties(target, list);
    }

    public MultiSelect select(Integer... Selections){
        List<Integer> list = new ArrayList<>(Arrays.asList(Selections));
        return Instrumented.instanceOf(MultiSelect.class).withProperties(list,target);
    }

    @Override
    @Step("{0} Selects values in #target")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                open());
        boolean toAppend = actor.asksFor(hasButton());
        Set<String> valueOfSelections = new HashSet<>();
        if (IntegerSelections != null){
            actor.attemptsTo(
                    open()
            );
            Selections = new ArrayList<>();
            for (Integer selection : IntegerSelections){
                actor.attemptsTo(
                        Scroll.to(DROPDOWN_SELECTION_BY_ORDER.of(String.valueOf(selection + 1)))
                );
                String value = DROPDOWN_SELECTION_BY_ORDER.of(String.valueOf(selection + 1)).resolveFor(actor).getText();
                valueOfSelections.add(value);
            }
            valueOfSelections.addAll(actor.asksFor(selectedValues()));
        }
        if (valueOfSelections.isEmpty()) {
            valueOfSelections = Selections.stream().map(Target::getName).collect(Collectors.toSet());
        }

        for (String selected : selectedValues().answeredBy(actor)){
            if (!valueOfSelections.contains(selected)){
                actor.attemptsTo(clearSelection(selected));
            }
            valueOfSelections.remove(selected);
        }
        Selections = valueOfSelections.stream().map(value -> DROPDOWN_SELECTION_BY_TEXT.of(value).called(value)).collect(Collectors.toList());
            for(Target Selection : Selections){
                    actor.attemptsTo(
                        open(),
                        Scroll.to(Selection));
                actor.attemptsTo(
                        Check.whether(toAppend)
                                .andIfSo(Click.on(appendButtonToTargets(Selection)))
                                        .otherwise(Click.on(Selection)),
                        Wait.forInvisibilityOf(Selection),
                        Wait.forPresenceOf(SELECTED_OPTION.of(Selection.getName()))
                );
            }
            actor.attemptsTo(
                    close()
            );
        for (Target Selection: Selections){
            actor.attemptsTo(
                    Wait.forTime(Duration.ofSeconds(4)), //TO be changed. getting error without this
                    Ensure.thatTheAnswerTo(isSelected(Selection)).isTrue()
            );
        }

    }

    public Question<List<String>> selectedValues(){
        return Question.about("selected values").answeredBy(actor ->  {

            List<WebElementFacade> elements = Target.the("selected items")
                    .locatedBy(this.target.getCssOrXPathSelector()+MultiSelectOptions).resolveAllFor(actor);
            List<String> values = new ArrayList<>();
            for( WebElementFacade element : elements){
                values.add(element.getTextValue());
            }

            return (values);

        });
    }

    public Question<List<WebElementFacade>> selectedElements(){
        return Question.about("selected elements").answeredBy(actor -> (Target.
                the("selected elements")
                .locatedBy(this.target.getCssOrXPathSelector()+MultiSelectOptions)
                .resolveAllFor(actor)));
    }

    public Performable clear(){
        return Task.where("{0} clears selection in TARGET".replace("TARGET",target.getName()),actor -> {
            List<WebElementFacade> selected = actor.asksFor(selectedElements());
            for (WebElementFacade elementFacade : selected){
                elementFacade.thenFind("./following-sibling::*[@role=\"button\"]").click();
            }
        });
    }
    protected Performable clearSelection(String selection){
        return Task.where("{0} clear single selection",actor -> {
            WebElementFacade elementFacade = SELECTED_OPTION.of(selection).resolveFor(actor);
            elementFacade.thenFind("./following-sibling::*[@role=\"button\"]").click();
        }).withNoReporting();
    }

    public Question<Boolean> isSelected(Target toSelect){
        return Question.about("is Already selected").answeredBy(actor -> {
            List<String> selected = selectedValues().answeredBy(actor);
            return selected.contains(toSelect.getName());
        });
    }


    protected Question<Boolean> hasButton(){
        return Question.about("Does Multi Select have Buttons").answeredBy(actor -> {
            actor.attemptsTo(
                this.open()
            );
            WebElementFacade firstElement = DROPDOWN_SELECTION_BY_ORDER.of("1").resolveFor(actor);
            List<WebElementFacade> toCheck = firstElement.thenFindAll(MultiSelectButton);
            return !toCheck.isEmpty();
        });
    }

    private Target appendButtonToTargets(Target toAppend){
        return Target.the(toAppend.getName())
                .locatedBy(toAppend.getCssOrXPathSelector() + MultiSelectButton);

    }




}
