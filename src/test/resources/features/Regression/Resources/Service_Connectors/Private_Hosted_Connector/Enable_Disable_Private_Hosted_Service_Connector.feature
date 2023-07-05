Feature: Enable/Disable Private Hosted Service Connector
  As a user I would like to test all Scenarios of Enabling and Disabling Private Hosted Service Connector

  Background: User is logged in as
    Given User is logged in as tenant

  @TCZT-1553
  Scenario: Verify that Disable Connector is working
    Given connector A is active
    When user disables connector A
    Then service connector A should be disabled

  @TCZT-1554
  Scenario: Verify that Enable Connector is working
    Given Connector A is disabled
    When user enables connector A
    Then service connector A should be enabled

  @TCZT-1555
  Scenario: Verify Service Access after Connector is re-enabled
    Given service A is attached to connector A
    And Policy A is attached to service A
    And Rule A is attached to Policy A
    When Connector A is disabled
    And user enables connector A
    Then service connector A should be enabled
    And Policy A should be active
#    Given user is on end user portal
#    Then user can access service given in <parameters>
#    Then user opens tenant admin portal


  @TCZT-1555
  Scenario: Verify Service Access after Connector is re-enabled by attaching new services
    Given Connector A is disabled
    When user enables connector A
    And User adds ssh service called A for Connector A
    Then User should see that service A is active
    When User adds policy A for Service A
    And User adds Rule A for Policy A
    Then Rule A should be added
#    Given user is on end user portal
#    Then user can access service given in <parameters>
#    Then user opens tenant admin portal
