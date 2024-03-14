Feature: Basic Arithmetic
    Background: A Calculator
        Given a calculator I just turned on

    Scenario: Addition
        When I add 2 and 3
        Then the result is 5
    
    Scenario: Subtraction
        When I subtract 7 to 5
        Then the result is 2

    Scenario: Multiplication
        When I multiply 5 with 5
        Then the result is 25
    
    Scenario: Division
        When I divide 6 by 2
        Then the result is 3