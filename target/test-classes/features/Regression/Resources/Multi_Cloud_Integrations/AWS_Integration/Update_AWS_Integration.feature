Feature: Update AWS Integration
  As a user I would like to test all scenarios of Updating AWS Integration

  Scenario: Verify that user is able to update the Integration Name
    Given aws-integration A is active
    When user tries to edit AWS integration A name
    Then AWS integration A name should be updated

