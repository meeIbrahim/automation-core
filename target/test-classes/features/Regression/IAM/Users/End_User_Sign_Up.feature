Feature: End User Sign Up
  As a user I would like to signup end users.

  Background: User is logged in as Tenant
    Given User is logged in as tenant

  @TCZT-1312
  @smoke
  Scenario: Verify End User can Sign Up Locally using Invite Link Sent to Email
    Given User A is invited
    When User A registers
    Then User A is added with Active status
