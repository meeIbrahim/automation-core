package ui.com.ztna.web.pages.rules;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.conditions.Check;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;
import resource.cached.ZTAParameters;
import resource.data.Data;
import resource.manager.ResourceException;
import serenity.custom.questions.URL;
import ui.com.ztna.web.common.filters.BulkFilters;
import ui.com.ztna.web.common.filters.Filter;
import ui.com.ztna.web.common.filters.Query;
import ui.com.ztna.web.common.input.Checkbox;
import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;

import ui.com.ztna.web.common.Wait.Wait;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.ZTA.ZTA;
import ui.com.ztna.web.pages.policies.Policies;
import ui.com.ztna.web.pages.rules.modals.AddRule;
import ui.com.ztna.web.parameters.AGParameters;
import ui.com.ztna.web.parameters.RuleParameters;
import ui.com.ztna.web.parameters.UserParameters;

import java.util.*;

import static ui.com.ztna.web.common.interactions.CommonInteraction.waitNotPresenceOf;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.handleTimeAccess;
import static ui.com.ztna.web.policy.add_rule.interactions.AddRuleForm.locationAccess;

public class Rules extends  ResourcePage<RuleParameters> {

    String parentPolicy;
    Policies policyPage;

    Table table = new Table(this,
            "Rules",
            "Rule Name",
            "Status",
            "User/Access Group",
            "Location Based Access", "Time Based Access");

    ResourceUI UI = new RulesUI();
    ResourceQuestions questions = new Questions();

    /// Do not Instantiate without Policy
    public Rules(Policies policy,String parentPolicy){
        this.policyPage = policy != null ? policy : new Policies();
        this.parentPolicy = parentPolicy;
    }
    public Policies policy(){
        return policyPage;
    }
    @Override
    public String url() {
        return "/app/policies?policy-id="; /// Rule URL is used for Filters and not for navigation
    }


    @Override
    public Performable goToPageURL() {
        return policyPage.manageRules(parentPolicy);
    }

    @Override
    public Question<Boolean> isOnPage() {
        return Question.about("Is On Page").answeredBy(actor -> {
            EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
            String baseURL = EnvironmentSpecificConfiguration.from(env).getProperty("webdriver.base.url");
            String url = baseURL + url();
            if(!actor.asksFor(URL.current()).contains(url)){
                return false;
            }
            return Questions.getPolicy().answeredBy(actor).equals(parentPolicy);
        });
    }

    public static Question<Boolean> isOpen() {
        return Question.about("Is Rules Page Open").answeredBy(
                actor -> {
                    actor.attemptsTo(waitNotPresenceOf(LOADER));
                    List<WebElementFacade> elements = RulesUI.TITLE.resolveAllFor(actor);
                    return !elements.isEmpty();
                }
        );
    }


    @Override
    public Table table() {
        return table;
    }

    @Override
    public ResourceUI ui() {
        return UI;
    }

    @Override
    public ResourceQuestions question() {
        return questions;
    }

    @Override
    public Performable refresh() {
        return Task.where("{0} refreshes the rule page", actor -> {
            boolean checked = Checkbox.of(RulesUI.REMOVE_RULES_CHECKBOX).isChecked().answeredBy(actor);
            List<Query> filters = Filter.applied().answeredBy(actor);
            BulkFilters allFilters = Filter.using(filters.toArray(Query[]::new));
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            driver.navigate().refresh();
            actor.attemptsTo(
                    PageLoad.waitFor(),
                    waitNotPresenceOf(LOADER),
                    allFilters.applyAll(),
                    Check.whether(checked).andIfSo(
                            Checkbox.of(RulesUI.REMOVE_RULES_CHECKBOX).enable()
                    ).otherwise(
                            Checkbox.of(RulesUI.REMOVE_RULES_CHECKBOX).disable()
                    )
            );
        }).withNoReporting();
    }

