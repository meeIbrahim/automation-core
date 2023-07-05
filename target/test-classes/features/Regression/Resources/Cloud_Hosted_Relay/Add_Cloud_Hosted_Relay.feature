Feature: Add Cloud Hosted Relay
  As a user I would like to test all Scenarios of Adding Cloud Hosted Relay

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1533
    @smoke
  Scenario: Verify that Cloud Hosted Relay is added
    Given User is on Cloud Relay Nodes page
    When user adds Cloud Relay Node
    And user remembers Cloud Hosted Relay name
#    And resources_relaynode table should contain the recently added relay entry

    ## QA's Need to update this test case
  @TCZT-1534
  Scenario: Verify the working of a Relay Node stuck in In Progress state
    Given relay A is available
    And user remembers id for Cloud Hosted Relay A
    When user changes status to IN_PROGRESS for Relay A
    Then user verifies IN_PROGRESS state for Cloud Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id

    
  @TCZT-1535
  Scenario: Verify the working of a Relay Node stuck in Deployment In Progress state
    Given relay A is available
    And user remembers IP for Cloud Hosted Relay A
    And user stops relayagent service for Relay A
    And user changes status to DEPLOYMENT_IN_PROGRESS for Relay A
    Then user verifies DEPLOYMENT_IN_PROGRESS state for Cloud Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id

  @TCZT-1536
  Scenario: Verify the working of a Relay Node in Deployment Failed state
    Given relay A is available
    And user remembers IP for Cloud Hosted Relay A
    And user stops relayagent service for Relay A
    And user changes status to DEPLOYMENT_FAILED for Relay A
    Then user verifies Deployment Failed state for Cloud Relay A
#    And resources_relaynode table should not contain entry for the removed Relay Node id