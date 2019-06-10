package com.pkg.testcases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mysql.jdbc.UpdatableResultSet;
import com.pkg.base.ReusableMethods;
import com.pkg.dbvalidation.DataBaseConnection;
import com.pkg.dbvalidation.UpdateDB;
import com.pkg.repositories.Configuration;
import com.pkg.utilities.DataSetUp;
import com.pkg.utilities.ExcelReader;
import com.pkg.utilities.ExtentReport;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class DataSetter extends ExtentReport{
	public static LinkedHashSet<Integer> row = new LinkedHashSet<Integer>();
	public static String testInputPath = Configuration.testDataPath+Configuration.fileName;	
	static FileInputStream file;
	public static XSSFWorkbook excelWorkbook;
	static XSSFSheet excelSheet;
	public static ArrayList<ArrayList<String>> arrayData = new ArrayList<ArrayList<String>>();
	int count = 0;
	public static Connection connection;
	public static String updatevalue;
	 public Logger logger=LogManager.getLogger(DataSetter.class.getName());
	 WebDriver driver;
	 

	
	@BeforeClass
	public void beforeclass() throws IOException {
		 String name="DataSetUp";
		 ExtentReport.startRport(name);
		log=extent.createTest("Test data set up for system Process");
		logger.info("=======================Logging started==============================");
		System.out.println(testInputPath);
		file = new FileInputStream(testInputPath);
		excelWorkbook = new XSSFWorkbook(file);
		int sheetcount = excelWorkbook.getNumberOfSheets();
		for (int i = 0; i < 1; i++) {
			String sheetName = excelWorkbook.getSheetName(i);
			excelSheet = excelWorkbook.getSheet(sheetName);
			int rowCount = excelSheet.getLastRowNum();
			// logger.info("Row Count is: "+rowCount);
			for (int j = 1; j <= rowCount; j++) {
				arrayData.add(DataSetUp.readExcel(testInputPath, i, j));

			}

			for (int k = 0; k < rowCount; k++) {
				row.add(count);
				count++;

			}
			// logger.debug(row);
			// logger.debug(arrayData);
			// System.out.println(arrayData);
		}

	}

	@DataProvider(name = "dp")
	public Object[] getSrNo() {
		Object[] srNo = row.toArray();
		return srNo;
	}

	@Test(dataProvider = "dp")
	public void setUpData(int id) throws ClassNotFoundException, SQLException {	
		
		int a=id+1;
		logger.info("Data set up for Sr. No. : "+a+" is in progress" );
		log.log(Status.INFO, "Data set up for Sr. No. : "+a+" is in progress");
		String tableName = arrayData.get(id).get(2);
		String columnName = arrayData.get(id).get(3);
		String filterColumn= arrayData.get(id).get(5);
		updatevalue = arrayData.get(id).get(6);
		String identifier = arrayData.get(id).get(4);
		String databasename= arrayData.get(id).get(7);
		String storeColumn=arrayData.get(id).get(8);		
		String updatedValue = UpdateDB.updateDB(tableName, columnName, updatevalue, identifier,databasename,filterColumn,storeColumn);
		if (updatevalue.equalsIgnoreCase("null")) {
			try {
				Assert.assertTrue(updatedValue.contains(updatevalue));
			} catch (NullPointerException e) {
			}
		} else {
			Assert.assertTrue(updatedValue.contains(updatevalue));
		}

		logger.info("Data set up for Sr. No. : "+a+" is completed" );
	//	log.log(Status.INFO, MarkupHelper.createLabel(" Test data set up for WayBillSettlementIdentfier: "+identifier+ "is completed", ExtentColor.LIME));
			}

	@AfterMethod
	public void afterClass(ITestResult result) throws SQLException {
		if (result.isSuccess()) {		
			logger.info("==Data set Up Passed===");
		      log.log(Status.PASS, MarkupHelper.createLabel(" Test data set up is sucessful",ExtentColor.GREEN));
		    }
		    else if (result.getStatus() == ITestResult.FAILURE) {	
		    	logger.debug("===Data setup failed==="+result.getThrowable().toString());
		          log.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+" Test data set up is Failed",ExtentColor.RED));
		    }
		    else if (result.getStatus() == ITestResult.SKIP) {	
		    	logger.info("===Data setup skipped===");
		        log.log(Status.SKIP, MarkupHelper.createLabel(" Test Case SKIP",ExtentColor.ORANGE));
		    }		 
		 
	}
	
	@AfterTest
	public void afterTest(){
		extent.flush();
		 driver=ReusableMethods.launchBrowseer(Configuration.browser);
		 driver.get(ExtentReport.path);
		
	}

}
