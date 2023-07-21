@GET
Feature: GoRest Automation for GET request

Scenario: GET request for fetching information 
Given Gorest API is up and running fine  
When I use GET request on "public/v1/users"
Then API should return all users as response

Scenario: GET request with invalid Token to fetch information 
Given I passed "invalid token" in the request to Gorest API 
When I use GET request on "public/v1/users" 
Then API should return as invalid token

Scenario: GET request to fetch individual data 
Given Gorest API is up and running fine  
When I use GET request with userID "3136847" with "public/v1/users/" 
Then API should return data of single user with userID "3136847"

Scenario: GET request for fetching information by invalidID
Given Gorest API is up and running fine  
When I use GET request on "public/v1/users/3186ab788"
Then API should error mesg saying resource not found

Scenario: GET request for validating pagination 
Given Gorest API is up and running fine with query parameter "page" value 5 
When I use GET request on "public/v1/users"
Then API should return data of all user on page no 5

Scenario: GET request for filtering out results using gender as query param 
Given Gorest API is up and running fine with query parameter "gender" value "female"
When I use GET request on "public/v1/users"
Then API should return filtered result for "female" gender

Scenario: GET request for filtering out results using gender and status as query param 
Given Gorest API is up and running fine with query parameter "gender" as "female" and "status" as "active"
When I use GET request on "public/v1/users"
Then API should return filtered result for "female" and "active" users