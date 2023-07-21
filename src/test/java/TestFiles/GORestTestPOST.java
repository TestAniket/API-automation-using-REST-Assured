package TestFiles;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class GORestTestPOST extends baseClass {
	String BaseURI="https://gorest.co.in/";
	String AuthToken="e7a6f20ea564cab89a20aca1c95fd329249cb89d8d19d123939a5d174cbe5212";
	
	
	//@Test
	public void POSTcreateUserWithValidData_TC1()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
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
		
		System.out.println("response body returbed after TC1: "+ Resp.asString());
	}
	//@Test
	public void POSTpassNullDataThroughBody_TC2()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"\"name\":\"\",\r\n" + 
						"\"email\":\"\",\r\n" + 
						"\"gender\":\"\",\r\n" + 
						"\"status\":\"\"\r\n" + 
						"}")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("can't be blank")).
		body("data[1].message", equalTo("can't be blank")).
		body("data[2].message", equalTo("can't be blank, can be male of female")).
		body("data[3].message", equalTo("can't be blank")).extract().response();
		
		System.out.println("response body returbed after TC2: "+ Resp.asString());
	}
	//@Test
	public void POSTvalidateMandatoryFieldsName_TC3()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"\"name\":\""+dynamicName+"\",\r\n" + 
						"\"email\":\"\",\r\n" + 
						"\"gender\":\"\",\r\n" + 
						"\"status\":\"\"\r\n" + 
						"}")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("can't be blank")).
		body("data[1].message", equalTo("can't be blank, can be male of female")).
		body("data[2].message", equalTo("can't be blank")).extract().response();
		
		System.out.println("response body returbed after TC3: "+ Resp.asString());
	}
	//@Test
	public void POSTvalidateMandatoryFieldEmail_TC4()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"\"name\":\"\",\r\n" + 
						"\"email\":\""+dynamicEmail+"\",\r\n" + 
						"\"gender\":\"\",\r\n" + 
						"\"status\":\"\"\r\n" + 
						"}")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("can't be blank")).
		body("data[1].message", equalTo("can't be blank, can be male of female")).
		body("data[2].message", equalTo("can't be blank")).extract().response();
		
		System.out.println("response body returbed after TC4: "+ Resp.asString());
	}
	//@Test
	/*This particular API is taking invalid
	names as valid entry it is a bug hence test case failed*/
	public void POSTvalidateInvalidName_TC5()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"        \"name\": \"124568\",\r\n" + 
						"        \"email\": \""+dynamicEmail+"\",\r\n" + 
						"        \"gender\": \"female\",\r\n" + 
						"        \"status\": \"active\"\r\n" + 
						"    }")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].message", equalTo("Invalid Name")).extract().response();
		
		System.out.println("response body returbed after TC5: "+ Resp.asString());
	}
	//@Test
	public void POSTvalidateInvalidEmail_TC6()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"        \"name\": \""+dynamicName+"\",\r\n" + 
						"        \"email\": \"124586\",\r\n" + 
						"        \"gender\": \"female\",\r\n" + 
						"        \"status\": \"active\"\r\n" + 
						"    }")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].field", equalTo("email")).
		body("data[0].message",equalTo("is invalid")).extract().response();
		
		System.out.println("response body returbed after TC6: "+ Resp.asString());

	}
	@Test
	public void POSTvalidateInvalidGender_TC7()
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		Response Resp = given().log().all().baseUri(BaseURI).headers(headers)
				.auth().oauth2(AuthToken).body("{\r\n" + 
						"        \"name\": \""+dynamicName+"\",\r\n" + 
						"        \"email\": \""+dynamicEmail+"\",\r\n" + 
						"        \"gender\": \"8856\",\r\n" + 
						"        \"status\": \"active\"\r\n" + 
						"    }")
		.when().post("public/v1/users").
		then().log().all().statusCode(422).assertThat().
		body("data[0].field", equalTo("gender")).
		body("data[0].message",equalTo("can't be blank, can be male of female")).extract().response();
		
		System.out.println("response body returbed after TC7: "+ Resp.asString());
	}
	
	
	
}
