Feature: Using the web browser
    Background:
        Given a working web browser on the homepage
        And a valid user session

    Scenario: loginSuccessful
        When I go to "Login"
        And  I type "Bruno" on "username"
        And I type "password" on "password"
        And I click on the submit button
        Then I am sent to the homepage
    
    Scenario: loginWrongUsername
        When I go to "Login"
        And I type "something" on "username"
        And I type "password" on "password"
        And I click on the submit button
        Then an alert saying "Login failed" will appear

    Scenario: loginWrongPassword
        When I go to "Login"
        And I type "Bruno" on "username"
        And I type "something" on "password"
        And I click on the submit button
        Then an alert saying "Login failed" will appear

    Scenario: registerSuccessful
        When I go to "Register"
        And I type "Rodrigo" on "username"
        And I type "password" on "password"
        And I click on the submit button
        Then I am sent to the homepage

    Scenario: bookingSuccessful
        When I select option "1" on "fromLocation"
        And I select option "2" on "toLocation"
        And I type "2024-04-06" on "tripDate"
        And I click on "searchBtn"
        And I type "12" on "seat1"
        And I click on "btn1"
        Then an alert saying "sceduling successful" will appear

    Scenario: bookingSeatOccupied
        When I select option "1" on "fromLocation"
        And I select option "2" on "toLocation"
        And I type "2024-04-06" on "tripDate"
        And I click on "searchBtn"
        And I type "11" on "seat1"
        And I click on "btn1"
        Then an alert saying "sceduling failed" will appear
    
