Feature: Remove Azure Integration
  As a user I would like to test all scenarios of Removing Azure Integration

  Scenario: Verify that Azure Integration is removed
    Given azure-integration A is available
    When user tries to delete Azure integration A
    Then Azure integration A should be removed
