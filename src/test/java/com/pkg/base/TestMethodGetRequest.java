package com.pkg.base;

import org.testng.Assert;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.pkg.repositories.Configuration;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;;

public class TestMethodGetRequest {

	//@Test
	public void test01() {
		// String
		// token="eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbGFkZHJlc3MiOiIqw5fDlcO0w4Bfw4zCpX9FXFzCtMOCwrZ1wo_CvMKuPMWSw554w4_igJ3igJrDl1x1MDAwZlx1MDAwNcOmw77Dg8OodMOGwqlcdTAwMWVsUsWhXHUwMDEzw6zDv8W-wrFcIsO6w5fCriIsInVwbiI6Imdtw4thQ8Kkw5fDh1xmw4914oCgfMKsxb5Ew4rDoFfCu3LCuHHDn1XDg8KxYOKAk0nCp8KjJXVcdTAwMDJcdTAwMTLCjUvDrVwiwqvigKbDuT1qw4N-wq4iLCJuYW1lIjoiw6TDpcKze8KuVVx1MDAxMTPCvsOv4oSi4oCiy4bDuMO4wr9cdTAwMTRcZmnigKBTwq3DoSlqQMKrwqtcbsK7eVxiIiwiZ2l2ZW5uYW1lIjoiw55UXHUwMDE3w4hXYcO8dWjCo1x1MDAxMTTCtlx1MDAxODhcdTAwMDDDmsWSXHUwMDFiVMKuakBGwrdaZHTDn1xcw5jDhyIsImF1dGhlbnRpY2F0aW9ubWV0aG9kIjoiLVxiXHUwMDAywrDigJ0xb2bDjcOWXHUwMDBmw4JIwqfDkkbCq2RmXHUwMDE5y4Zlw4lcdTAwMGLigKJcYj9sXHUwMDE0YMO0w4lcXMOixaHigLDDl1pM4oCwXHUwMDAybOKAoEvDi0xcdTAwMDJcdTAwMDdcdTAwMTdaw5XCukpdNy_DlMWgwrV_XHUwMDE2wqpvw5x4VuKAoEdZZMOiw55ccsOtSFwiwrZQwqvCp1x1MDAxZjpAVMOOXHUwMDFmSsOewpDigJwlc8OSwrdEw54iLCJhdXRoZW50aWNhdGlvbmluc3RhbnQiOiJqIMK64oCmROKAokDCqcKBw6rDmiMmP8uGw71cdTAwMTHDgsOhwq1zw5xcdTAwMDDDvzJAb8O9XHUwMDAzKm7igJ5cdTAwMTjDplx1MDAxOOKAnTsoK8KkYzLDvlx1MDAwM8Orwrtjw5oiLCJTZWNyZXRWZXJzaW9uIjoiMDUxZGRmZGQ2MGFhNGFhMjhjMTM5MTQwZWYyY2EwODgiLCJPcmdhbml6YXRpb24iOiJLQ1NSIiwibmJmIjoxNTUyMzA5MTcxLCJleHAiOjE1NTIzOTczNzEsImlzcyI6Imh0dHA6Ly9jbS5jb20iLCJhdWQiOiJodHRwOi8vY20uYXVkLmNvbSJ9.vIrQgjN-L6xcu-PESBepRx3N2vO1rFz19cHq3vGasoE";
		Response resp = given().header("Authorization", "Bearer " + Configuration.token).when()
				.get("https://cm-kcsr-mmcs-qa-web-api.cloudmoyo.com/TestMethods");
		System.out.println(resp.asString());
		Assert.assertEquals(resp.statusCode(), 200);
	}

	// @extracting parameter from response
	//@Test
	public void test02() {
		String id = given().header("Authorization", "Bearer " + Configuration.token).when()
				.get("https://cm-kcsr-mmcs-qa-web-api.cloudmoyo.com/TestMethods").then().contentType(ContentType.JSON)
				.extract().path("[0].Id");

		System.out.println("Id for first test method:- " + id);
	}

