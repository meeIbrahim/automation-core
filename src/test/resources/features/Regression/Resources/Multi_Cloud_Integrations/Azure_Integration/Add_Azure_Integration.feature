Feature: Add Azure Integration
  As a user I would like to test all scenarios of Adding Azure Integration

  @scenario-2
  Scenario: Verify that user is able to add Azure Integration
    When User configures Azure integration A
    Then Azure integration A should be added

  @scenario-3
  Scenario: Verify that user is unable to add Azure Integration if integration with same credentials is added in the same account of the same workspace
    Given azure-integration A is active
    When User fails to configure Azure integration A

  @scenario-5
  Scenario: Verify that user is able to add multiple Azure Integrations with different credentials in the same account of the same workspace
    Given azure-integration A is active
    When User configures Azure integration B
    Then Azure integration B should be added

  @post-scenario
  Scenario: Verify that Azure Integration is removed
    Given azure-integration A is active
    When user tries to delete Azure integration A
    Then Azure integration A should be removed

    ## this test case is not eligible anymore
  @scenario-4
  Scenario: Verify that user is unable to add Azure Integration if integration with same credentials is added in another account of the same workspace

  @scenario-6
  Scenario: Verify that user is able to add multiple Azure Integrations with different credentials in different accounts of the same workspace
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant B
    When User configures Azure integration A
    Then Azure integration A should be added

  @post-scenario
  Scenario: Verify that Azure Integration is removed
    Given azure-integration A is active
    When user tries to delete Azure integration A
    Then Azure integration A should be removed
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant A

  #implicity verified in each scenario
  @scenario-6
  Scenario: Verify that Resource Groups and VNETs are fetched for the newly added Azure Integration