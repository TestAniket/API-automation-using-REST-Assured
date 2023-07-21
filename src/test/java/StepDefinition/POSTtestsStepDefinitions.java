package StepDefinition;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;

import TestFiles.baseClass;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class POSTtestsStepDefinitions extends baseClass {
	dependancyInjection di;
	public POSTtestsStepDefinitions(dependancyInjection dependancy)
	{
		this.di= dependancy;
	}
	
	@Given("headers and body is given to the request")
	public void headers_and_body_is_given_to_the_request() {
	  di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
	  .body("{\r\n" + 
				"        \"name\": \""+dynamicName+"\",\r\n" + 
				"        \"email\": \""+dynamicEmail+"\",\r\n" + 
				"        \"gender\": \"female\",\r\n" + 
				"        \"status\": \"active\"\r\n" + 
				"    }");
	}
	@When("I use POST request on {string}")
	public void i_use_post_request_on(String resource) {
		di.resp=di.reqSpec.when().post(resource);
	}
	@Then("API should create new user and return the success message")
	public void api_should_create_new_user_and_return_the_success_message() {
		di.resp.then().log().all().statusCode(201).assertThat().body("data.id", not(equalTo(null))).
		body("data.name", equalTo(dynamicName)).
		body("data.email", equalTo(dynamicEmail)).extract().response();
		
		System.out.println("response body recieved : "+ di.resp.asString());
	}
	@Given("headers and null body is given to the request")
	public void headers_and_null_body_is_given_to_the_request() {
		di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
		  .body("{\r\n" + 
					"\"name\":\"\",\r\n" + 
					"\"email\":\"\",\r\n" + 
					"\"gender\":\"\",\r\n" + 
					"\"status\":\"\"\r\n" + 
					"}");
	}

	@Then("API should return error messages in response")
	public void api_should_return_error_messages_in_response() {
		di.resp.then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("can't be blank")).
		body("data[1].message", equalTo("can't be blank")).
		body("data[2].message", equalTo("can't be blank, can be male of female")).
		body("data[3].message", equalTo("can't be blank")).extract().response();
	
		System.out.println("response body returned: "+ di.resp.asString());
	}
	@Given("headers and valid name with null body is given to the request")
	public void headers_and_valid_name_with_null_body_is_given_to_the_request() {
	    di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
	    .body("{\r\n" + 
						"\"name\":\""+dynamicName+"\",\r\n" + 
						"\"email\":\"\",\r\n" + 
						"\"gender\":\"\",\r\n" + 
						"\"status\":\"\"\r\n" + 
						"}");
	}

	@Then("API should return error messages for blank fields in response")
	public void api_should_return_error_messages_for_blank_fields_in_response() {
		di.resp.then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("can't be blank")).
		body("data[1].message", equalTo("can't be blank, can be male of female")).
		body("data[2].message", equalTo("can't be blank")).extract().response();
		
		System.out.println("response body returned: "+ di.resp.asString());
	}
	@Given("headers and valid email with null body is given to the request")
	public void headers_and_valid_email_with_null_body_is_given_to_the_request() {
		di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
	    .body("{\r\n" + 
						"\"name\":\"\",\r\n" + 
						"\"email\":\""+dynamicEmail+"\",\r\n" + 
						"\"gender\":\"\",\r\n" + 
						"\"status\":\"\"\r\n" + 
						"}");
	}
	@Given("headers with invalid name {string} is passed into the body")
	public void headers_with_invalid_name_is_passed_into_the_body(String invalidName) {
		di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
	    .body("{\r\n" + 
						"\"name\":\""+invalidName+"\",\r\n" + 
						"\"email\":\""+dynamicEmail+"\",\r\n" + 
						"\"gender\":\"female\",\r\n" + 
						"\"status\":\"active\"\r\n" + 
						"}");
	}

	@Then("API should return error message for invalid name")
	public void api_should_return_error_message_for_invalid_name() {
	    di.resp.then().assertThat().body("data.message", equalTo("Invalid name"));
		System.out.println(di.resp.asString());
	}
	@Given("headers with invalid email {string} is passed into the body")
	public void headers_with_invalid_email_is_passed_into_the_body(String invalidEmail) {
		di.reqSpec.headers(headersHashmap("Content-Type", "application/json"))
	    .body("{\r\n" + 
						"\"name\":\""+dynamicName+"\",\r\n" + 
						"\"email\":\""+invalidEmail+"\",\r\n" + 
						"\"gender\":\"female\",\r\n" + 
						"\"status\":\"active\"\r\n" + 
						"}");
	}

	@Then("API should return error message for invalid email")
	public void api_should_return_error_message_for_invalid_email() {
		di.resp.then().log().all().statusCode(422).assertThat().
		body("data[0].field", equalTo("email")).
		body("data[0].message",equalTo("is invalid"));
		System.out.println(di.resp.asString());
	}
}
