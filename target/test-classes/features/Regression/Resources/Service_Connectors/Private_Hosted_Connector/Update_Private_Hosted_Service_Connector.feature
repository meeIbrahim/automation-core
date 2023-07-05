Feature: Update Private Hosted Service Connector
  As a user I would like to test all Scenarios of updating Private Hosted Service Connector

  Background: User is logged in as
    Given User is logged in as tenant

  @TCZT-1549
  Scenario: Verify that Connector Name is updated
    Given connector A is active
    When User updates Connector A name
    Then Connector A should be updated
#    And resources_host table should contain the recently updated Service Connector entry




