Feature: Add Private Web App
  As a user I would like to test all Scenarios of Adding Private Web App

  Background: User is logged in as Tenant
    Given User is logged in as tenant


  @TCZT-1399
  @smoke
  Scenario Outline: User Adds Agentbased HTTP/HTTPS Service
    Given Connector A is active
    When User adds <protocol> webApp called A with Connector A
    Then run_as_agentless column in resource_service table should be false for Service A
    And is_url_hidden should be set to true for Service A
    Examples:
      | protocol |
      | HTTP     |
      | HTTPS    |

  @TCZT-1400
  Scenario Outline: User Adds Agentbased HTTP/HTTPS Service with Unhidden URL
    Given Connector A is active
    When User adds unhidden <protocol> webApp called A with Connector A
    Then run_as_agentless column in resource_service table should be false for Service A
    And is_url_hidden should be set to false for Service A
    Examples:
      | protocol |
      | HTTP     |
      | HTTPS    |


  @TCZT-1401
  Scenario Outline: User Verifies Test Connectivity for Accessible Service
    Given Connector A is active
    When User tests connectivity for <protocol> Service with Connector A
    Then Service connectivity should pass
    Examples:
      | protocol |
      | HTTP     |
      | HTTPS    |
      | RDP      |
      | VNC      |
      | SSH      |

  @TCZT-1402
  Scenario Outline: User Verifies Test Connectivity for Inaccessible Service
    Given Connector A is active
    When User tests connectivity for <protocol> Service with address <address> and Connector A
    Then Service connectivity should fail
    Examples:
    Examples:
      | protocol | address     |
      | HTTP     | 10.0.0.0:95 |
      | HTTPS    | 10.0.0.0:95 |
      | RDP      | 10.0.0.0:95 |
      | VNC      | 10.0.0.0:95 |
      | SSH      | 10.0.0.0:95 |