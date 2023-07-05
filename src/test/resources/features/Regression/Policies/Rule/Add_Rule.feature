Feature: Add Rule
  As a user I would like to test all Scenarios of Adding Rule


  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @smoke
  Scenario: User adds a rule for smoke test
    Given policy A is available
    When User adds Rule A for Policy A
    Then Rule A should be added

  @TCZT-8951
  Scenario: User adds a rule for a project to allow access to an empty access group
    Given policy A is available with project
    When User adds Rule A on Access Group for Policy A
    Then Rule A should be added

  @TCZT-8952
  Scenario: User adds a rule for a empty project to allow access to an empty access group
    Given policy A is available with project
    When User adds Rule A on Access Group for Policy A
    Then Rule A should be added

  @TCZT-8953
  Scenario: User Adds a Standard Rule for a Service to allow access to a single User
    Given policy A is available with service
    When User adds Rule A on User for Policy A
    Then Rule A should be added

  @TCZT-8954
  Scenario: Users Adds a Standard Rule for a Service to allow access to a Access Group
    Given policy A is available with service
    When User adds Rule A on Access Group for Policy A
    Then Rule A should be added

  @TCZT-8955
  Scenario: User Adds a Time Based Rule for a Service to allow access to a Access Group
    Given policy A is available with service
    When User adds Rule A on Access Group for Policy A starting after 0 H 3 M and Ending after 0 H and 9 M
    Then rule A is Impending
    And rule A is Active after 4 mins
    And rule A is Expired after 8 mins

  @TCZT-8956
  Scenario: User Adds a Time Based Rule for a Service to allow access to a User
    Given policy A is available with service
    When User adds Rule A on User for Policy A starting after 0 H 3 M and Ending after 0 H and 9 M
    Then rule A is Impending
    And rule A is Active after 4 mins
    And rule A is Expired after 8 mins

  @TCZT-8957
  Scenario: User adds location based for a Service to allow access to a User
    Given policy A is available with service
    When User adds Rule A on User for Policy A for countries China,Pakistan,Afghanistan
    Then rule A is Active
    Then user verifies that rule A is added for China,Pakistan,Afghanistan
    And user verifies that policy A rule count has increased

  @TCZT-8958
  Scenario: User adds location based for a Service to allow access to a Access Group
    Given policy A is available with service
    When User adds Rule A on Access Group for Policy A for countries China,Pakistan,Afghanistan
    Then rule A is Active
    Then user verifies that rule A is added for China,Pakistan,Afghanistan
    And user verifies that policy A rule count has increased

  @TCZT-89511
  Scenario: User Adds a Standard Rule for a Project to allow access to a single User
    Given policy A is available with project
    When User adds Rule A on User for Policy A
    Then Rule A should be added

  @TCZT-89512
  Scenario: Users Adds a Standard Rule for a Project to allow access to a Access Group
    Given policy A is available with project
    When User adds Rule A on Access Group for Policy A
    Then Rule A should be added

  @TCZT-89513
  Scenario: User Adds a Time Based Rule for a Project to allow access to a Access Group
    Given policy A is available with project
    When User adds Rule A on Access Group for Policy A starting after 0 H 3 M and Ending after 0 H and 9 M
    Then rule A is Impending after 2 mins
    And rule A is Active after 4 mins
    And rule A is Expired after 8 mins

  @TCZT-89514
  Scenario: User Adds a Time Based Rule for a Project to allow access to a User
    Given policy A is available with project
    When User adds Rule A on User for Policy A starting after 0 H 3 M and Ending after 0 H and 9 M
    Then rule A is Impending after 2 mins
    And rule A is Active after 4 mins
    And rule A is Expired after 8 mins

  @TCZT-89515
  Scenario: User adds location based for a Project to allow access to a User
    Given policy A is available with project
    When User adds Rule A on User for Policy A for countries China,Pakistan,Afghanistan
    Then rule A is Active
    Then user verifies that rule A is added for China,Pakistan,Afghanistan
    And user verifies that policy A rule count has increased

  @TCZT-89516
  Scenario: User adds location based for a Project to allow access to a Access Group
    Given policy A is available with project
    When User adds Rule A on Access Group for Policy A for countries China,Pakistan,Afghanistan
    Then rule A is Active
    Then user verifies that rule A is added for China,Pakistan,Afghanistan
    And user verifies that policy A rule count has increased

