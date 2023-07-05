Feature: Remove Sites
  As a user I would like to test all Scenarios of Removing Sites

  Scenario: Verify that On Prem Site is removed
    Given On-prem Site A is active
    Then user removes site A
    Then Site A should be removed

  Scenario: Verify that AWS Site is removed
    Given AWS Site A is active
    Then user removes site A
    Then Site A should be removed

  Scenario: Verify that Azure Site is removed
    Given Azure Site A is active
    Then user removes site A
    Then Site A should be removed

  Scenario: Verify that GCP Site is removed
    Given GCP Site A is active
    Then user removes site A
    Then Site A should be removed

  Scenario: Verify Remove an existing Active Relay in a Site when Site is not attached to any Service Connector
    Given relay A is active
    Given relay B is active
    Then User adds site B with relays
      | A | B |
    Then Site B should be added
    Then user removes active relay A from site B
    Then Site B should be added

  Scenario: Verify Remove an existing Down Relay in a Site when Site is not attached to any Service Connector
    Given relay A is active
    Given relay B is active
    Then User adds site B with relays
      | A | B |
    And user remembers IP for Cloud Hosted Relay A
    And user stops relayagent service for Relay A
    Then user removes active relay A from site B
    Then Site B should be added


  Scenario: Verify Remove an existing Relay in a Site when Site is attached to a Service Connector
    Given relay A is active
    Given relay B is active
    Then User adds site B with relays
      | A | B |
    Then Site B should be added
    When User configures service connector A with site B
    And User installs service connector on vm
    Then service connector A is enabled
    And Service Connector A is attached to Site B
    When user removes active relay A from site B
    Then Site B should be added

  Scenario: Verify Remove an existing Active Relay in a Site when Site is attached to a Disabled Service Connector
    Given relay A is active
    Given relay B is active
    Then User adds site B with relays
      | A | B |
    Then Site B should be added
    When User configures service connector A with site B
    And User installs service connector on vm
    Then service connector A is enabled
    And Service Connector A is attached to Site B
    Then user disables connector A
    Then service connector A should be disabled
    When user removes active relay A from site B
    Then Site B should be added

  Scenario: Verify Remove an existing Active Relay in a Site when Site is attached to a Down Service Connector
    Given relay A is active
    Given relay B is active
    Then User adds site B with relays
      | A | B |
    Then Site B should be added
    When User configures service connector A with site B
    And User installs service connector on vm
    Then service connector A is enabled
    And Service Connector A is attached to Site B
    Then User stops agent on Connector A
    When user removes active relay A from site B
    Then Site B should be added
