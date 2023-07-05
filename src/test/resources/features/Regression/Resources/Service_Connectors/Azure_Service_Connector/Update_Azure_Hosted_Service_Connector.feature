Feature: Update Azure Hosted Service Connector
  As a user I would like to test all Scenarios of updating Azure Hosted Service Connector

  @scenario-1
  Scenario: Verify that Connector Name is updated
    Given Azure connector A is active
    When User updates Connector A name
    Then Connector A should be updated



