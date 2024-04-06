Feature: Authentication
    # Look for V002_Examples.sql for the pre-existing values

    Scenario: Login success
        When I try to login with the username "Bruno" and the password "password"
        Then it returns me a status 200 message

    Scenario: Login failed username
        When I try to login with the username "Rodrigo" and the password "password"
        Then it returns me a status 401 message

    Scenario: Login failed password
        When I try to login with the username "Bruno" and the password "wrongPassword"
        Then it returns a status 401 message
    
    Scenario: Register success
        When I try to register with the username "Rodrigo" and the password "password"
        Then it returns me a status 200 message

    Scenario: Register failed
        When I try to register with the username "Bruno" and the password "password"
        Then it returns me a status 409 message

    Scenario: Logout
        When I try to Logout
        Then it returns me a status 200 message