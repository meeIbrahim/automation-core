Feature: Remove AWS Integration
  As a user I would like to test all scenarios of Removing AWS Integration

  Scenario: Verify that AWS Integration is removed
    Given aws-integration A is available
    When user tries to delete AWS integration A
    Then AWS integration A should be removed
