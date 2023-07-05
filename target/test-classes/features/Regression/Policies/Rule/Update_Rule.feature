Feature: Update Rule
  As a user I would like to test all Scenario:s of Updating Rule

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-8961
  Scenario: User Updates a Standard Service Rule for a User Changes Rule Name
    Given rule A for user on service is available
    When User updates name for rule A
    Then Name for rule A should be updated

  @TCZT-8962
  Scenario: User Updates a Standard Service Rule for a User Changes Rule Name to a preexisting Rule's name
    Given rule A for user on service is available
    When rule B for user on service is available
    Then User updates name for rule A with name of rule B
    And Duplicate Rule Conflict Error is shown

  @TCZT-8963
  Scenario: User Updates a Standard Service Rule for a User to include Location Based Access
    Given rule A for user on service is available
    When user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-8964
  Scenario: User Updates a Standard Service Rule for a User to include Time Based Access
    Given rule A for user on service is available
    When user updates time based access for rule A and now rule is accessible starting after 0 H 3 M and Ending after 0 H and 9 M
    When rule A is Impending after 2 mins

  @TCZT-8966
  Scenario: User Updates a Time based Service Rule for a User to remove time based access check
    Given rule A for user on service is available with time starting after 0 H 0 M and Ending after 0 H and 9 M
    When user removes time based access for rule A
    Then rule A is Active

  @TCZT-8967
  Scenario: User Updates a Location Based  Service Rule for a User to remove location based access check
    Given rule A for user on service is available with countries Pakistan,China,Brazil
    When user removes location based access for rule A
    Then location access is removed for rule A

  @TCZT-89610
  Scenario: User Updates a Time Based Service Rule for a User and extends the end time
    Given rule A for user on service is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 0 M and Ending after 0 H and 10 M
    And rule A is Active

  @TCZT-89611
  Scenario: User Updates a Time Based Service Rule for a User and moves the start time ahead of current time
    Given rule A for user on service is available with time starting after 0 H 0 M and Ending after 0 H and 10 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 5 M and Ending after 0 H and 10 M
    And rule A is Impending after 2 mins

  @TCZT-89612
  Scenario: User Updates a Location Based Service Rule for a User to a new Location
    Given rule A for user on service is available with countries Pakistan,China,Brazil
    When user verifies that rule A is added for China,Pakistan,Brazil
    And user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89613
  Scenario: User Updates a Standard Project Rule for a User and Changes Rule Name
    Given rule A for user on project is available
    When User updates name for rule A
    Then Name for rule A should be updated

  @TCZT-89614
  Scenario: User Updates a Standard Project Rule for a User and Changes Rule Name to a preexisting Rule's name
    Given rule A for user on project is available
    When rule B for user on project is available
    Then User updates name for rule A with name of rule B
    And Duplicate Rule Conflict Error is shown

  @TCZT-89615
  Scenario: User Updates a Standard Project Rule for a User to include Location Based Access
    Given rule A for user on project is available
    When user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89616
  Scenario: User Updates a Standard Project Rule for a User to include Time Based Access
    Given rule A for user on project is available
    When user updates time based access for rule A and now rule is accessible starting after 0 H 3 M and Ending after 0 H and 9 M
    When rule A is Impending after 2 mins

  @TCZT-89618
  Scenario: User Updates a Time based Project Rule for a User to remove time based access check
    Given rule A for user on project is available with time starting after 0 H 0 M and Ending after 0 H and 9 M
    When user removes time based access for rule A
    Then rule A is Active

  @TCZT-89619
  Scenario: User Updates a Location Based Project Rule for a User to remove location based access check
    Given rule A for user on project is available with countries Pakistan,China,Brazil
    When user removes location based access for rule A
    Then location access is removed for rule A

  @TCZT-89622
  Scenario: User Updates a Time Based Project Rule for a User and extends the end time
    Given rule A for user on project is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 0 M and Ending after 0 H and 10 M
    And rule A is Active

  @TCZT-89623
  Scenario: User Updates a Time Based Project Rule for a User and moves the start time ahead of current time
    Given rule A for user on project is available with time starting after 0 H 0 M and Ending after 0 H and 10 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 5 M and Ending after 0 H and 10 M
    And rule A is Impending after 2 mins

  @TCZT-89624
  Scenario: User Updates a Location Based Project Rule for a User to a new Location
    Given rule A for user on project is available with countries Pakistan,China,Brazil
    When user verifies that rule A is added for China,Pakistan,Brazil
    And user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89625
  Scenario: User Updates a Standard Service Rule for a Access Group and Changes Rule Name
    Given rule A for accessgroup on service is available
    When User updates name for rule A
    Then Name for rule A should be updated

  @TCZT-89626
  Scenario: User Updates a Standard Service Rule for a Access Group and Changes Rule Name to a preexisting Rule's name
    Given rule A for accessgroup on service is available
    When rule B for accessgroup on service is available
    Then User updates name for rule A with name of rule B
    And Duplicate Rule Conflict Error is shown

  @TCZT-89627
  Scenario: User Updates a Standard Service Rule for a Access Group to include Location Based Access
    Given rule A for accessgroup on service is available
    When user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89628
  Scenario: User Updates a Standard Service Rule for a Access Group to include Time Based Access
    Given rule A for accessgroup on service is available
    When user updates time based access for rule A and now rule is accessible starting after 0 H 3 M and Ending after 0 H and 9 M
    When rule A is Impending after 2 mins

  @TCZT-89630
  Scenario: User Updates a Time based Service Rule for a Access Group to remove time based access check
    Given rule A for accessgroup on service is available with time starting after 0 H 0 M and Ending after 0 H and 9 M
    When user removes time based access for rule A
    Then rule A is Active

  @TCZT-89631
  Scenario: User Updates a Location Based Service Rule for a Access Group to remove location based access check
    Given rule A for accessgroup on service is available with countries Pakistan,China,Brazil
    When user removes location based access for rule A
    Then location access is removed for rule A

  @TCZT-89634
  Scenario: User Updates a Time Based Service Rule for a Access Group and extends the end time
    Given rule A for accessgroup on service is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 0 M and Ending after 0 H and 10 M
    And rule A is Active

  @TCZT-89635
  Scenario: User Updates a Time Based Service Rule for a Access Group and moves the start time ahead of current time
    Given rule A for accessgroup on service is available with time starting after 0 H 0 M and Ending after 0 H and 10 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 5 M and Ending after 0 H and 10 M
    And rule A is Impending after 2 mins

  @TCZT-89636
  Scenario: User Updates a Location Based Service Rule for a Access Group to a new Location
    Given rule A for accessgroup on service is available with countries Pakistan,China,Brazil
    When user verifies that rule A is added for China,Pakistan,Brazil
    And user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89637
  Scenario: User Updates a Standard Project Rule for a Access Group and Changes Rule Name
    Given rule A for accessgroup on project is available
    When User updates name for rule A
    Then Name for rule A should be updated

  @TCZT-89638
  Scenario: User Updates a Standard Project Rule for a Access Group and Changes Rule Name to a preexisting Rule's name
    Given rule A for accessgroup on project is available
    When rule B for accessgroup on project is available
    Then User updates name for rule A with name of rule B
    And Duplicate Rule Conflict Error is shown

  @TCZT-89639
  Scenario: User Updates a Standard Project Rule for a Access Group to include Location Based Access
    Given rule A for accessgroup on project is available
    When user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan

  @TCZT-89640
  Scenario: User Updates a Standard Project Rule for a Access Group to include Time Based Access
    Given rule A for accessgroup on project is available
    When user updates time based access for rule A and now rule is accessible starting after 0 H 3 M and Ending after 0 H and 9 M
    When rule A is Impending after 2 mins

  @TCZT-89642
  Scenario: User Updates a Time based Project Rule for a Access Group to remove time based access check
    Given rule A for accessgroup on project is available with time starting after 0 H 0 M and Ending after 0 H and 9 M
    When user removes time based access for rule A
    Then rule A is Active

  @TCZT-89643
  Scenario: User Updates a Location Based Project Rule for a Access Group to remove location based access check
    Given rule A for accessgroup on project is available with countries Pakistan,China,Brazil
    When user removes location based access for rule A
    Then location access is removed for rule A

  @TCZT-89646
  Scenario: User Updates a Time Based Project Rule for a Access Group and extends the end time
    Given rule A for accessgroup on project is available with time starting after 0 H 0 M and Ending after 0 H and 5 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 0 M and Ending after 0 H and 10 M
    And rule A is Active

  @TCZT-89647
  Scenario: User Updates a Time Based Project Rule for a Access Group and moves the start time ahead of current time
    Given rule A for accessgroup on project is available with time starting after 0 H 0 M and Ending after 0 H and 10 M
    When rule A is Active
    Then user updates time based access for time based rule A and now rule is accessible starting after 0 H 5 M and Ending after 0 H and 10 M
    And rule A is Impending after 2 mins

  @TCZT-89648
  Scenario: User Updates a Location Based Project Rule for a Access Group to a new Location
    Given rule A for accessgroup on project is available with countries Pakistan,China,Brazil
    When user verifies that rule A is added for China,Pakistan,Brazil
    And user updates location access for rule A and now rule is accessible in China,Japan
    Then user verifies that rule A is added for China,Japan



