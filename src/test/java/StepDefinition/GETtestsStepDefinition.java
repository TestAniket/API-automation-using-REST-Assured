package StepDefinition;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;

import TestFiles.baseClass;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GETtestsStepDefinition extends baseClass{
	dependancyInjection di;
	public GETtestsStepDefinition(dependancyInjection dependancy)
	{
		this.di=dependancy;
	}
	
	@Given("Gorest API is up and running fine")
	public void gorest_api_is_up_and_running_fine() {
		di.reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken);	    
	}

	@When("I use GET request on {string}")
	public void i_use_get_request_on(String resource) {
		di.resp = di.reqSpec.when().get(resource);
		System.out.println("response body: "+di.resp.asString());
	}

	@Then("API should return all users as response")
	public void api_should_return_all_users_as_response() {
		di.resp.then().statusCode(200).
		assertThat().body("meta.pagination.limit", equalTo(10)).
		assertThat().body("data", not(emptyArray()));
	}
	
	@Given("I passed {string} in the request to Gorest API")
	public void i_passed_in_the_request_to_gorest_api(String invalidToken) {
		di.reqSpec = given().baseUri(BaseURI).auth().oauth2(invalidToken);
	}

	@Then("API should return as invalid token")
	public void api_should_return_as_invalid_token() {
		di.resp.then().statusCode(401).
		assertThat().body("data.message",equalTo("Invalid token"));	
	}
	@When("I use GET request with userID {string} with {string}")
	public void i_use_get_request_with_user_id(String userID, String endPoint) {
		di.resp = di.reqSpec.when().get(endPoint+userID);
		System.out.println("response body: "+di.resp.asString());
	}
	@Then("API should return data of single user with userID {string}")
	public void api_should_return_single_user_data_as_response(String userID) {
		int validatableID= Integer.parseInt(userID);
		di.resp.then().statusCode(200).
		assertThat().body("data.id",equalTo(validatableID));
	}
	@Then("API should error mesg saying resource not found")
	public void api_should_error_mesg_saying_resource_not_found() {
		di.resp.then().statusCode(404).
		assertThat().body("data.message",equalTo("Resource not found"));
	}
	@Given("Gorest API is up and running fine with query parameter {string} value {int}")
	public void gorest_api_is_up_and_running_fine_with_query_parameter_value(String key, int pageNo) {
		di.reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken).param(key,pageNo );
	}

	@Then("API should return data of all user on page no {int}")
	public void api_should_return_data_of_all_user_on_page_no(int pageNo) {
		di.resp.then().assertThat().statusCode(200).
		assertThat().body("meta.pagination.page",equalTo(pageNo)).
		assertThat().body("data", not(emptyArray()));
	}
	@Given("Gorest API is up and running fine with query parameter {string} value {string}")
	public void gorest_api_is_up_and_running_fine_with_query_parameter_value(String key, String value) {
		
		di.reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken).param(key,value);
	}
	@Then("API should return filtered result for {string} gender")
	public void api_should_return_filtered_result_for_female_gender(String gender) {
		di.resp.then().assertThat().body("data", not(emptyArray())).
		assertThat().body("data.gender", everyItem(equalTo(gender)));
	}
	@Given("Gorest API is up and running fine with query parameter {string} as {string} and {string} as {string}")
	public void gorest_api_is_up_and_running_fine_with_query_parameter_value_and_as(String gender, String female, String status, String active) {
		di.reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken).
				param("gender",female ).param("status", active);
	}
	@Then("API should return filtered result for {string} and {string} users")
	public void api_should_return_filtered_result_for_and_users(String gender, String status) {
		di.resp.then().assertThat().statusCode(200).
		assertThat().body("data.status",everyItem(equalTo(status))).
		assertThat().body("data", not(emptyArray())).
		assertThat().body("data.gender", everyItem(equalTo(gender)));
	}
}
