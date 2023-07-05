Feature: Create Access Group
  As a user I would like to test all Scenarios of Creating Access Group

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1344
  @smoke
  Scenario: Verify Creation of Access Group with No User
    Given User is on Access Group Page
    When User opens Create Access Group Modal
    And User fills Access Group details
    Then Access Group is Created





