Feature: Update Sites
  As a user I would like to test all Scenarios of Updating Sites

  Scenario: User updates an On-Prem site
    Given On-prem Site A is active
    Then User updates Site A name
    Then Site A should be updated

  Scenario: User updates an AWS site
    Given AWS Site A is active
    Then User updates Site A name
    Then Site A should be updated
#    When user updates an aws site using <parameters>


  Scenario: User updates an Azure site
    Given Azure Site A is active
    Then User updates Site A name
    Then Site A should be updated
#    When user updates an azure site using <parameters>


  Scenario: User edits a GCP site
    Given GCP Site A is active
    Then User updates Site A name
    Then Site A should be updated
#    When user updates a gcp site using <parameters>
