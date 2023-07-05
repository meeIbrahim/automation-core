Feature: Revoke/Regenerate access of Private Hosted Service Connector
  As a user I would like to test all Scenarios of Revoke/Regenerate access of Private Hosted Service Connector

  Background: User is logged in as
    Given User is logged in as tenant

  @TCZT-1556
  Scenario: Verify that Service Connector's access is revoked
      Given connector A is available
      When User revokes access of connector A
      Then Access of Connector A should be revoked

  @TCZT-1557
  Scenario: Verify that Service Connector's access is regenerated
    Given Connector A is revoked
    When User regenerates access key for connector A
    Then service connector A should be enabled


  @TCZT-1558
  Scenario: Verify Service Access after Connector's Access is Regenerated
    Given Revoked Connector A that is attached to service A
    When User regenerates access key for connector A
    And service connector A is enabled
    Then User should see that service A is active




