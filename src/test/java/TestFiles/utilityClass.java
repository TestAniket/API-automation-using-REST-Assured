package TestFiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class utilityClass extends baseClass
{
	public int createNewUser()
	{
		Response Resp = given().log().all().baseUri(BaseURI).
				headers(headersHashmap("Content-Type","application/json"))
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"        \"name\": \""+dynamicName+"\",\r\n" + 
						"        \"email\": \""+dynamicEmail+"\",\r\n" + 
						"        \"gender\": \"female\",\r\n" + 
						"        \"status\": \"active\"\r\n" + 
						"    }")
		.when().post("public/v1/users").
		then().log().all().statusCode(201).assertThat().body("data.id", not(equalTo(null))).
		body("data.name", equalTo(dynamicName)).
		body("data.email", equalTo(dynamicEmail)).extract().response();
		int newID=Resp.jsonPath().getInt("data.id");
		return newID;
	}
	
	
}
