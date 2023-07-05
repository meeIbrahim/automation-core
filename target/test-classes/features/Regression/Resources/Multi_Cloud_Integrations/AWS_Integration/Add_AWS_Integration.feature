Feature: Add AWS Integration
  As a user I would like to test all scenarios of Adding AWS Integration

  @scenario-2
  Scenario: Verify that user is able to add AWS Integration
    When User configures AWS integration A
    Then AWS integration A should be added

        ## this test case is not eligible anymore
  @scenario-3
  Scenario: Verify that user is unable to add AWS Integration if integration with same credentials is added in another account of the same workspace

  @scenario-4
  Scenario: Verify that user is unable to add AWS Integration if integration with same credentials is added in the same account of the same workspace
    Given aws-integration A is active
    When User fails to configure AWS integration A

  @scenario-5
  Scenario: Verify that user is able to add multiple AWS Integrations with different credentials in the same account of the same workspace
    Given aws-integration A is active
    When User configures AWS integration B
    Then AWS integration B should be added

  @post-scenario
  Scenario: Verify that AWS Integration is removed
    Given aws-integration A is active
    When user tries to delete AWS integration A
    Then AWS integration A should be removed

  @scenario-6
  Scenario: Verify that user is able to add multiple AWS Integrations with different credentials in different accounts of the same workspace
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant B
    When User configures AWS integration A
    Then AWS integration A should be added

  @post-scenario
  Scenario: Verify that AWS Integration is removed again
    Given aws-integration A is active
    When user tries to delete AWS integration A
    Then AWS integration A should be removed
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant A

  #implicity verified in each scenario
  @scenario-7
  Scenario: Verify that Regions and VPCs are fetched for the newly added AWS Integration