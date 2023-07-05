Feature: ZT Disable Private Web App
  As a user I would like to test all Scenarios of ZT Disabling Private Web App

  Scenario: User ZT Disables Service
    Given webApp A is active
    When User disables webApp A
    Then WebApp A should be disabled


  Scenario: User ZT Disables Service with Policy Attached
    Given Policy A is attached to webApp A
    When User tries to disable webApp A
    Then Disable should not be available as an option


  Scenario: User ZT Disables Service with Project Attached
    Given Project A is created with webApp A
    When User tries to disable webApp A
    Then Disable should not be available as an option
