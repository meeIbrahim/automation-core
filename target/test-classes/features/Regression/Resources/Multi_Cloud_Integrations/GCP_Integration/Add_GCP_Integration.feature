Feature: Add GCP Integration
  As a user I would like to test all scenarios of Adding GCP Integration

  @scenario-2
  Scenario: Verify that user is able to add GCP Integration
    When User configures GCP integration A
    Then GCP integration A should be added

  @scenario-3
  Scenario: Verify that user is unable to add GCP Integration if integration with same credentials is added in the same account of the same workspace
    Given gcp-integration A is active
    When User fails to configure Gcp integration A

  @scenario-5
  Scenario: Verify that user is able to add multiple GCP Integrations with different credentials in the same account of the same workspace
    Given gcp-integration A is active
    When User configures GCP integration B
    Then GCP integration B should be added

  @pre-scenario
  Scenario: Verify that GCP Integration is removed
    Given gcp-integration A is active
    When user tries to delete GCP integration A
    Then GCP integration A should be removed

  @scenario-6
  Scenario: Verify that user is able to add multiple GCP Integrations with different credentials in different accounts of the same workspace
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant B
    When User configures GCP integration A
    Then GCP integration A should be added

  @post-scenario
  Scenario: Verify that GCP Integration is removed
    Given gcp-integration A is active
    When user tries to delete GCP integration A
    Then GCP integration A should be removed
    When user tries to logout
    Then user should see that logout is successful
    Given User is logged in as tenant A

     ## this test case is not eligible anymore
  @scenario-4
  Scenario: Verify that user is unable to add GCP Integration if integration with same credentials is added in another account of the same workspace

  #implicity verified in each scenario
  @scenario-7
  Scenario: Verify that Regions and VPCs are fetched for the newly added GCP Integration
