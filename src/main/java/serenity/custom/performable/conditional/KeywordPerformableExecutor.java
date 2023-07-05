package serenity.custom.performable.conditional;

import com.google.gson.JsonObject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.conditions.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;



public class KeywordPerformableExecutor implements Task {
    KeywordPerformable[] keywordPerformables;
    List<String> keySet;

    public KeywordPerformableExecutor(KeywordPerformable[] keywordPerformables) {
        this.keywordPerformables = keywordPerformables;
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        for(KeywordPerformable keywordPerformable : keywordPerformables) {
            t.attemptsTo(Check.whether(matchFoundFor(keywordPerformable)).andIfSo(keywordPerformable));
        }
    }

    private Boolean matchFoundFor(KeywordPerformable keywordPerformable) {
        List<Boolean> anyMatch = keySet.stream().map(taskName-> List.of(keywordPerformable.getKeywords()).contains(taskName)).collect(Collectors.toList());
        return anyMatch.contains(Boolean.TRUE);
    }

    public static KeywordPerformableExecutor executeKeywordPerformable(KeywordPerformable... keywordPerformables) {
        return new KeywordPerformableExecutor(keywordPerformables);
    }

    public KeywordPerformableExecutor onlyIfItsIn(Set<String> keySet) {
        this.keySet = new ArrayList<>(keySet);
        return this;
    }

    public KeywordPerformableExecutor onlyIfItsIn(Map<String,String> parameters) {
        this.keySet = new ArrayList<>(parameters.keySet());
        return this;
    }

    public KeywordPerformableExecutor onlyIfItsIn(JsonObject parameters) {
        this.keySet = new ArrayList<>(parameters.keySet());
        return this;
    }

    public KeywordPerformableExecutor onlyIfItsIn(List<String> performableTaskNames) {
        this.keySet = performableTaskNames;
        return this;
    }

    public KeywordPerformableExecutor onlyIfItsIn(String performableTaskName) {
        this.keySet = new ArrayList<>(keySet);
        return this;
    }

}
