Feature: Remove Cloud Hosted Relay
  As a user I would like to test all Scenarios of Removing Cloud Hosted Relay

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1537
  Scenario: Verify that Cloud Hosted Relay is removed
    Given relay A is available
    And user remembers id for Cloud Hosted Relay A
    Then user removes Cloud Hosted Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id


  @TCZT-1538
  Scenario: Verify the working of a Relay Node stuck in Delete In Progress state
    Given relay A is available
    And user remembers id for Cloud Hosted Relay A
    And user changes state to DELETE_IN_PROGRESS for Relay A
    Then User verifies DELETE_IN_PROGRESS state for Cloud Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id
