Feature: Update GCP Hosted Service Connector
  As a user I would like to test all Scenarios of updating GCP Hosted Service Connector

  @scenario-1
  Scenario: Verify that Connector Name is updated
    Given GCP connector A is active
    When User updates Connector A name
    Then Connector A should be updated



