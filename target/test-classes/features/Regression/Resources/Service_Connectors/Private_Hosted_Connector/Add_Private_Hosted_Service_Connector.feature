Feature: Add Private Hosted Service Connector
  As a user I would like to test all Scenarios of Adding Private Hosted Service Connector

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1539
  @smoke
  Scenario: Verify that Service Connector is added
    Given site A is active
    When User configures service connector A with site A
    And User installs service connector on vm
    Then service connector A is enabled
    And Service Connector A is attached to Site A


  @TCZT-1540
  Scenario: Verify that multiple Service Connectors can be added with same Relay Node and Site
    Given site A is active
    When Connector A is attached to site A
    And Connector B is attached to site A
    Then service connector A is enabled
    And service connector B is enabled
#    And resources_host table should contain the recently added Service Connector entry



  @TCZT-1542
  Scenario: Verify that multiple Service Connectors can be added with same Relay Node and different Sites
    Given Site A is attached to Relay A
    Given Site B is attached to Relay A
    When Connector A is attached to site A
    And Connector B is attached to site B
    Then service connector A is enabled
    And service connector B is enabled
#    And resources_host table should contain the recently added Service Connector entry


  @TCZT-1543
  Scenario: Verify the working of a Service Connector stuck in In progress state
    Given Connector A is active
    When User changes state of service connector A to IN_PROGRESS
    And State of service connector A is IN_PROGRESS
    Then User verifies In-Progress state of connector A
    And User changes state of service connector A to ENABLED
#    And resources_host table should contain the updated Service Connector state

#  Private Hosted cannot be in Deployment Failed
#  Scenario : Verify the working of a Service Connector in 'Deployment Failed' state
#    Given Connector A is active
#    When User changes state of service connector A to DEPLOYMENT_FAILED
#    And State of service connector A is IN_PROGRESS
#    And resources_host table should contain the updated Service Connector state

  @TCZT-1545
  Scenario: Verify the working if Service and Service Connector are hosted on the same VM
    Given Connector A is active
    When user adds connector A as ssh service A
    Then User should see that service A is active



  @TCZT-1546
  Scenario: Verify the working if user tries to configure a Service Connector on a VM that already has a running host on it
    Given Connector A is active
    When User installs connector agent on connector A
    And service connector is not added


