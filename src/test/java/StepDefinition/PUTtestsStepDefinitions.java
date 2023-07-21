package StepDefinition;

import TestFiles.baseClass;
import TestFiles.utilityClass;
import io.cucumber.java.en.*;
import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PUTtestsStepDefinitions extends baseClass
{
	dependancyInjection di;
	public PUTtestsStepDefinitions(dependancyInjection dependancy) {
	this.di = dependancy;
	}
	int ID;
	@Given("A new user is created")
	public void a_new_user_is_created() {
	    utilityClass util= new utilityClass();
	     ID=util.createNewUser();
	    System.out.println("new user ID: "+ID);
	}

	@Given("headers and body is given with updated email value")
	public void headers_and_body_is_given_with_updated_email_value() {
	    di.reqSpec=given().log().all().baseUri(BaseURI).
	    headers(headersHashmap("Content-Type", "application/json")).
	    auth().oauth2(AuthToken).
	    body("{\r\n" + 
	    		"    \"email\":\""+dynamicEmail+"\"\r\n" + 
	    		"}");
	}

	@When("I use PUTT request on {string}")
	public void i_use_putt_request_on(String resource) {
		System.out.println(BaseURI+resource+Integer.toString(ID));
	   di.resp=di.reqSpec.when().put(resource+Integer.toString(ID));
	}

	@Then("API should return body with updated email")
	public void api_should_return_body_with_updated_email() {
	    di.resp.then().assertThat().statusCode(200).body("data.email", equalTo(dynamicEmail)).
	    body("data.id", equalTo(ID));
	    System.out.println(di.resp.asString());
	}
}
