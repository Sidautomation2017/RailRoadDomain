package com.pkg.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.ParseConversionEvent;

import com.pkg.utilities.ExcelReader;

public class Configuration {
	public static File file;  
	public static FileInputStream fileInput;
	public static Properties properties;		
	public static String host,token,testDataPath,testdatasetup,fileName,reportpath,browser,kcsqahost,testenv;
	public static String iss_master, iss_RWB, iss_transaction,iss_transaction1;
	public static String currentdir=System.getProperty("user.dir");
	static String path=currentdir+"\\src\\test\\resources\\configuration.properties";
	public static int config_wait,polling_wait;
	public static String dataSetUp;	
	

	
	
	static {
		
		file=new File(path);
		try {
			fileInput=new FileInputStream(file);
			properties=new Properties();
			properties.load(fileInput);
			host=properties.getProperty("HOST");
			testDataPath=properties.getProperty("TESTDATAPATH");
			testdatasetup=properties.getProperty("TESTDATASETUP");
			fileName=properties.getProperty("FILENAME");			
			token=properties.getProperty("TOKEN_HOST");
			iss_master=properties.getProperty("DB_MASTER");
			iss_RWB=properties.getProperty("DB_RWB");
			iss_transaction=properties.getProperty("DB_TRANSACTION");
			iss_transaction1=properties.getProperty("DB_TRANSACTION1");
			reportpath=properties.getProperty("reportPath");		
			browser=properties.getProperty("BROWSER");
			kcsqahost=properties.getProperty("KCSQA_HOST");
			testenv=properties.getProperty("ENVIRONMENT");
			dataSetUp=properties.getProperty("DATA_SETUP");
			
		}
	catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

}
