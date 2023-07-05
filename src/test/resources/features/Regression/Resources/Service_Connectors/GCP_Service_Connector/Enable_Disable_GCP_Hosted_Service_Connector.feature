Feature: Enable/Disable GCP Hosted Service Connector
  As a user I would like to test all Scenarios of Enabling and Disabling GCP Hosted Service Connector

  @scenario-1
  Scenario: Verify that Disable Connector is working
    Given GCP connector A is active
    When user disables connector A
    Then service connector A should be disabled

  @scenario-2
  Scenario: Verify that Enable Connector is working
    Given Connector A is disabled
    When user enables connector A
    Then service connector A should be enabled

  @scenario-3A
  Scenario: Verify Service Access after Connector is re-enabled
    Given GCP connector A is active
    When User adds HTTPS webApp called A with Connector A
    And Policy A is attached to service A
    And Rule A is attached to Policy A
    When Connector A is disabled
    And user enables connector A
    Then service connector A should be enabled
    And Policy A should be active

  @scenario-3B
  Scenario: Verify Service Access after Connector is re-enabled by attaching new services
    Given GCP connector A is active
    When user disables connector A
    When user enables connector A
    Then service connector A should be enabled
    When User adds HTTPS webApp called A with Connector A
    And Policy A is attached to service A
    And Rule A is attached to Policy A
    Then Rule A should be added
