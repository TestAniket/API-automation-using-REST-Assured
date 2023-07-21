package TestFiles;
import org.junit.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoRestTestGET extends baseClass
{
	String BaseURI= LoadInformation().getProperty("url");
	String AuthToken= LoadInformation().getProperty("token");
	String gender= "female";
	String status="active";
	int userID=3136847;
	int pageNo=5;
	@Test
	public void GETAllUsersData_TC01()
	{
		System.out.println(BaseURI);
		RequestSpecification reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken);
		Response resp = reqSpec.when().get("public/v1/users");

		System.out.println("response of GET Request TC01: "+resp.asString());
		ValidatableResponse validatableResp = resp.then().statusCode(200).
				assertThat().body("meta.pagination.limit", equalTo(10)).
				assertThat().body("data", not(emptyArray()));

	}

	@Test
	public void GETAllUsersDataInvalidToken_TC02()
	{
		RequestSpecification reqSpec = given().baseUri(BaseURI).auth().oauth2("Invalid token12345");
		Response resp = reqSpec.when().get("public/v1/users");

		System.out.println("response of GET Request TC02: "+resp.asString());

		ValidatableResponse validatableResp = resp.then().statusCode(401).
				assertThat().body("data.message",equalTo("Invalid token"));
	}

	@Test
	public void GETSpecificUserData_TC03()
	{
		RequestSpecification reqSpec = given().log().all().baseUri(BaseURI).auth().oauth2(AuthToken);
		Response resp = reqSpec.when().get("public/v1/users/"+userID);

		System.out.println("response of GET Request TC03: "+resp.asString());

		ValidatableResponse validatableResp = resp.then().statusCode(200).
				assertThat().body("data.id",equalTo(userID));
	}	
	@Test
	public void GETSpecificUserDataWithInvalidID_TC04()
	{
		RequestSpecification reqSpec = given().log().all().baseUri(BaseURI).auth().oauth2(AuthToken);
		Response resp = reqSpec.when().get("public/v1/users/3186ab788");

		System.out.println("response of GET Request TC04: "+resp.asString());

		ValidatableResponse validatableResp = resp.then().statusCode(404).
				assertThat().body("data.message",equalTo("Resource not found"));
	}
	@Test
	public void GETallUsersCheckPagination_TC5()
	{
		RequestSpecification reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken).param("page",pageNo );
		Response resp = reqSpec.when().get("public/v1/users");
		resp.then().assertThat().statusCode(200).assertThat().body("meta.pagination.page",equalTo(pageNo)).
		assertThat().body("data", not(emptyArray()));

		System.out.println("test case 5 passed pagination is correct");
	}
	@Test
	public void GETfilterUsersUsingSingleQueryParam_TC6()
	{
		RequestSpecification reqSpecification = given().baseUri(BaseURI).auth().oauth2(AuthToken).param("gender",gender );
		Response resp = reqSpecification.when().get("public/v1/users");
		resp.then().assertThat().body("data", not(emptyArray())).
		assertThat().body("data.gender", everyItem(equalTo(gender)));

		System.out.println("test case 6 passed single query param gender working fine");
	}
	@Test
	public void GETfilterUsersUsingMultipleQueryParam_TC7()
	{
		RequestSpecification reqSpec = given().baseUri(BaseURI).auth().oauth2(AuthToken).
				param("gender",gender ).param("status", status);
		Response resp = reqSpec.when().get("public/v1/users");
		resp.then().assertThat().statusCode(200).
		assertThat().body("data.status",everyItem(equalTo(status))).
		assertThat().body("data", not(emptyArray())).
		assertThat().body("data.gender", everyItem(equalTo(gender)));

		System.out.println("test case 7 passed multiple query param gender, status  working fine");
	}

}
