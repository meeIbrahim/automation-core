Feature: Remove HA Cloud Relay
  As a user I would like to test all Scenarios of Removing HA Cloud Hosted Relay

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-3575
  Scenario: Verify that the HA Cloud Relay is removed successfully
    Given HA Relay A is available
    And user remembers id for Cloud Hosted Relay A
    Then user removes Cloud Hosted Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id
