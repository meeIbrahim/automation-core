Feature: Remove Policy
  As a user I would like to test all Scenarios of Remove Policy

  Background: User is logged in as Tenant
    Given User is logged in as tenant


  @TCZT-1941
  Scenario: User Removes a Policy with no Active Rules
    Given Policy A is active
    When User removes policy A
    Then Policy A should be deleted
