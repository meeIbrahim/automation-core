#Feature: Add Private Saas Service
#  As a user I would like to test all Scenarios of Adding Private Saas
#
#  @scenario-1
#  Scenario Outline: Users adds JIRA Service as SaaS
#    Given user is on http_https page
#    When user adds private saas service using <parameters>
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table is updated to be false
#    And service type is correct
#    And resources_agentlessservicesubdomains table should contain the entry
#    Examples:
#      | parameters               |
#      | Add private saas Service |
#
#  @scenario-2 #implicity verified
#  Scenario: User Verifies Test Connectivity for Accessible Service
#
#  @scenario-3
#  Scenario Outline: User Verifies Test Connectivity for Inaccessible Private SaaS Service
#    Given user is on http_https page
#    When user checks failed test connectivity for private SaaS service using <parameters>
#    Then user should see that service is not added
#    And service is not added to resource_service table
#    Examples:
#      | parameters               |
#      | Add private saas Service |