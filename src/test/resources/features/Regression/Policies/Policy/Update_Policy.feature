Feature: Update Policy
  As a user I would like to test all Scenarios of Updating Policy


  Background: User is logged in as Tenant
    Given User is logged in as tenant


  @TCZT-1938
  Scenario: User Updates Policy Name
    Given Policy A is active
    When User updates name for policy A
    Then Name for policy A should be updated


  @TCZT-1939
  Scenario: User Updates Policy Description
    Given Policy A is active
    When User updates description for policy A
    Then Description for policy A should be updated

  @TCZT-1940
  Scenario: User tries to Update Policy Name to a preexisting Policy Name
    Given Policy A is active
    And Policy B is active
    When User updates name for policy A with name of policy B
    Then Duplicate Policy Conflict Error is shown
