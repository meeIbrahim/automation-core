Feature: Add HA for Cloud Hosted Relay
  As a user I would like to test all Scenarios of Adding HA for Cloud Hosted Relay

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-3539
    @smoke
  Scenario: Verify Cloud relay is added with HA enabled
    Given User is on Cloud Relay Nodes page
    When User adds HA enabled Cloud Hosted Relay called A
#    And User remembers name for Relay A
#    And user remembers id for Cloud Hosted Relay A
    Then Relay A should be HA enabled
#    And resources_relaynode table should contain the recently added relay entry
#    And is_ha should be true in resources_relaynode table

  @TCZT-3541
  Scenario: Verify successful attachment of Service Connector with HA Relay
    Given Site A is attached to HA Relay A
    And user remembers id for Cloud Hosted Relay A
    When User configures service connector A with site A
    And User installs service connector on vm
    Then service connector A is enabled
    And service connector A is attached to Relay A
#    And resources_host table should contain Service Connector A
#    And resources_host table should contain the corresponding relay_node_id for Connector A

  @TCZT-3542
  Scenario: Verify that services are successfully added for SC with HA Relay
    Given Connector A is attached to HA Relay A
    When User adds ssh service called A for Connector A
    And user is on policies page
    When User adds policy A for Service A
    And User adds rule A in policy A for any user
    #Add service access verification here also.
