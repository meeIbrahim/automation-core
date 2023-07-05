Feature: Create Project
  As a user I would like to test all create project scenarios

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1501
  @smoke
  Scenario: User Creates Project with no Services
    When User creates empty Project A
    Then Project A should be added

  @TCZT-1502
  @smoke
  Scenario: User Creates Project with Services Attached
    Given webApp A is available
    When User creates Project A with service A
    Then Project A should be added

