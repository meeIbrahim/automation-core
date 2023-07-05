package ui.com.ztna.web.common.filters;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import org.jetbrains.annotations.NotNull;
import ui.com.ztna.web.common.filters.actions.Apply;
import ui.com.ztna.web.common.filters.actions.Clear;
import ui.com.ztna.web.common.filters.questions.getValue;
import ui.com.ztna.web.common.filters.questions.isApplied;

import java.util.Objects;

public class Query {



    String target;
    String Value = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query that = (Query) o;
        return ( target.contains(that.target) | that.target.contains(target));
    }

    @Override
    public int hashCode() {
        return Objects.hash(target);
    }

    public Query(String target, String Value){
        this.target = target.toLowerCase();
        this.Value = Value;
    }
    public Query(String target){
        this.target = target.toLowerCase();
        this.Value = "";
    }
    public Query with(String value){
        this.Value = value;
        return this;
    }
    @NotNull
    public String getFilter(){
        return target;
    }
    @NotNull
    //// Get Stored Object Value
    public String getValue(){
        return Value;
    }

    public <T extends Actor> void syncAppliedValue(T actor){
        this.Value = actor.asksFor(getAppliedValue());
    }

    /// Get Applied Filter Value and update Object value
    public Question<String> getAppliedValue(){
        return getValue.forFilter(this.getFilter());
    }

    public Question<Boolean> isApplied(){
        return isApplied.forFilter(getFilter());
    }
    public Performable clear(){
        return Clear.filter(getFilter());
    }
    public Performable apply(){
        return Apply.filter(getFilter()).withValue(getValue());
    }
}
