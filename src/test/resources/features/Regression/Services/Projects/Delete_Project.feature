Feature: Delete Project
  As a user I would like to test all delete project scenarios

  @scenario-1
  Scenario: Delete Project with no Policy Attached
    When User creates empty Project A
    Then Project A should be added
    Then user removes Project A
    Then Project A should be removed

  @scenario-2
  Scenario: Delete Project with Policy Attached
    When User creates empty Project A
    Then Project A should be added
    When User adds Policy A for Project A
    Then Policy A is added and is active
    When User adds Rule A for Policy A
    Then Rule A should be added
    Then user removes Project A
    Then Project A should be removed
