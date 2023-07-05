Feature: Update Project
  As a user I would like to test all update project scenarios

  @scenario-1
  Scenario: User Updates Project Name
    When User creates empty Project A
    Then Project A should be added
    Then User updates Project A name and description
    Then Project A should be updated
#    When user updates projects using <parameters>
#    Then user should see that project is updated
#    And Project updated in resources_project
#    Examples:
#      | parameters                |
#      | Update Project Parameters |

  @scenario-2
  Scenario: User Adds Services to Project
    Given Connector A is active
    Then User adds HTTP webApp called A with Connector A
    Then User creates empty Project A
    Then Project A should be added
    When User adds Application A to Application Group A
    Then Project A should be added
#    When User adds Application A to Application Group A
#    Given user is on projects page
#    When user updates projects using <parameters>
#    Then user should see that project is updated
#    And Project updated in resources_project
#    And Entries updated in resources_projectservice
#    Examples:
#      | parameters                |
#      | Update Project Parameters |

  @scenario-3
  Scenario: User Removes Services from Project
    Given Connector A is active
    Then User adds HTTP webApp called A with Connector A
    When User creates Project A with service A
    Then Project A should be added
    When User removes Application A from Application Group A
    Then Project A should be added
#    When user updates projects using <parameters>
#    Then user should see that project is updated
#    And Project updated in resources_project
#    And Entries updated in resources_projectservice
#    Examples:
#      | parameters                |
#      | Update Project Parameters |

  @scenario-4
  Scenario: Users Add Services to Project with Policy Attached
    When User creates empty Project A
    Then Project A should be added
    When User adds Policy A for Project A
    Then Policy A is added and is active
    When User adds Rule A for Policy A
    Then Rule A should be added
    When User removes Application A from Application Group A
    Then Project A should be added
#    Given user is on projects page
#    When user updates projects using <parameters>
#    Then user should see that project is updated
#    And Project updated in resources_project
#    And Entries updated in resources_projectservice
#    Examples:
#      | parameters                |
#      | Update Project Parameters |

  @scenario-5
  Scenario: User Remove Services to Project with Policy Attached
    When User creates empty Project A
    Then Project A should be added
    When User adds Policy A for Project A
    Then Policy A is added and is active
    When User adds Rule A for Policy A
    Then Rule A should be added
    When User adds Application A to Application Group A
    Then Project A should be added
#    When user updates projects using <parameters>
#    Then user should see that project is updated
#    And Project updated in resources_project
#    And Entries updated in resources_projectservice
#    Examples:
#      | parameters                |
#      | Update Project Parameters |

