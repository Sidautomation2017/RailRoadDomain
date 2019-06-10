package com.pkg.testcases;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import static com.jayway.restassured.RestAssured.*;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.pkg.repositories.Configuration;
import com.pkg.repositories.RequestPath;
import com.pkg.repositories.RequestPayLoad;
import com.pkg.utilities.ExcelReader;
import com.pkg.utilities.ExtentReport;
import com.pkg.utilities.SendEmail;

import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EDI_Validation extends ExtentReport {
	
	 public static LinkedHashSet<Integer> row = new LinkedHashSet<Integer>();
	 public static ArrayList<ArrayList<String>> arrayData=new ArrayList<ArrayList<String>>();
	 
	static String testInputPath=Configuration.testDataPath+Configuration.fileName;	
	static FileInputStream file;
	 public static XSSFWorkbook excelWorkbook;
	 static XSSFSheet excelSheet;
	 int count=0;
	 public Logger logger=LogManager.getLogger(EDI_Validation.class.getName());
	
	 
	 @BeforeTest
	 public void beforetest(){
		 
		 ExtentReport.startRport(EDI_Validation.class.getName());
	 }

	
	 @BeforeClass
	 public void beforeClass() throws IOException{
		 file=new FileInputStream(testInputPath);
		 excelWorkbook=new XSSFWorkbook(file);
		 int sheetcount = excelWorkbook.getNumberOfSheets();		
			for (int i = 0; i < sheetcount; i++) {
				String sheetName = excelWorkbook.getSheetName(i);
				excelSheet = excelWorkbook.getSheet(sheetName);
				int rowCount=excelSheet.getLastRowNum();
				logger.info("Row Count is: "+rowCount);
				for(int j=1;j<=rowCount;j++){
					arrayData.add(ExcelReader.getData(testInputPath, i,j));
					
				}
				
				for(int k=0;k<rowCount;k++){					
					row.add(count);
					count++;
					
				}
				logger.debug(row);
				logger.debug(arrayData);
			}
			
			
		
			
	 }
	 
	 
	
	 
	 @DataProvider (name="getID")
	 public Object[] getSrNo(){
		 Object[] srNo=row.toArray();
		return srNo;
	 }
	
	@Test(dataProvider="getID")
	public void postEDIValidate(int id) throws IOException { 	
		
		logger.info("postEDIValidate"+id+" is in progress");
		log=extent.createTest("postEDIValidate - "+id);
		int expectedCode=Integer.parseInt(arrayData.get(id).get(1));
		RestAssured.baseURI = Configuration.host;			
		Response resp=given()
				.body(RequestPayLoad.postEDIValidate(id))
				.when().contentType(ContentType.JSON).post(RequestPath.postEDIValidate()).then().assertThat().statusCode(expectedCode)
				.extract().response();	
			logger.info(resp.asString());
		}		
	
	@AfterMethod
	 public void afterMethod(ITestResult result){
		 
		 if (result.isSuccess()) {
		      logger.info("==Test casses  Passed===");
		      log.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED",ExtentColor.GREEN));
		    }
		    else if (result.getStatus() == ITestResult.FAILURE) {
		          logger.debug("===Test case failed==="+result.getThrowable().toString());
		          log.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+" Test Case Failed",ExtentColor.RED));
		    }
		    else if (result.getStatus() == ITestResult.SKIP) {
		        logger.info("===Test skipped===");
		        log.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case PASSED",ExtentColor.ORANGE));
		    }		 

	 }
	
	 @AfterTest
	 public void endReport() throws Exception{
		 extent.flush();
		// SendEmail.sendEmail();
	 }
	
}
