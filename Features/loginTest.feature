#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Verify Avanza actions
  Verify Avanza actions

  @tag1
  Scenario: Verification of Avanza actions
    Given Open the Firefox and launch the application
#    And some other precondition
#    When Enter the Username and Password and login
    When Enter the personal number and login
    And Check name on details page
    And Fetch pending orders
    And Fetch account balance
    And Buy two OMX index instruments for half current price    
#    And yet another action
    And Check order for two pieces of an OMX minifuture has been placed
    And Sell two OMX index instruments
    And Cancel all pending orders
    Then Check name on details page
#    And check more outcomes

#  @tag2
#  Scenario Outline: Title of your scenario outline
#    Given I want to write a step with <name>
#    When I check for the <value> in step
#    Then I verify the <status> in step

#    Examples: 
#      | name  | value | status  |
#      | name1 |     5 | success |
#      | name2 |     7 | Fail    |
