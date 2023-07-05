package ui.com.ztna.web.pages.policies;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import ui.com.ztna.web.common.page.Modal;
import ui.com.ztna.web.common.page.ResourcePage;
import ui.com.ztna.web.common.page.ResourceQuestions;
import ui.com.ztna.web.common.page.ResourceUI;
import resource.cached.ZTAParameters;

import ui.com.ztna.web.common.interactions.PageLoad;
import ui.com.ztna.web.common.tables.Row;
import ui.com.ztna.web.common.tables.Table;
import ui.com.ztna.web.pages.policies.modals.AddPolicy;
import ui.com.ztna.web.pages.rules.Rules;
import ui.com.ztna.web.parameters.PolicyParameters;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ui.com.ztna.web.pages.policies.Questions.hasProject;

public class Policies extends ResourcePage<PolicyParameters> {


    Table table = new Table(this,
            "Policies",
            "Name",
            "Status",
            "Rules Status",
            "Resource", "No. of Rules");

    ResourceUI ui = new PoliciesUI();
    ResourceQuestions questions = new Questions();

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Table table() {
        return table;
    }

    @Override
    public ResourceUI ui(){
        return ui;
    }

    @Override
    public ResourceQuestions question() {
        return questions;
    }

    @Override
    public Performable create(PolicyParameters parameters) {
        return Task.where("{0} adds Policy",actor -> {
            actor.attemptsTo(
                    openPage(),
                    waitForNewResource(new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            actor.attemptsTo(
                                    AddPolicy.open(),
                                    AddPolicy.fill(parameters.name,parameters.description,parameters.attachment,parameters.forProject),
                                    Modal.save(),
                                    openPage(),
                                    PageLoad.waitFor()
                            );
                        }
                    },PoliciesUI.POLICY_STATUS_ACTIVE,PoliciesUI.ACTIVE_POLICY)
            );
        });
    }

    public Question<ZTAParameters> getFree(List<ZTAParameters> Cached, Boolean hasProject) {
        return Question.about("Free Policy").answeredBy(actor -> {
            actor.attemptsTo(
                    openPage()
            );
            Iterator<Row> iterator = table().rowIterator().answeredBy(actor);
            while (iterator.hasNext()){
                Row resource = iterator.next();
                if (Boolean.TRUE.equals(actor.asksFor(isFree(resource,hasProject)))){
                    if(!Cached.stream()
                            .map(ZTAParameters::getName)
                            .collect(Collectors.toList()).contains(resource.name())) {
                        return actor.asksFor(question().getParameters(resource));
                    }                }
            }
            return null;
        });
    }


    @Override
    public Question<Boolean> isFree(Row row) {
        return Question.about("Is Policy: ROW free".replace("ROW",row.name())).answeredBy(actor -> {
            return isActive(row).answeredBy(actor);
        });
    }

    public Question<Boolean> isFree(Row row, Boolean hasProject) {
        return Question.about("Is Policy: ROW free".replace("ROW",row.name())).answeredBy(actor -> {
            if(Boolean.TRUE.equals(hasProject)) {
                return (isActive(row).answeredBy(actor) && hasProject(row).answeredBy(actor));
            }else{
                return (isActive(row).answeredBy(actor) && !hasProject(row).answeredBy(actor));
            }
        });
    }

    @Override
    public Question<Boolean> isActive(Row row) {
        return Question.about("Is Policy: ROW active".replace("ROW",row.name())).answeredBy(actor -> {
            Boolean isHealthy = row.contains(PoliciesUI.ACTIVE_POLICY).answeredBy(actor);
            Boolean isActive =  actor.asksFor(row.contains("Active",2));
            return (isActive && isHealthy);
        });
    }

    @Override
    public String url() {
        return "/app/policies";
    }


    public Performable manageRules(String policy){
        return Task.where("{0} Navigates to Rules for POLICY".replace("POLICY",policy),actor -> {
            actor.attemptsTo(
                    openPage());
            Row row;
            if (policy.equals("")){
                throw new IllegalArgumentException("Empty Policy Name!");
            }
            else {
                row = actor.asksFor(table().row(policy));
            }
            if (row == null){
                try {
                    throw new PolicyDoesntExist(policy);
                } catch (PolicyDoesntExist e) {
                    throw new AssertionError(e);
                }
            }
                    actor.attemptsTo(
                            row.action(PoliciesUI.MANAGE_RULES_ACTION_MENU),
                    Ensure.thatTheAnswerTo(Rules.isOpen()).isTrue()
            );

        });
    }

    public Question<Rules> rules(String policy){
        return Question.about("Get Rule for Policy").answeredBy(actor -> new Rules(this,policy));
    }

    @Override
    public Performable removeAssociations(String name){
        return Task.where("{0} removes Association for Policy Z".replace("Z",name),actor -> {

        }).withNoReporting();
    }
}
