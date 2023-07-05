Feature: Update GCP Integration
  As a user I would like to test all scenarios of Updating GCP Integration

  Scenario: Verify that user is able to update the Integration Name
    Given gcp-integration A is active
    When user tries to edit GCP integration A name
    Then GCP integration A name should be updated