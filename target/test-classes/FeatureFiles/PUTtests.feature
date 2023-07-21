@PUT
Feature: GoRest Automation for PUT request

Scenario: PUT request to update email for existing user
Given A new user is created
And headers and body is given with updated email value  
When I use PUTT request on "/public/v1/users/"
Then API should return body with updated email 