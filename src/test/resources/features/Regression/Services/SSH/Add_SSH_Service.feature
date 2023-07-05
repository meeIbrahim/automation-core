Feature: Add SSH Service
  As a user I would like to test all Scenarios of Adding SSH Service

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1474
  @smoke
  Scenario: User Adds Agentbased SSH Service
    Given Connector A is active
    When User adds SSH A with Connector A
    Then run_as_agentless column in resource_service table should be false for Service A


#    @removed
#  Scenario Outline: User adds Agentless SSH Service with Transparent Authentication
#    Given user is on ssh page
#    When user adds ssh service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be TRANSPARENT
#    Examples:
#      | parameters                |
#      | Add SSH agentless Service |

#    @removed
#  Scenario Outline: User adds Agentless SSH Service with Managed Authentication: Username + Password
#    Given user is on ssh page
#    When user adds ssh service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be MANAGED
#    Examples:
#      | parameters                |
#      | Add SSH agentless Service |

#    @removed
#  Scenario Outline: User adds Agentless SSH Service with Managed Authentication: Username + Private Key
#    Given user is on ssh page
#    When user adds ssh service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be MANAGED
#    Examples:
#      | parameters                |
#      | Add SSH agentless Service |

#    @removed
#  Scenario Outline: User adds Agentless SSH Service with Managed Authentication: Username + Private Key + Passphrase
#    Given user is on ssh page
#    When user adds ssh service using <parameters>
#    Then user should see that service is added
#    And service is added to resource_service table
#    And run_as_agentless column in resource_service table should be true
#    And authentication_method in db should be MANAGED
#    Examples:
#      | parameters                |
#      | Add SSH agentless Service |

#  implicity verified
#  Scenario: User Verifies Test Connectivity when Service is accessible
