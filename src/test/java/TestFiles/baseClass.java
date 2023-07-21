package TestFiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;

import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class baseClass {


	
	protected String BaseURI= LoadInformation().getProperty("url");
	protected String AuthToken= LoadInformation().getProperty("token");
	protected String dynamicName= randomName(6);
	protected String dynamicEmail= randomEmail(7)+"@gmail.com";


	public Properties LoadInformation() 
	{
		try {
			InputStream Instream= getClass().getClassLoader().getResourceAsStream("propertyFile.properties");

			Properties prop = new Properties();
			prop.load(Instream);
			return prop;
		} catch (IOException e) {
			System.out.println("File not found exception thrown by LoadInfo");
			return null;
		}

	}
	public HashMap<String, String> headersHashmap(String key ,String value)
	{
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put(key, value);
		return headers;
	}
	public String randomName(int noOfAlphabets)
	{
		String name=RandomStringUtils.randomAlphabetic(noOfAlphabets);
		return name;
	}
	public String randomEmail(int noOfIntegers)
	{
		String number= RandomStringUtils.randomAlphabetic(noOfIntegers);
		return number;
	}

}
