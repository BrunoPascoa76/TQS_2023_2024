Feature: Flight Reservation
    Scenario: Purchase a Flight
        Given a webpage open on the homepage
        When I select a flight from "Philadelphia" to "Rome"
        And I select a flight
        And I put "Bruno" as my name
        And I put "14641 SW 104th St" as my address 
        And I put "Miami" as my city
        And I put "Florida" as my state
        And I put "33186" as my zip code
        And I put "American Express", "12345" and "Foobar" as my card's details
        And I purchase a flight
        Then I see a page that says "Thank you for your purchase today!"