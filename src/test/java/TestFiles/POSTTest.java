package TestFiles;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;


import groovyjarjarantlr4.v4.parse.ANTLRParser.notSet_return;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;

public class POSTTest 
{
	
	String dynamicName= randomString(10);
	int dynamicYear= Integer.parseInt(randomNumber(4));
	int dynamicID= Integer.parseInt(randomIntID(6));
	int existingID=dynamicID;
	@Test
	public void POSTAddPlaceRahulShetty() throws InterruptedException
	{
		String BaseURI="https://rahulshettyacademy.com";
		HashMap<String,String> headerText= new HashMap<String, String>();
		HashMap<String,String> queryParam= new HashMap<String, String>();
		queryParam.put("Key", "qaclick123");
		headerText.put("Content-Type","application/json");
		RequestSpecification requestSpec = given().baseUri(BaseURI).headers(headerText).queryParams(queryParam)
											.body("{\r\n" + 
													"  \"location\": {\r\n" + 
													"    \"lat\": -38.383494,\r\n" + 
													"    \"lng\": 33.427362\r\n" + 
													"  },\r\n" + 
													"  \"accuracy\": 50,\r\n" + 
													"  \"name\": \"vaishali more\",\r\n" + 
													"  \"phone_number\": \"898989\",\r\n" + 
													"  \"address\": \"pune maharashtra\",\r\n" + 
													"  \"types\": [\r\n" + 
													"    \"shoe park\",\r\n" + 
													"    \"shop\"\r\n" + 
													"  ],\r\n" + 
													"  \"website\": \"http://google.com\",\r\n" + 
													"  \"language\": \"French-IN\"\r\n" + 
													"}");
		Response resp = requestSpec.when().post("/maps/api/place/add/json");
		System.out.println("BODY of newly added place :"+resp.asString());
		String placeID=resp.jsonPath().getString("place_id");
		queryParam.put("place_id", placeID);
		System.out.println("placeID of newly added place: "+placeID);
		
		resp.then().statusCode(200).assertThat().body("status",equalTo("OK"))
		.assertThat().body("place_id", not(emptyArray()));
		
		//post request always followed by get request to validate if content is 
		//posted successfully 
		
		Response GETresponse = given().log().all().baseUri(BaseURI).queryParam("Key","qaclick123").queryParam("place_id", placeID).
				when().get("/maps/api/place/get/json");
		System.out.println("Response received using GET :"+GETresponse.asString());
		Thread.sleep(3000);
		GETresponse.then().statusCode(200);
		System.out.println("status code checked");
		GETresponse.then().assertThat().body("address", equalTo("jalna maharashtra")).
		assertThat().body("phone_number", equalTo("3333"));	
	}
	
	//@Test
	public void POSTRequestToAddDataDummy()
	{
		String baseURI="https://dummy.restapiexample.com/";
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Cotent-Type", "application/json");
		
		Response resp = given().baseUri(baseURI).headers(headers).
						body("{\"name\":\"Nisha more\",\"salary\":\"49500\",\"age\":\"23\"}").when().post("api/v1/create");
		System.out.println("body received after POST req: "+resp.asString());
		int ID=resp.jsonPath().getInt("data.id");
		System.out.println("ID fetched from the body: "+ID);
		
		resp.then().statusCode(200).assertThat().body("status",equalTo("success")).
		assertThat().body("data.id", equalTo(ID));
		System.out.println("*-*-****-*POST REQUEST DONE-*-*-*-*-");
	//GET Request to validate the Added content using POST
	
		Response GetResp = given().log().all().baseUri(baseURI).when().get("api/v1/employee/"+ID);
		System.out.println("Response after GET req: "+GetResp.asString());
//		int IDFetchedUsingGET=GetResp.jsonPath().getInt("data.id");
//		String nameFetchedUsingGET=GetResp.jsonPath().getString("data.employee_name");
//		int salaryFetchedUsingGET= GetResp.jsonPath().getInt("data.employee_salary");
//		int ageFetchedUsingGET= GetResp.jsonPath().getInt("data.employee_age");
//		
//		GetResp.then().statusCode(200).assertThat().body("data.id", equalTo(IDFetchedUsingGET)).
//		assertThat().body("data.employee_name", equalTo(nameFetchedUsingGET)).
//		assertThat().body("data.employee_salary", equalTo(salaryFetchedUsingGET)).
//		assertThat().body("data.employee_age", equalTo(ageFetchedUsingGET));
	}
//	@Test
	
	public void TC01()
	{
		String BaseURI="https://api.instantwebtools.net";
		
		Response resp = given().log().all().baseUri(BaseURI).header("Content-Type","application/json").body("{\r\n" + 
				"    \"id\": "+dynamicID+",\r\n" + 
				"    \"name\": \""+dynamicName+"\",\r\n" + 
				"    \"country\": \"India\",\r\n" + 
				"    \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png\",\r\n" + 
				"    \"slogan\": \"From India\",\r\n" + 
				"    \"head_quaters\": \"Dharavi mumbai\",\r\n" + 
				"    \"website\": \"www.indigoAirlines.com\",\r\n" + 
				"    \"established\": \""+dynamicYear+"\"\r\n" + 
				"}").when().post("/v1/airlines");
		System.out.println("response body after POST req TC 1: "+resp.asString());
		int ID = resp.jsonPath().getInt("id");
		System.out.println("ID fetched from response of POST TC 1: "+ID);
		resp.then().statusCode(200);
		
		System.out.println("----POST REQUEST TC1 ENDS HERE------");
		
		Response GETresponse = given().baseUri(BaseURI).when().get("v1/airlines/"+ID);
		
		System.out.println("Body fetched from GET method TC 1: "+GETresponse.asString());
		GETresponse.then().statusCode(200).assertThat().body("id", equalTo(ID));
		System.out.println("----GET REQUEST TC1 ENDS HERE------");
	}
//	@Test
	public void TC02()
	{
		String BaseURI="https://api.instantwebtools.net";
		Response resp = given().log().all().baseUri(BaseURI).header("Content-Type","application/json").body("{\r\n" + 
				"    \"id\": "+this.existingID+",\r\n" + 
				"    \"name\": \""+dynamicName+"\",\r\n" + 
				"    \"country\": \"India\",\r\n" + 
				"    \"logo\": \"https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Qatar_Airways_Logo.svg/sri_lanka.png\",\r\n" + 
				"    \"slogan\": \"From India\",\r\n" + 
				"    \"head_quaters\": \"Dharavi mumbai\",\r\n" + 
				"    \"website\": \"www.indigoAirlines.com\",\r\n" + 
				"    \"established\": \""+dynamicYear+"\"\r\n" + 
				"}").when().post("/v1/airlines");
		System.out.println("response body after POST req TC 2: "+resp.asString());
		int ID = resp.jsonPath().getInt("id");
		System.out.println("ID fetched from response of POST TC 2: "+ID);
		resp.then().statusCode(200);
		
		System.out.println("----POST REQUEST TC2 ENDS HERE------");	
	}
	
//	@Test
//	public void TC3()
//	{
//		System.out.println("running test case 3");
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String randomString(int noOfAlphabets)
	{
		String name=RandomStringUtils.randomAlphabetic(noOfAlphabets);
		return name;
	}
	public String randomNumber(int noOfIntegers)
	{
		String number= RandomStringUtils.randomNumeric(noOfIntegers);
		return number;
	}
	public String randomIntID(int noOFIntegers)
	{
		String ID= RandomStringUtils.randomNumeric(noOFIntegers);
		return ID;
	}
}
