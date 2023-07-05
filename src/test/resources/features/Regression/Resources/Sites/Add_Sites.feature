Feature: Add Sites
  As a user I would like to test all Scenarios of Adding Sites

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1363
  @smoke
  Scenario: Verify that On Prem Site is added
    Given Relay A is active
    When User adds site A with relay A
#    When With a custom parameter type [1,2,3,7]
    Then Site A should be added

  @scenario-2
  Scenario: Verify that AWS Site is added
    Given Relay A is active
#    Given user adds AWS integration 149049417381
    Given aws-integration A is active
    When user adds aws site A with relay A and integration A
    Then Site A should be added

  @scenario-3
  Scenario: Verify that Azure Site is added
    Given Relay A is active
#    Given user adds Azure integration e893bfba-f36d-482f-bfb8-9a7a7e9e43dd
    Given azureintegration A is active
    When user adds azure site A in resource group zta-qa-auto with relay A and integration A
    Then Site A should be added

  @scenario-4
  Scenario: Verify that GCP Site is added
    Given Relay A is active
#    Given user adds gcp integration ztna-automation-project-1
    Given gcpintegration A is active
    When user adds gcp site A with relay A and integration A
    Then Site A should be added


  @scenario-5
  Scenario: Verify if multiple Sites are created with the same Relay Node
    Given Relay A is active
    When User adds site A with relay A
    Then Site A should be added
    Then User adds site B with relay A
    And Site B should be added

  @scenario-5
  Scenario: Verify if multiple aws Sites are created with the same Relay Node
    Given Relay A is active
    Given aws-integration A is active
    When user adds aws site A in region eu-central-1 with relay A and integration A
    Then Site A should be added
    When user adds aws site B in region us-east-1 with relay A and integration A
    Then Site B should be added

  @pre-scenario-5
  Scenario: Verify that Azure Site is removed
    Given Azure Site A is active
    Then user removes site A
    Then Site A should be removed

  @scenario-5
  Scenario: Verify if multiple azure Sites are created with the same Relay Node
    Given Relay A is active
    Given azureintegration A is active
    When user adds azure site A in resource group zta-qa-auto with relay A and integration A
    Then Site A should be added
    When user adds azure site B in resource group zta-qa-auto with relay A and integration A
    Then Site B should be added

  @pre-scenario-5
  Scenario: Verify that GCP Site is removed
    Given GCP Site A is active
    Then user removes site A
    Then Site A should be removed

  @scenario-5
  Scenario: Verify if multiple gcp Sites are created with the same Relay Node
    Given Relay A is active
    Given gcpintegration A is active
    When user adds gcp site A with relay A and integration A
    Then Site A should be added
    When user adds gcp site B with relay A and integration A
    Then Site B should be added
