package com.pkg.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.pkg.utilities.ServiceBus;




public class ReusableMethods {
	static Properties properties;
	static File file;  
	static FileInputStream fileInput;
	static FileOutputStream out;

	static WebDriver driver;
	public static WebDriver launchBrowseer(String browser){
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/BrowserDrivers/chromedriver.exe");
			driver=new ChromeDriver();
		
			break;
			
		case "firefox":	
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/BrowserDrivers/geckodriver.exe");
			driver=new FirefoxDriver();
			
			
		case "ie":	
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/src/test/resources/BrowserDrivers/IEDriverServer.exe");			
			driver=new InternetExplorerDriver();
			break;
			
		
		}
		driver.manage().window().maximize();
		
		return driver;
		
		
	}
	
	
	
	public static void storeValue(String parameter, String value) 
	{
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\dependency.properties";
		
		try{
		
		fileInput = new FileInputStream(path);
		properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		out = new FileOutputStream(path);
		properties.setProperty(parameter,value);
		properties.store(out, null);
		out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void storeValue(String parameter, int value) 
	{
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\dependency.properties";
		
		try{
		
		fileInput = new FileInputStream(path);
		properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		out = new FileOutputStream(path);
		String value1=Integer.toString(value);
		properties.setProperty(parameter, value1);
		properties.store(out, null);
		out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	



	public static String getStored_value(String parameter) 
	{
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\dependency.properties";
		String value=null;
		try {
			fileInput = new FileInputStream(path);
		
		properties = new Properties();
		properties.load(fileInput);
		value=properties.getProperty(parameter);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
		
	}
	public static int getValue(String parameter) 
	{
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\dependency.properties";
		String value=null;
		int value1 = 0;
		try {
			fileInput = new FileInputStream(path);
		
		properties = new Properties();
		properties.load(fileInput);
		value=properties.getProperty(parameter);
		value1=Integer.parseInt(value);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value1;
		
	}
	
	public static String getUTCdatetime(){
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date=dateFormatGmt.format(new Date());		
		return date;
		
	}
	
	public static String customizingDays(int n){
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Calendar c = Calendar.getInstance(); 	
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String updateddate=dateFormatGmt.format(c.getTime());
		System.out.println(updateddate);
		return updateddate;			
	}
	
	public static void waitForQueue() throws Exception{
		while(ServiceBus.getActiveCount()!=0){
			Thread.sleep(1000);			
		}
	}
	

}
