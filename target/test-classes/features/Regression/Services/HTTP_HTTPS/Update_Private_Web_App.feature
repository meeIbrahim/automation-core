Feature: Update Private Web App
  As a user I would like to test all Scenarios of Updating Private Web App

  @TCZT-1405
  Scenario: User changes Service name
    Given webApp A is active
    When User updates webApp A
    Then WebApp A should be updated


  @TCZT-1406
  Scenario: User Tries to Update Service attached to a Policy
    Given Policy A is attached to webApp A
    When User updates webApp A
    Then WebApp A should be updated




  @TCZT-1407
  Scenario: User Tries to Update Service attached to a Project
    Given Project A is created with webApp A
    When User updates webApp A
    Then WebApp A should be updated



#  @scenario-7 #implicity verified
#  Scenario: User Verifies Test Connectivity when Service is Accessible

