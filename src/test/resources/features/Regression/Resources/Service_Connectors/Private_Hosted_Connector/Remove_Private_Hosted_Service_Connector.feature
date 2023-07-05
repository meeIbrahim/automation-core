Feature: Remove Private Hosted Service Connector
  As a user I would like to test all Scenarios of removing Private Hosted Service Connector

  Background: User is logged in as
    Given User is logged in as tenant

  @TCZT-1559
  Scenario: Verify that Service Connector is removed
    Given connector A is available
    When User removes connector A
    Then Connector A should be removed

  @TCZT-1560
  Scenario: Verify the working of a Service Connector stuck in Delete in progress state
    Given connector A is available
    And User stops agent on Connector A
    When User changes state of service connector A to DELETE_IN_PROGRESS
    Then User verifies DELETE_IN_PROGRESS state of connector A
    Then User deletes agent on Connector A