	//@Test
	public void test03() { 
		
		//** Add new test method 
		String apibody="{" + "\"Id\": \"00000000-0000-0000-0000-000000000000\"," + "\"Name\": \"RestAssuredTest\","
				+ "\"ModifiedBy\": \"string\"," + "\"Identifier\": 0,"
				+ "\"Description\": \"this is automation driven\"," + "\"Status\": 0,"
				+ "\"TrackId\": \"00000000-0000-0000-0000-000000000000\","
				+ "\"ModifiedDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"StartDate\": \"2019-03-15T07:07:19.828Z\"," + "\"EndDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"ChildId\": 0," + "\"SystemDefined\": true," + "\"IsEditable\": true" + 
				"}";
		RestAssured.baseURI = "https://cm-kcsr-mmcs-qa-web-api.cloudmoyo.com";
		Response resp=given().header("Authorization", "Bearer " + Configuration.token)
				.body(apibody)
				.when().contentType(ContentType.JSON).post("/TestMethods").then().assertThat().statusCode(201).and()
				.body("Name", equalTo("RestAssuredTest")).and().contentType(ContentType.JSON)
				.extract().response();
		
		System.out.println(resp.asString());

		//Update same test method exacting ID
		JsonPath js=new JsonPath(resp.asString());
		String sysid=js.get("Id");
		System.out.println(sysid);		
		String apibody1="{" + "\"Id\": \""+sysid+"\"," + "\"Name\": \"RestAssuredTest4\","
				+ "\"ModifiedBy\": \"string\"," + "\"Identifier\": 0,"
				+ "\"Description\": \"this is automation driven\"," + "\"Status\": 1,"
				+ "\"TrackId\": \"00000000-0000-0000-0000-000000000000\","
				+ "\"ModifiedDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"StartDate\": \"2019-03-15T07:07:19.828Z\"," + "\"EndDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"ChildId\": 0," + "\"SystemDefined\": true," + "\"IsEditable\": true" + 
				"}";
		Response resp1=given().header("Authorization", "Bearer " + Configuration.token).body(apibody1).
		when().contentType(ContentType.JSON).put("/TestMethods").
		then().assertThat().statusCode(204)
		.extract().response();
		
		System.out.println(resp1.asString());
		
		
	}
	
	//@Test
	public void putrequest(){
		RestAssured.baseURI = "https://cm-kcsr-mmcs-qa-web-api.cloudmoyo.com";
		String apibody1="{" + "\"Id\": \"d61f4258-4707-11e9-867b-00155df693a1\"," + "\"Name\": \"RestAssuredTest3\","
				+ "\"ModifiedBy\": \"string\"," + "\"Identifier\": 0,"
				+ "\"Description\": \"this is automation driven\"," + "\"Status\": 1,"
				+ "\"TrackId\": \"00000000-0000-0000-0000-000000000000\","
				+ "\"ModifiedDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"StartDate\": \"2019-03-15T07:07:19.828Z\"," + "\"EndDate\": \"2019-03-15T07:07:19.828Z\","
				+ "\"ChildId\": 0," + "\"SystemDefined\": true," + "\"IsEditable\": true" + 
				"}";
		Response resp1=given().header("Authorization", "Bearer " + Configuration.token).body(apibody1).
		when().contentType(ContentType.JSON).put("/TestMethods").
		then().assertThat().statusCode(204)
		.extract().response();
		
		System.out.println(resp1.asString());
		
	}
	
	//@Test
	public void postRequest() throws IOException{
		
		String apibody=GenerateStringFromResource("C:\\MMCS_Workspace\\Automation\\MMCS_ISS_APITesting\\src\\test\\resources\\testMethod.JSON");
		RestAssured.baseURI = "https://cm-kcsr-mmcs-qa-web-api.cloudmoyo.com";
		Response resp=given().header("Authorization", "Bearer " + Configuration.token)
				.body(apibody)
				.when().contentType(ContentType.JSON).post("/TestMethods").then().assertThat().statusCode(201).and()
				.and().contentType(ContentType.JSON)
				.extract().response();
		
		System.out.println(resp.asString());
	}

	
	public static String GenerateStringFromResource(String path) throws IOException{
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
	
	
	
}
