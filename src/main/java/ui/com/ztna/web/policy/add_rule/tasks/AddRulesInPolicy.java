package ui.com.ztna.web.policy.add_rule.tasks;

import net.serenitybdd.screenplay.AnonymousPerformableFunction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import ui.com.ztna.web.policy.add_rule.interactions.AddRule;
import ui.com.ztna.web.policy.add_rule.models.AddRuleParameters;
import ui.com.ztna.web.policy.add_rule.models.RuleParameters;

import static constants.Waits.FIFTEEN;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.NO_OF_RULES;
import static ui.com.ztna.web.policy.add_policy.user_interfaces.PoliciesGridUI.POLICY_ROW;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRule.openManageRulesPageFor;

public class AddRulesInPolicy {
    public static AnonymousPerformableFunction using(AddRuleParameters addRuleParameters) {
        return Task.where("{0} attempts to add rules in policy", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(POLICY_ROW.of(addRuleParameters.policy), isPresent())
                            .forNoMoreThan(FIFTEEN).seconds()
            );
            actor.remember("NoOfRulesBeforeRulesAdded", Text.of(NO_OF_RULES.of(addRuleParameters.policy)).asInteger().answeredBy(actor));
            actor.attemptsTo(
                    openManageRulesPageFor(addRuleParameters.policy)
            );
            for (RuleParameters rule : addRuleParameters.rules)
                actor.attemptsTo(
                        AddRule.named(rule)
                );
        });
    }

    public static AnonymousPerformableFunction withConflict(AddRuleParameters addRuleParameters) {
        return Task.where("{0} attempts to add rules in policy", actor -> {
            actor.attemptsTo(
                    WaitUntil.the(POLICY_ROW.of(addRuleParameters.policy), isPresent())
                            .forNoMoreThan(FIFTEEN).seconds()
            );
            actor.remember("NoOfRulesBeforeRulesAdded", Text.of(NO_OF_RULES.of(addRuleParameters.policy)).asInteger().answeredBy(actor));
            actor.attemptsTo(
                    openManageRulesPageFor(addRuleParameters.policy)
            );
            for (RuleParameters rule : addRuleParameters.rules)
                actor.attemptsTo(
                        AddRule.withConflict(rule)
                );
        });
    }
}