    @Override
    public Performable create(RuleParameters parameters) {

        return Task.where("{0} Adds Rule",actor -> {
            if (Objects.equals(parameters.attachment, ""))
            {
                ZTAParameters attachment;
                try {
                    if (parameters.forAccessGroup) {
                        attachment = actor.asksFor(ZTA.accessGroups().getFree());
                        if (attachment == null) {
                            AGParameters ag = Data.accessGroup().get();
                            actor.attemptsTo(ZTA.accessGroups().create(ag));
                            attachment = ag;
                        }
                    } else {
                        attachment = actor.asksFor(ZTA.users().getFree());
                        if (attachment == null) {
                            UserParameters user = Data.localUser().any();
                            actor.attemptsTo(ZTA.users().create(user));
                            attachment = user;
                        }
                    }
                }
                catch (Exception e){
                    throw new ResourceException("User");
                }
                parameters.attachment = attachment.name;
            }
            Rules rule = actor.asksFor(ZTA.policies().rules(parameters.policy));
            actor.attemptsTo(
                    rule.openPage(),
                    rule.waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddRule.open(),
                                    AddRule.fillGeneralInfo(parameters.name,parameters.attachment,parameters.forAccessGroup)
                            );
                            if(parameters.timeAccess){
                                actor.attemptsTo(
                                        handleTimeAccess(parameters.STH,parameters.STM,parameters.ETH,parameters.ETM)
                                );
                            }
                            if(parameters.locationAccess){
                                actor.attemptsTo(
                                        locationAccess(parameters.countries)
                                );
                            }
                            actor.attemptsTo(
                                    Modal.save()
                            );
                        }
                    },RulesUI.RULE_NAME.of(parameters.name))
            );
        });
    }

    @Override
    public Question<ZTAParameters> getFree(List<ZTAParameters> Cached) {
        return getActive(Cached);
    }
    @Override
    public Question<List<ZTAParameters>> getAllFree() {
        return getAllActive();
    }

    @Override
    public Question<ZTAParameters> getActive(List<ZTAParameters> Cached) {
        return Question.about("Active Rule").answeredBy(actor -> {
            actor.attemptsTo(
                    ZTA.policies().openPage()
            );
            Iterator<Row> policyIterator = ZTA.policies().table().rowIterator().answeredBy(actor);
            ZTAParameters policy = null;
            while (policyIterator.hasNext()){
                Row row = policyIterator.next();
                if(actor.asksFor(ui.com.ztna.web.pages.policies.Questions.hasRules(row))){
                    policy = ZTA.policies().question().getParameters(row).answeredBy(actor);
                }
            }
            if (policy==null){return null;}
            String policyName = policy.name;
            Rules rule = actor.asksFor(ZTA.policies().rules(policyName));
            actor.attemptsTo(
                    rule.openPage());
            HashSet<Row> rows = actor.asksFor(rule.table().rows(RulesUI.RULE_STATUS_ACTIVE));
            if (rows.size() == 0){return null;}
            return actor.asksFor(question().getParameters(rows.iterator().next()));
        });
    }

    @Override
    public Question<List<ZTAParameters>> getAllActive() {
        return Question.about("All Active Rules").answeredBy(actor -> {
            ArrayList<ZTAParameters> rules = new ArrayList<>();
            actor.attemptsTo(
                    ZTA.policies().openPage()
            );
            Iterator<Row> policyIterator = ZTA.policies().table().rowIterator().answeredBy(actor);
            ArrayList<ZTAParameters> policies = new ArrayList<>();
            while (policyIterator.hasNext()){
                Row row = policyIterator.next();
                if(actor.asksFor(ui.com.ztna.web.pages.policies.Questions.hasRules(row))){
                    policies.add(ZTA.policies().question().getParameters(row).answeredBy(actor));
                }
            }
            if(policies.isEmpty()){return rules;}
            for (ZTAParameters policy : policies){
                String policyName = policy.name;
                Rules rule = actor.asksFor(ZTA.policies().rules(policyName));
                actor.attemptsTo(
                        rule.openPage());
                Iterator<Row> iterator = rule.table().rowIterator().answeredBy(actor);
                while (iterator.hasNext()){
                    Row row = iterator.next();
                    rules.add(question().getParameters(row).answeredBy(actor));
                }
            }
            return rules;
        });
    }

    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Rule ROW free".replace("ROW",row.name())).answeredBy(
                actor -> row.contains(RulesUI.RULE_STATUS_ACTIVE).answeredBy(actor));
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Rule ROW active".replace("ROW",row.name())).answeredBy(
                actor -> row.contains(RulesUI.RULE_STATUS_ACTIVE).answeredBy(actor));
    }

    @Override
    public Performable removeAssociations(String name){
        return Task.where("{0} removes Association for Rule Z".replace("Z",name),actor -> {
        }).withNoReporting();
    }

}
