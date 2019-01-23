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
Feature: Buy instruments on Avanza
  Buy instruments on Avanza

  @tag1
  Scenario: Buy instruments on Avanza
    Given Open the Firefox and launch the application
#    get pending orders of short instruments for underlying
    When Get pending orders 
#		cancel listed pending orders
    When Cancel listed pending orders
#		get holdings for underlying
    When Get short holdings for underlying
#		sell short holdings for underlying
    When Sell short holdings for underlying
#		get value of long instruments for underlying
    When Get value of long instruments for underlying
#		get remaining finance ceiling for underlying
    When Get remaining finance ceiling for underlying
#		calculate available amount, if less than ceiling then update ceiling
#		calculate amount of instruments to buy
#		buy long instruments
    Then Buy Instruments