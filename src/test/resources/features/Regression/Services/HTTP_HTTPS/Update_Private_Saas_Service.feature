#Feature: Update Private SaaS
#  As a user I would like to test all Scenarios of Updating Private SaaS Service
#
#  @scenario-1
#  Scenario Outline: User Updates Private JIRA running as SaaS when no Policy is attached
#    Given user is on http_https page
#    When user updates Service Name using <parameters>
#    Then service should be updated
#    Examples:
#      | parameters                |
#      | Update Service Parameters |
#
#    @pre-scenario-2
#    Scenario Outline: User Adds a Policy for a Service
#      Given user is on policies page
#      When user add policies using <parameters>
#      Then policies given in parameters should be added
#      Given user is on policies page
#      When user add rules in policy using <parameters>
#      Then rules given in parameters should be added
#      Examples:
#        | parameters             |
#        | Add Policy Parameters  |
#
#  @scenario-2
#  Scenario Outline: User Updates Private JIRA running as SaaS when Policy is attached
#    Given user is on http_https page
#    When user updates Service Name using <parameters>
#    Then service should be updated
#    Examples:
#      | parameters                |
#      | Update Service Parameters |
#
