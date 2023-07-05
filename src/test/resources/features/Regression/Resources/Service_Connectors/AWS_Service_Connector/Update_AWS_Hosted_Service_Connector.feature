Feature: Update AWS Hosted Service Connector
  As a user I would like to test all Scenarios of updating AWS Hosted Service Connector

  @scenario-1
  Scenario: Verify that Connector Name is updated
    Given AWS connector A is active
    When User updates Connector A name
    Then Connector A should be updated



