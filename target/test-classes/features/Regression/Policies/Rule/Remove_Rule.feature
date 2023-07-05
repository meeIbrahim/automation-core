@current
Feature: Remove Rule
  As a user I would like to test all Scenarios of Removing Rules

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-8971
  Scenario: User Deletes a Standard Service Rule for a User
    Given rule A for user on service is available
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8973
  Scenario: User Deletes an impending Service Rule for a User
    Given rule A for user on service is available with time starting after 0 H 3 M and Ending after 0 H and 9 M
    And rule A is Impending after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8974
  Scenario: User Deletes an expired Service Rule for a User
    Given rule A for user on service is available with time starting after -1 H 0 M and Ending after 0 H and 0 M
    And rule A is Expired after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8976
  Scenario: User Deletes a Location Based Service Rule for a User
    Given rule A for user on service is available with countries Pakistan,China,Brazil
    And user verifies that rule A is added for China,Pakistan,Brazil
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8972
  Scenario: User Deletes a Standard Project Rule for User
    Given rule A for user on project is available
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8977
  Scenario: User Deletes an impending Project Rule for a User
    Given rule A for user on project is available with time starting after 0 H 3 M and Ending after 0 H and 9 M
    And rule A is Impending after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-8978
  Scenario: User Deletes an expired Project Rule for a User
    Given rule A for user on project is available with time starting after -1 H 0 M and Ending after 0 H and 0 M
    And rule A is Expired after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89710
  Scenario: User Deletes a Location Based Project Rule for a User
    Given rule A for user on project is available with countries Pakistan,China,Brazil
    And user verifies that rule A is added for China,Pakistan,Brazil
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89711
  Scenario: User Deletes a Standard Service Rule for a Access Group
    Given rule A for accessgroup on service is available
    When User removes rule A
    Then rule A is removed successfully
  @TCZT-89713
  Scenario: User Deletes an impending Service Rule for a Access Group
    Given rule A for accessgroup on service is available with time starting after 0 H 3 M and Ending after 0 H and 9 M
    And rule A is Impending after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89714
  Scenario: User Deletes an expired Service Rule for a Access Group
    Given rule A for accessgroup on service is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    And rule A is Expired after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89716
  Scenario: User Deletes a Location Based Service Rule for a Access Group
    Given rule A for accessgroup on service is available with countries Pakistan,China,Brazil
    And user verifies that rule A is added for China,Pakistan,Brazil
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89712
  Scenario: User Deletes a Standard Project Rule for Access Group
    Given rule A for accessgroup on project is available
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89717
  Scenario: User Deletes an impending Project Rule for a Access Group
    Given rule A for accessgroup on project is available with time starting after 0 H 3 M and Ending after 0 H and 9 M
    And rule A is Impending after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89718
  Scenario: User Deletes an expired Project Rule for a Access Group
    Given rule A for accessgroup on project is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    And rule A is Expired after 2 mins
    When User removes rule A
    Then rule A is removed successfully

  @TCZT-89720
  Scenario: User Deletes a Location Based Project Rule for a Access Group
    Given rule A for accessgroup on project is available with countries Pakistan,China,Brazil
    And user verifies that rule A is added for China,Pakistan,Brazil
    When User removes rule A
    Then rule A is removed successfully


