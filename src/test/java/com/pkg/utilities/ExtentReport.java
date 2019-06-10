package com.pkg.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.pkg.base.ReusableMethods;


public class ExtentReport {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest log;
	static String date=new SimpleDateFormat("dd_MMMM_yyyy").format(new Date());
	static String time=new SimpleDateFormat("HH_mm_ss").format(new Date());
	public static String path;
			
	public static void startRport(String apiname){
		String project="ISS_";		
		path=System.getProperty("user.dir")+"\\target\\ExtentReport\\"+date+"\\"+project+apiname+"_"+time+".html";
		
		ReusableMethods.storeValue("reportPath", path);
		htmlReporter=new ExtentHtmlReporter(path);		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Enviroment", "QA");
		extent.setSystemInfo("User Name", "Sidheshwar Tondare");
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\extent.config.xml"),true);	
		
	}

	

}
