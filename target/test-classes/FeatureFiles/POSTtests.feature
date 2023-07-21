@POST
Feature: GoRest Automation for POST request

Scenario: POST request to create new user data
Given Gorest API is up and running fine
And headers and body is given to the request  
When I use POST request on "public/v1/users"
Then API should create new user and return the success message 

Scenario: POST request with null data passed in body
Given Gorest API is up and running fine
And headers and null body is given to the request  
When I use POST request on "public/v1/users"
Then API should return error messages in response

Scenario: POST request with only valid name passed through body rest is null
Given Gorest API is up and running fine
And headers and valid name with null body is given to the request  
When I use POST request on "public/v1/users"
Then API should return error messages for blank fields in response

Scenario: POST request with only valid email passed through body rest is null
Given Gorest API is up and running fine
And headers and valid email with null body is given to the request  
When I use POST request on "public/v1/users"
Then API should return error messages for blank fields in response

Scenario: POST request with invalid name in the body 
Given Gorest API is up and running fine
And headers with invalid name "1245%$" is passed into the body 
When I use POST request on "public/v1/users"
Then API should return error message for invalid name

Scenario: POST request with invalid email in the body 
Given Gorest API is up and running fine
And headers with invalid email "8712AV34*&" is passed into the body 
When I use POST request on "public/v1/users"
Then API should return error message for invalid email