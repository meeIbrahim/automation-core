Feature: Remove Private Web App
  As a user I would like to test all Scenarios of Removing Private Web App

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1413
  Scenario: User Deletes a Agentbased Service with no Association
    Given webApp A is available
    When User deletes webApp A
    Then webApp A is deleted

  @TCZT-1414
  Scenario: User Tries to Delete a Service with Policy Attached
    Given Policy A is attached to webApp A
    When User tries to delete webApp A
    Then Remove should not be available as an option


  @TCZT-1415
  Scenario: User Tries to Delete a Service with Project Attached
    Given Project A is created with webApp A
    When User tries to delete webApp A
    Then Remove should not be available as an option



  @TCZT-1416
  @TCZT-1417
  Scenario: User Verifies Deletion for Service Stuck in In-Progress Status and Force Deletion
    Given webApp A is available
    Given User changes status of webApp A to DELETE_IN_PROGRESS
    Then User verifies DELETE_IN_PROGRESS state for webApp A
