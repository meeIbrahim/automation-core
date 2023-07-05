Feature: Invite Users
  As a user I would like to invite users

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1301
  @smoke
  Scenario: Verify Users can be Invited using 'Invite Features'
    Given User remembers available access groups
    And User is on users page
    When User opens Invite User Modal
    And User fills emails in invite users modal
    Then User verifies that available access groups are shown in dropdown
    When User saves Invite User Form
    Then Invited Users are added with Pending state