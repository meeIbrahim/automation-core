Feature: Remove GCP Integration
  As a user I would like to test all scenarios of Removing GCP Integration

  Scenario: Verify that GCP Integration is removed
    Given gcp-integration A is available
    When user tries to delete GCP integration A
    Then GCP integration A should be removed