Feature: Remove AWS Hosted Service Connector
  As a user I would like to test all Scenarios of removing AWS Hosted Service Connector

  @scenario-1
  Scenario: Verify that Service Connector is removed
    Given AWS connector A is active
    When User removes connector A
    Then Connector A should be removed

  @scenario-2
  Scenario: Verify the working of a Service Connector stuck in Delete in progress state
    Given AWS connector A is active
    And User stops agent on Connector A
    When User changes state of service connector A to DELETE_IN_PROGRESS
    Then User verifies DELETE_IN_PROGRESS state of connector A
    Then User deletes agent on Connector A
