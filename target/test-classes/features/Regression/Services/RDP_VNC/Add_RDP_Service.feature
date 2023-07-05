Feature: Add RDP Service
  As a user I would like to test all Scenarios of Adding RDP Service

  Background: User is logged in as Tenant
    Given User is logged in as tenant



  @TCZT-1428
  @smoke
  Scenario: User Adds Agentbased RDP Service
    Given Connector A is active
    When User adds RDP A with Connector A
    Then run_as_agentless column in resource_service table should be false for Service A


#  @removed
#  Scenario Outline: User Adds Agentless RDP Service with Transparent Authentication
#    Given user is on rdp_vnc page
#    When user adds rdp_vnc service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be TRANSPARENT
#    Examples:
#      | parameters                |
#      | Add RDP agentless Service |

#  @removed
#  Scenario Outline: User Adds Agentless RDP Service with Managed Authentication
#    Given user is on rdp_vnc page
#    When user adds rdp_vnc service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be MANAGED
#    Examples:
#      | parameters                |
#      | Add RDP agentless Service |