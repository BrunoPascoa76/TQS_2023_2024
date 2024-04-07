Feature: Booking Trips
    # Look for V002_Examples.sql for the pre-existing values

    Background:
        Given a working server connection

    Scenario: List trips (all)
        When I try to list all trips
        Then I get 3 results
            
    Scenario: List trips (with parameters)
        When I try to list all trips between "Porto" and "Aveiro" on "2024/04/06"
        Then I get 2 results

    Scenario: Choose seat success 
        When I choose the trip with the ID 1
        And I choose seat number 11
        Then it returns me a status 201 message

    Scenario: Choose seat failed (occupied)
        When I choose the trip with the ID 1
        And I choose seat number 41
        Then it returns me a status 409 message

    Scenario: Choose seat failed (out of bounds)
        When I choose the trip with the ID 1
        And I choose seat number 200
        Then it returns me a status 403 message


