Feature: Disable HA for Cloud Hosted Relay
  As a user I would like to test all Scenarios of Disabling HA for Cloud Hosted Relay

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-3571
  Scenario: Verify HA is disabled for Cloud Relay
    Given HA Relay A is available
    And User remembers name for Relay A
    And user remembers id for Cloud Hosted Relay A
#    And is_ha should be true in resources_relaynode table
    Then user disables HA for relay
#    And is_ha should be false in resources_relaynode table
#
  @TCZT-3574
  Scenario: Verify that connected Service Connector and Services are not affected by disabling HA for Cloud Relay
    Given HA Relay A is active
    And User remembers name for Relay A
    Then user disables HA for relay
    And connector A is attached to relay A
    Then User adds ssh service called A for Connector A
    #Add service access verification here also.


  @TCZT-3572
  Scenario: Verify that HA is enabled again for disabled Cloud Relay
    Given HA Relay A is active
    And User remembers name for Relay A
    Then user disables HA for relay
    When user enables HA for relay
    Then Relay A should be HA enabled

