Feature: Add GCP Hosted Service Connector
  As a user I would like to test all Scenarios of Adding GCP Hosted Service Connector

    @scenario-1
  Scenario: Verify that Service Connector is added
      Given Relay A is active
      Given gcp-integration A is active
      When user adds gcp site A with relay A and integration A
      Then Site A should be added
      When User configures gcp service connector A with site A
      Then service connector A is enabled

  @scenario-2
  Scenario: Verify that multiple Service Connectors can be added with same Relay Node and Site
    Given GCP Site A is active
    When User configures gcp service connector A with site A
    Then service connector A is enabled

  @scenario-4
  Scenario: Verify that multiple Service Connectors can be added with same Relay Node and different Sites
    Given Relay A is active
    Given gcp-integration A is active
    When user adds gcp site A with relay A and integration A
    Then Site A should be added
    When User configures gcp service connector A with site A
    Then service connector A is enabled

  @scenario-5
  Scenario: Verify the working of a Service Connector stuck in In progress state
    Given GCP connector A is active
    When User changes state of service connector A to IN_PROGRESS
    And State of service connector A is IN_PROGRESS
    Then User verifies In-Progress state of connector A
    And User changes state of service connector A to ENABLED

  @scenario-6
  Scenario: Verify the working of a Service Connector stuck in 'Deployment in progress' state
    Given GCP connector A is active
    When User changes state of service connector A to DEPLOYMENT_IN_PROGRESS
    And State of service connector A is IN_PROGRESS
    Then User verifies In-Progress state of connector A
    And User changes state of service connector A to ENABLED

  @scenario-7
  Scenario: Verify the working of a Service Connector in 'Deployment Failed' state
    Given GCP connector A is active
    When User changes state of service connector A to DEPLOYMENT_FAILED
    And State of service connector A is DEPLOYMENT_FAILED
    Then User verifies Deployment-failure state of connector A
    And User changes state of service connector A to ENABLED

#  @scenario-8
#  Scenario Outline: Verify the working if Cloud LB Service and Service Connector are hosted in the same region
#    Given user is on http_https page
#    When user adds cloud LB service using <parameters>
#    Examples:
#      | parameters                          |
#      | AnyCloud Web App Service Parameters |