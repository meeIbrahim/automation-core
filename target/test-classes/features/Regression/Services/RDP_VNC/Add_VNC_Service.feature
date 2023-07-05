Feature: Add VNC Service
  As a user I would like to test all Scenarios of Adding VNC Service


  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1451
  @smoke
  Scenario: User Adds Agentbased VNC Service
    Given Connector A is active
    When User adds VNC A with Connector A
    Then run_as_agentless column in resource_service table should be false for Service A

#  @removed
#  Scenario Outline: User Adds Agentless VNC Service with Transparent Authentication
#    Given user is on rdp_vnc page
#    When user adds rdp_vnc service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be false
#    Examples:
#      | parameters                 |
#      | Add VNC agentbased Service |

#  @removed
#  Scenario Outline: User Adds Agentless VNC Service with Managed Authentication
#    Given user is on rdp_vnc page
#    When user adds rdp_vnc service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be false
#    Examples:
#      | parameters                 |
#      | Add VNC agentbased Service |