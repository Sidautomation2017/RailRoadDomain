package com.pkg.dbvalidation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pkg.repositories.Configuration;


public class DataBaseConnection {
	private static String username;
	private static String password ;
	private static String dbURL;
	private static String connectionString;
	static Connection connn;
	

	public static void main (String []args) throws ClassNotFoundException, SQLException{
		QueryRepositories.getWayBillStatus(2, "OrgPathId");
		}
		
		


	public static Connection setUp_DBConnection(String dbName) throws ClassNotFoundException, SQLException {	
		
		if(Configuration.testenv.equalsIgnoreCase("CMQA")){
			 username = "cmmmcsissadmin";
		  password = "dhrys4$%^#FDB";
			dbURL = "jdbc:sqlserver://cm-kcsr-mmcs-qa-iss-sqldb.database.windows.net;";
			connectionString=dbURL+"databaseName="+dbName+";autoReConnect=true&amp;allowMultiQueries=true";	
						   
			 }
		else{
			 username = "kcsmmcsqaisssqldb@cloudmoyo.com";
			  password = "r#6l9N0c@ZFG&G";
				dbURL = "jdbc:sqlserver://kcs-mmcs-qa-iss-sqldb-sncus.database.windows.net;";
			connectionString=dbURL+"databaseName="+dbName+";authentication=ActiveDirectoryPassword;encrypt=true;trustServerCertificate=true;selectMethod=cursor";
			System.out.println("Connection has been set up correctly");
			
		}
										
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Class.forName("org.apache.derby.jdbc.ClientDriver");
		Connection con = DriverManager.getConnection(connectionString,username,password);			     					        		    	        
    	return con;			
	}

}