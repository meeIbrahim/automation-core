Feature: Add Policy
  As a user I would like to test all Scenarios of Adding Policy

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1933
  @smoke
  Scenario: User Adds a Policy for a Service
    Given webApp A is active
    When User adds Policy A for service A
    Then Policy A is added and is active

  @TCZT-1934
  Scenario: User Adds a Policy for an Empty Project
    Given Empty Project A is available
    When User adds Policy A for Project A
    Then Policy A is added and is active

  @TCZT-1935
  Scenario: User Adds a Policy for a Project with Services
    Given Project A is available with Services
    When User adds Policy A for Project A
    Then Policy A is added and is active
