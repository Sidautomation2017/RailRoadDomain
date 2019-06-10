package com.pkg.repositories;

import com.jayway.restassured.RestAssured;

public class RequestPath {
	
	public static String getBaseURI(String env ){
	if(env.equals("CMQA")){
		RestAssured.baseURI = Configuration.host;		
		}
		else{
			
			RestAssured.baseURI = Configuration.kcsqahost;	
		}
	return RestAssured.baseURI;
		
	}
	
	
	public static String postEDIValidate(){
		String resource="/EDI/Validate";
		return resource;
		
	}
	
	
	public static String postSetSystemStatus(){
		String resource="/SystemProcessTest";
		return resource;
		
	}
	
	public static String postToken(){
		String resource="/Token";
		return resource;		
	}
	
	

	
}
