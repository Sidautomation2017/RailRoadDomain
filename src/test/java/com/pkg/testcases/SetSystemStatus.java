package com.pkg.testcases;


import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.pkg.base.ReusableMethods;
import com.pkg.dbvalidation.DataBaseConnection;
import com.pkg.dbvalidation.QueryRepositories;
import com.pkg.repositories.Configuration;
import com.pkg.repositories.RequestPath;
import com.pkg.repositories.RequestPayLoad;
import com.pkg.utilities.ExcelReader;
import com.pkg.utilities.ExtentReport;
import com.pkg.utilities.ServiceBus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SetSystemStatus extends ExtentReport{
	
	 public static LinkedHashSet<Integer> row = new LinkedHashSet<Integer>();
	 public static ArrayList<ArrayList<String>> arrayData=new ArrayList<ArrayList<String>>();
	 
	public static String testInputPath=Configuration.testDataPath+Configuration.fileName;	
	static FileInputStream file;
	 public static XSSFWorkbook excelWorkbook;
	 static XSSFSheet excelSheet;
	 int count=0;
	 public Logger logger=LogManager.getLogger(SetSystemStatus.class.getName());
	 private WebDriver driver;
	 private String authToken;
	 public static Connection connection;
	 
	@BeforeSuite
	 public void beforeSuite(){
		 RestAssured.baseURI = Configuration.token;		
		 Response resp=given()
					.body(RequestPayLoad.postToken())
					.when().contentType(ContentType.JSON).post(RequestPath.postToken()).then().assertThat().statusCode(200)
					.extract().response();			 
		 String tokenKey=resp.body().asString();
		 String tokenKey1=tokenKey.substring(1,tokenKey.length()-1);
		 System.out.println(tokenKey1);		
		authToken="Bearer "+tokenKey1;		
		  
	 }
	 
	@BeforeTest
	 public void beforetest() throws ClassNotFoundException, SQLException{
		 String name="SystemProcess";
		 ExtentReport.startRport(name);
		 if(Configuration.testenv.equalsIgnoreCase("CMQA")){
			connection = DataBaseConnection.setUp_DBConnection(Configuration.iss_transaction);
			}
			
			else {
				connection = DataBaseConnection.setUp_DBConnection(Configuration.iss_transaction1);				
			}
	 }

	
	 @BeforeClass
	 public void beforeClass() throws IOException{
		 logger.info("=======================Logging started==============================");
		 file=new FileInputStream(testInputPath);
		 excelWorkbook=new XSSFWorkbook(file);
		 int sheetcount = excelWorkbook.getNumberOfSheets();		
			for (int i = 1; i < 2; i++) {
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
	
	@Test(dataProvider="getID",priority=0)	
	public void postSyStemProcessTest(int id) throws Exception { 	
		
		logger.info("postSyStemProcessTest- "+id+" is in progress");
		String testcaseid=arrayData.get(id).get(ReusableMethods.getValue("Sr.No.Index"));
		String testCaseName=arrayData.get(id).get(ReusableMethods.getValue("TestScenario_Index")); 
		int wayBbillIdentifier=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("WayBillSettlementIdentifier_Index")));
		log=extent.createTest("TC"+testcaseid+"- "+testCaseName+" for WayBillIdentifier : "+wayBbillIdentifier);
		int expectedCode=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("StatusCode_Index")));
		RestAssured.baseURI=RequestPath.getBaseURI(Configuration.testenv);
		Response resp=given().header("Authorization", authToken)
				.body(RequestPayLoad.postSyStemProcess(id))
				.when().contentType(ContentType.JSON).post(RequestPath.postSetSystemStatus()).then().assertThat().statusCode(expectedCode)
				.extract().response();	
			logger.info(resp.asString());
			log.log(Status.PASS, MarkupHelper.createLabel("API responce code is as expected",ExtentColor.GREEN));
			Thread.sleep(2000);
			
		}		
	
	@Test(dataProvider="getID",priority=1)
	public void dataBaseValidation(int id) throws InterruptedException, ClassNotFoundException, Exception{
		logger.info("DBValidation- "+id+" is in progress");
		String testcaseid=arrayData.get(id).get(ReusableMethods.getValue("Sr.No.Index"));
		String testCaseName=arrayData.get(id).get(ReusableMethods.getValue("TestScenario_Index"));
		int wayBbillIdentifier=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("WayBillSettlementIdentifier_Index")));
		log=extent.createTest("DBValidation_TC"+testcaseid+"-"+testCaseName+"for WayBillIdentifier : "+wayBbillIdentifier);
		//SetSystemStatusDBvalidation 	
		System.out.println(wayBbillIdentifier);
		String expected_status=arrayData.get(id).get(ReusableMethods.getValue("ExpectedSettlementStatusID_Index"));
		System.out.println("expected status" +expected_status);
		log.log(Status.INFO, "System Status DB validation is in progress");	
		ReusableMethods.waitForQueue();
		String actual_status=QueryRepositories.getWayBillStatus(wayBbillIdentifier, "SettlementStatusId");
		Assert.assertEquals(actual_status,expected_status);	
		log.log(Status.PASS, MarkupHelper.createLabel("Set SystemStatus DB Validation is successful", ExtentColor.GREEN));
		
		//PostDisputeStatusDBValidation
		String  overrideDisputeStatus=SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("OverrideDisputeStatus_Index"));
		if(!overrideDisputeStatus.equalsIgnoreCase("NA")){
			logger.info("postSettlementDispute- "+id+" is in progress");
			log.log(Status.INFO,"postSettlementDispute DB validation is started");
			int expectedstatus=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("ExpectedDisputeStatusID_Index")));
			int actualstatus=QueryRepositories.getDisputStatus(wayBbillIdentifier);
			Assert.assertEquals(actualstatus, expectedstatus);
			log.log(Status.PASS, MarkupHelper.createLabel("Set postSettlementDispute DB validation is successful" , ExtentColor.GREEN));
					
		}	
		
		//PriorityURRWIN
		String ExpectedPrURRWIN=arrayData.get(id).get(ReusableMethods.getValue("ExpectedPrURRWIN_ActiveCount_Index"));
		if(!ExpectedPrURRWIN.equalsIgnoreCase("NA")){
		logger.info("PriorityURRWINDBValidation- "+id+" is in progress");
		log.log(Status.INFO, "PriorityURRWIN DB validation is started");
		int expected_count=Integer.valueOf(arrayData.get(id).get(ReusableMethods.getValue("ExpectedPrURRWIN_ActiveCount_Index")));		
		System.out.println("expected count" +expected_count);
		int actual_count=QueryRepositories.getPUActiveCount(wayBbillIdentifier);
		Assert.assertEquals(actual_count, expected_count);
		log.log(Status.PASS, MarkupHelper.createLabel("PriorityURRWIN DB record is udpated as expected" , ExtentColor.GREEN));
		if(actual_count>0) {
			int interlineSettlementId=Integer.valueOf(arrayData.get(id).get(ReusableMethods.getValue("ISSIdentifier_Index")));
			int actual_issId=QueryRepositories.getISId(wayBbillIdentifier);
			Assert.assertEquals(actual_issId, interlineSettlementId);
			log.log(Status.PASS, MarkupHelper.createLabel("ISS identifier is updated as expected" , ExtentColor.GREEN));
			}
		}	
	}
	
	//@Test(dataProvider="getID",priority=2)
	public void postSettlementDispute(int id) throws ClassNotFoundException{
				String  overrideDisputeStatus=SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("OverrideDisputeStatus_Index"));
		if(!overrideDisputeStatus.equalsIgnoreCase("NA")){
			logger.info("postSettlementDispute- "+id+" is in progress");
			String testcaseid=arrayData.get(id).get(ReusableMethods.getValue("Sr.No.Index"));
			String testCaseName=arrayData.get(id).get(ReusableMethods.getValue("TestScenario_Index"));
			log=extent.createTest("postSettlementDispute_TC"+testcaseid+"-"+testCaseName);
			int wayBbillIdentifier=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("WayBillSettlementIdentifier_Index")));
			int expectedstatus=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("ExpectedDisputeStatusID_Index")));
			int actualstatus=QueryRepositories.getDisputStatus(wayBbillIdentifier);
			Assert.assertEquals(actualstatus, expectedstatus);
					
		}
		/*else{
			 throw new SkipException("NA");
		}*/
	}
	
	//@Test(dataProvider="getID",priority=3)
	public void PriorityURRWINDBValidation(int id){
		String ExpectedPrURRWIN=arrayData.get(id).get(ReusableMethods.getValue("ExpectedPrURRWIN_ActiveCount_Index"));
		if(!ExpectedPrURRWIN.equalsIgnoreCase("NA")){
		logger.info("PriorityURRWINDBValidation- "+id+" is in progress");
		String testcaseid=arrayData.get(id).get(ReusableMethods.getValue("Sr.No.Index"));
		String testCaseName=arrayData.get(id).get(ReusableMethods.getValue("TestScenario_Index"));
		log=extent.createTest("PriorityURRWINDBValidation_TC"+testcaseid+"-"+testCaseName);
		int wayBbillIdentifier=Integer.parseInt(arrayData.get(id).get(ReusableMethods.getValue("WayBillSettlementIdentifier_Index")));
		int expected_count=Integer.valueOf(arrayData.get(id).get(ReusableMethods.getValue("ExpectedPrURRWIN_ActiveCount_Index")));		
		System.out.println("expected count" +expected_count);
		int actual_count=QueryRepositories.getPUActiveCount(wayBbillIdentifier);
		Assert.assertEquals(actual_count, expected_count);
		if(actual_count>0) {
			int interlineSettlementId=Integer.valueOf(arrayData.get(id).get(ReusableMethods.getValue("ISSIdentifier_Index")));
			int actual_issId=QueryRepositories.getISId(wayBbillIdentifier);
			Assert.assertEquals(actual_issId, interlineSettlementId);
			}
		}
		
		/*else{
			 throw new SkipException("NA");
		}*/
			
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
		 driver=ReusableMethods.launchBrowseer(Configuration.browser);
		 driver.get(ExtentReport.path);
		 connection.close();
		 
		// SendEmail.sendEmail();
	 }

}
