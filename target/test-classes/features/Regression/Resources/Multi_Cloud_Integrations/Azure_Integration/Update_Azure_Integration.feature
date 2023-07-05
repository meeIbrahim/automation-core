Feature: Update Azure Integration
  As a user I would like to test all scenarios of Updating Azure Integration

  Scenario: Verify that user is able to update the Integration Name
    Given azure-integration A is active
    When user tries to edit Azure integration A name
    Then Azure integration A name should be updated